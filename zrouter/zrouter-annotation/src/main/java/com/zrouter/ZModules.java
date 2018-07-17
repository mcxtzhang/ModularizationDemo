package com.zrouter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Intro: 模块化时使用
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/3/9.
 * History:
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface ZModules {
    String[] value();
}
