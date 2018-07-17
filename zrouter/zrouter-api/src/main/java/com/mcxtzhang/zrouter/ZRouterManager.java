package com.mcxtzhang.zrouter;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Intro: 路由跳转管理类
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/3/9.
 * History:
 */

public class ZRouterManager {

    private static final String TAG = "zxt/ZRouterManager";

    private static Map<String, ZRouterRecord> routerMap = new HashMap<>();

    public static void addRule(String pagePath, ZRouterRecord record) {
        routerMap.put(pagePath, record);
    }

    private static void initRouterMap() {
        if (routerMap.isEmpty()) {
            ZRouterRules.init();
        }
    }

    public static void jump(Activity activity, String where) {
        jump(activity, where, null);
    }

    public static void jump(Activity activity, String where, Bundle bundle) {
        Intent intent = getJumpIntent(activity, where, bundle);
        if (intent != null) {
            activity.startActivity(intent);
            Log.d(TAG, "Jump success:" + where);
        }
    }

    public static void jump(Activity activity, String where, Bundle bundle, int requestCode) {
        Intent intent = getJumpIntent(activity, where, bundle);
        if (intent != null) {
            activity.startActivityForResult(intent, requestCode);
            Log.d(TAG, "Jump success:" + where);
        }
    }

    public static Intent getJumpIntent(Activity activity, String where, Bundle bundle) {
        initRouterMap();

        ZRouterRecord record = routerMap.get(where);
        if (null == record) {
            Log.e(TAG, "Error in getJumpIntent() where = [" + where + "] not found in routerMap!");
            return null;
        }
        String clsFullName = record.getClassPath();
        if (TextUtils.isEmpty(clsFullName)) {
            Log.e(TAG, "Error in getJumpIntent() clsFullName = [" + clsFullName + "] can not be null");
            return null;
        } else {
            if (record.isActivity()) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(activity.getPackageName(), clsFullName));
                if (null != bundle) {
                    intent.putExtras(bundle);
                }

                PackageManager packageManager = activity.getPackageManager();
                List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
                if (!activities.isEmpty()) {
                    return intent;
                } else {
                    Log.e(TAG, "Error in getJumpIntent() intent = [" + intent + "] not found in PackageManager!");
                    return null;
                }
            } else {//is fragment
                Intent commonIntent = getJumpIntent(activity, "commonFragmentActivity", bundle);
                if (null != commonIntent) {
                    commonIntent.putExtra("key-fragment-name", clsFullName);
                }
                return commonIntent;
            }
        }

    }

    public static Fragment getFragment(Context context, String path, Bundle bundle) {
        initRouterMap();

        ZRouterRecord record = routerMap.get(path);
        if (null == record) {
            Log.e(TAG, "Error in getFragment() path = [" + path + "] not found in routerMap!");
            return null;
        }
        String clsFullName = record.getClassPath();
        if (TextUtils.isEmpty(clsFullName)) {
            Log.e(TAG, "Error in getFragment() clsFullName = [" + clsFullName + "] can not be null");
            return null;
        } else {
            if (record.isFragment()) {
                return Fragment.instantiate(context, record.getClassPath(), bundle);
            } else {
                Log.e(TAG, "Error in getFragment() path = [" + path + "] is not a Fragment");
                return null;
            }
        }
    }

    public static android.support.v4.app.Fragment getFragmentV4(Context context, String path) {
        return getFragmentV4(context, path, null);
    }

    public static android.support.v4.app.Fragment getFragmentV4(Context context, String path, Bundle bundle) {
        initRouterMap();

        ZRouterRecord record = routerMap.get(path);
        if (null == record) {
            Log.e(TAG, "Error in getFragmentV4() path = [" + path + "] not found in routerMap!");
            return null;
        }
        String clsFullName = record.getClassPath();
        if (TextUtils.isEmpty(clsFullName)) {
            Log.e(TAG, "Error in getFragmentV4() clsFullName = [" + clsFullName + "] can not be null");
            return null;
        } else {
            if (record.isFragment()) {
                return android.support.v4.app.Fragment.instantiate(context, record.getClassPath(), bundle);
            } else {
                Log.e(TAG, "Error in getFragmentV4() path = [" + path + "] is not a Fragment");
                return null;
            }
        }
    }
}
