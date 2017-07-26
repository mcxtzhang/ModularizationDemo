package com.example;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;


/**
 * Intro: Use ComponentName replace Class
 * Author:zhangxutong
 * E-mail:mcxtzhang@163.com
 * Home Page:http://blog.csdn.net/zxt0601
 * Created:  2017/1/17.
 * History:
 * 2017/02/04 add : Auto bind params in bundle for target Activity/Fragment.
 */

@AutoService(Processor.class)
public class ZRouter3Processor extends AbstractProcessor {

    private Elements mElementUtils;
    private Types mTypes;
    private String moduleName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mElementUtils = processingEnv.getElementUtils();
        mTypes = processingEnv.getTypeUtils();

        Map<String, String> options = processingEnv.getOptions();
        if (null != options && !options.isEmpty()) {
            moduleName = options.get(Config.KEY_MODULE_NAME);
        }

    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(ZRouter.class.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        // modules
        String[] moduleNames = null;
        Set<? extends Element> modulesList = roundEnvironment.getElementsAnnotatedWith(ZModules.class);
        if (modulesList != null && modulesList.size() > 0) {
            Element modules = modulesList.iterator().next();
            moduleNames = modules.getAnnotation(ZModules.class).value();
        }
        //create ZRouterRules
        if (null != moduleNames) {
            MethodSpec.Builder initMethod = MethodSpec.methodBuilder("init")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC);
            for (String module : moduleNames) {
                initMethod.addStatement("ZRouterRules_" + module + ".init();");
            }
            TypeSpec routerInit = TypeSpec.classBuilder("ZRouterRules")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(initMethod.build())
                    .build();
            try {
                JavaFile.builder(AptUtils.PKG_NAME, routerInit)
                        .build()
                        .writeTo(processingEnv.getFiler());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        MethodSpec.Builder initBuilder = MethodSpec.methodBuilder("init");


        //traverse annotation named ZRtouer
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ZRouter.class);
        for (Element element : elements) {
            TypeElement typeElement;
            if (element instanceof TypeElement) {
                typeElement = (TypeElement) element;
            } else {
                return false;
            }
            ZRouter zRouter = typeElement.getAnnotation(ZRouter.class);
            String className = typeElement.getQualifiedName().toString();
            ClassName RouterManagerClass = ClassName.get(AptUtils.PKG_NAME, "ZRouterManager");
            initBuilder.addStatement("$T.addRule($S, $S)", RouterManagerClass, zRouter.path(), className);

            //2017 02 04 add auto bind params value in bundle for target activity

            TypeMirror TYPE_ACTIVITY = mElementUtils.getTypeElement("android.app.Activity").asType();
            TypeMirror TYPE_FRAGMENT = mElementUtils.getTypeElement("android.app.Fragment").asType();
            TypeMirror TYPE_PARCELABLE = mElementUtils.getTypeElement("android.os.Parcelable").asType();

            boolean isActivity = mTypes.isSubtype(typeElement.asType(), TYPE_ACTIVITY);


            List<? extends Element> members = mElementUtils.getAllMembers(typeElement);
            MethodSpec.Builder bindViewMethodSpecBuilder = MethodSpec.methodBuilder("bind")
                    .addModifiers(Modifier.PUBLIC/*, Modifier.STATIC*/)
                    .returns(TypeName.VOID);
            if (isActivity) {
                bindViewMethodSpecBuilder.addParameter(ClassName.get(typeElement.asType()), "activity")
                        .addStatement("$T intent = activity.getIntent()", ClassName.get("android.content", "Intent"))
                        .beginControlFlow("if (null != intent)")
                        .addStatement("$T extras = intent.getExtras()", ClassName.get("android.os", "Bundle"))
                        .beginControlFlow("if (null != extras)");
            } else {
                bindViewMethodSpecBuilder.addParameter(ClassName.get(typeElement.asType()), "activity")
                        .addStatement("$T extras = activity.getArguments()", ClassName.get("android.os", "Bundle"))
                        .beginControlFlow("if (null != extras)");
            }


            for (Element item : members) {
                buildGetParamsStatement(TYPE_PARCELABLE, bindViewMethodSpecBuilder, item);

            }
            if (isActivity) {
                bindViewMethodSpecBuilder.endControlFlow()
                        .endControlFlow();
            } else {
                bindViewMethodSpecBuilder.endControlFlow();
            }


            ParameterizedTypeName IZParamsBinding = ParameterizedTypeName.get(ClassName.get("com.mcxtzhang.zrouter", "IZParamsBinding"), ClassName.get(typeElement.asType()));

            TypeSpec typeSpec = TypeSpec.classBuilder(element.getSimpleName() + "ZParamsBinding")
                    //extends xxx
                    /*.superclass(ClassName.get("com.mcxtzhang.zrouter","IZParamsBinding"))*/
                    .addSuperinterface(IZParamsBinding)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(bindViewMethodSpecBuilder.build())
                    .build();
            JavaFile javaFile = JavaFile.builder(AptUtils.getPkgName(mElementUtils, typeElement), typeSpec).build();
            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        //ZRouterBindHelper.java build


        MethodSpec init = initBuilder
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .build();

        //class
        TypeSpec hello = TypeSpec.classBuilder("ZRouterRules_" + moduleName)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(init)
                .build();

        try {
            JavaFile.builder(AptUtils.PKG_NAME, hello)
                    .build()
                    .writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    // according to Element（Field），to build extras.getXXX()
    private void buildGetParamsStatement(TypeMirror TYPE_PARCELABLE, MethodSpec.Builder bindViewMethodSpecBuilder, Element item) {
        ZParams diView = item.getAnnotation(ZParams.class);
        if (diView == null) {
            return;
        }
        TypeMirror fieldType = item.asType();
        String fieldTypeName = fieldType.toString();
        switch (fieldTypeName) {
            case "byte":
                bindViewMethodSpecBuilder.addStatement(String.format("activity.%s = extras.getByte(\"%s\")", item.getSimpleName(), diView.value()));
                break;
            case "byte[]":
                bindViewMethodSpecBuilder.addStatement(String.format("activity.%s = extras.getByteArray(\"%s\")", item.getSimpleName(), diView.value()));
                break;
            case "char":
                bindViewMethodSpecBuilder.addStatement(String.format("activity.%s = extras.getChar(\"%s\")", item.getSimpleName(), diView.value()));
                break;
            case "char[]":
                bindViewMethodSpecBuilder.addStatement(String.format("activity.%s = extras.getCharArray(\"%s\")", item.getSimpleName(), diView.value()));
                break;
            case "float":
                bindViewMethodSpecBuilder.addStatement(String.format("activity.%s = extras.getFloat(\"%s\")", item.getSimpleName(), diView.value()));
                break;
            case "float[]":
                bindViewMethodSpecBuilder.addStatement(String.format("activity.%s = extras.getFloatArray(\"%s\")", item.getSimpleName(), diView.value()));
                break;
            case "ArrayList<Integer>":
            case "List<Integer>":
                bindViewMethodSpecBuilder.addStatement(String.format("activity.%s = extras.getIntegerArrayList(\"%s\")", item.getSimpleName(), diView.value()));
                break;
            case "boolean[]":
                bindViewMethodSpecBuilder.addStatement(String.format("activity.%s = extras.getBooleanArrayExtra(\"%s\")", item.getSimpleName(), diView.value()));
                break;
            case "boolean":
                bindViewMethodSpecBuilder.addStatement(String.format("activity.%s = extras.getBooleanExtra(\"%s\")", item.getSimpleName(), diView.value()));
                break;
            case "java.lang.String":
                bindViewMethodSpecBuilder.addStatement(String.format("activity.%s = extras.getString(\"%s\")", item.getSimpleName(), diView.value()));
                break;
            case "int":
            case "Integer":
                bindViewMethodSpecBuilder.addStatement(String.format("activity.%s = extras.getInt(\"%s\")", item.getSimpleName(), diView.value()));
                break;
            default:
                if (mTypes.isSubtype(fieldType, TYPE_PARCELABLE)) {  // PARCELABLE
                    bindViewMethodSpecBuilder.addStatement(String.format("activity.%s = extras.getParcelable(\"%s\")", item.getSimpleName(), diView.value()));
                } else {
                    bindViewMethodSpecBuilder.addStatement(String.format("%s = (\"%s\") (%s)", item.getSimpleName(), diView.value(), fieldType.toString()));
                }
                break;
        }
    }


}
