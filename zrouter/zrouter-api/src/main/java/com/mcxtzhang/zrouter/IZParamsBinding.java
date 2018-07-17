package com.mcxtzhang.zrouter;

/**
 * Intro: 路由自动绑定变量 接口类型
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/7/26.
 * History:
 */

public interface IZParamsBinding<T> {
    void bind(T target);
}
