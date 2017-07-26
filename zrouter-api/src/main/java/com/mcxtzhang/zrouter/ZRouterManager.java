package com.mcxtzhang.zrouter;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/3/9.
 * History:
 */

public class ZRouterManager {

    private static final String TAG = "zxt/ZRouterBindHelper";

    private static Map<String, String> routerMap = new HashMap<>();

    public static void addRule(String pagePath, String pageClsName) {
        routerMap.put(pagePath, pageClsName);
    }

    private static void initRouterMap() {
        if (routerMap.isEmpty()) {
            ZRouterRules.init();
        }
    }

    public static void jump(Activity activity, String where, Bundle bundle) {
        initRouterMap();

        String clsFullName = routerMap.get(where);
        if (TextUtils.isEmpty(clsFullName)) {
            Log.e(TAG, "Error in jump() where = [" + where + "] not found in routerMap!");
        } else {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(activity.getPackageName(), clsFullName));
            if (null != bundle) {
                intent.putExtras(bundle);
            }
            activity.startActivity(intent);
            Log.d(TAG, "Jump success:" + where);
        }
    }

    public static void jump(Activity activity, String where, Bundle bundle, int requestCode) {
        initRouterMap();


        String clsFullName = routerMap.get(where);
        if (TextUtils.isEmpty(clsFullName)) {
            Log.e(TAG, "Error in jump() where = [" + where + "] not found in routerMap!");
        } else {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(activity.getPackageName(), clsFullName));
            if (null != bundle) {
                intent.putExtras(bundle);
            }
            activity.startActivityForResult(intent, requestCode);
            Log.d(TAG, "Jump success:" + where);
        }
    }
}
