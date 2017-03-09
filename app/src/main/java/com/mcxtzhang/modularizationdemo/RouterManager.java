package com.mcxtzhang.modularizationdemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.util.Map;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/3/9.
 * History:
 */

public class RouterManager {

    private static final String TAG = "zxt/ZRouter";

    private static Map<String, String> routerMap;

/*    private ZRouter() {
        routerMap = new HashMap();
        routerMap.put("main", "com.mcxtzhang.modularizationdemo.MainActivity");
    }

    public static ZRouter getInstance() {
        return ZRouter.InnerClass.INSTANCE;
    }*/

    private static void initRouterMap() {
        if (routerMap == null) {

        }
    }

    public static void jump(Activity activity, String where, Bundle bundle) {
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
