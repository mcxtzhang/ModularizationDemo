package com.mcxtzhang.zrouter;

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


    public static void bind(Object target) {
        String className = target.getClass().getName();
        try {
            if (!blackList.contains(className)) {
                IZParamsBinding paramsBinding = classCache.get(className);
                if (null == paramsBinding) {  // No cache.
                    paramsBinding = (IZParamsBinding) Class.forName(className + "ZParamsBinding").getConstructor().newInstance();
                }
                paramsBinding.bind(target);
                classCache.put(className, paramsBinding);
            }
        } catch (Exception ex) {
            blackList.add(className);    // This instance need not autowired.
        }
    }
}
