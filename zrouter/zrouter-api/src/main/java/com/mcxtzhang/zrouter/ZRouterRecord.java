package com.mcxtzhang.zrouter;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/7/26.
 * History:
 */

public class ZRouterRecord {
    public static final int TYPE_ACTIVITIY = 1;
    public static final int TYPE_FRAGMENT = 2;

    private String classPath;
    private int type;//0 act 1 frag

    public ZRouterRecord() {
    }

    public ZRouterRecord(String classPath) {
        this.classPath = classPath;
    }

    public ZRouterRecord(String classPath, int type) {
        this.classPath = classPath;
        this.type = type;
    }

    public boolean isActivity() {
        return TYPE_ACTIVITIY == type;
    }

    public boolean isFragment() {
        return TYPE_FRAGMENT == type;
    }

    public String getClassPath() {
        return classPath;
    }

    public ZRouterRecord setClassPath(String classPath) {
        this.classPath = classPath;
        return this;
    }

    public int getType() {
        return type;
    }

    public ZRouterRecord setType(int type) {
        this.type = type;
        return this;
    }
}
