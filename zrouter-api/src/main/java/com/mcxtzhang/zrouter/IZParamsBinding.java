package com.mcxtzhang.zrouter;

import android.app.Activity;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/7/26.
 * History:
 */

public interface IZParamsBinding<T extends Activity> {
    void bind(T activity);
}
