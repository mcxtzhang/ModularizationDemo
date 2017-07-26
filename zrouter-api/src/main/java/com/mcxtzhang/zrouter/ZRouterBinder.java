package com.mcxtzhang.zrouter;

import android.app.Activity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/7/26.
 * History:
 */

public class ZRouterBinder {
    private static List<String> blackList = new ArrayList<>();
    private static Map<String, IZParamsBinding> classCache = new LinkedHashMap<>();


    public static void bind(Activity activity) {
        String className = activity.getClass().getName();
        try {
            if (!blackList.contains(className)) {
                IZParamsBinding paramsBinding = classCache.get(className);
                if (null == paramsBinding) {  // No cache.
                    paramsBinding = (IZParamsBinding) Class.forName(className + "ZParamsBinding").getConstructor().newInstance();
                }
                paramsBinding.bind(activity);
                classCache.put(className, paramsBinding);
            }
        } catch (Exception ex) {
            blackList.add(className);    // This instance need not autowired.
        }
    }
}
