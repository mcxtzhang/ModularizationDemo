package com.mcxtzhang.common_lib;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

/**
 * 介绍：假装我是项目里的 lib库
 * 里面有一个BaseActivity
 * 这里只是改变了背景色 啥都不干
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/12/14.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().findViewById(android.R.id.content).setBackgroundColor(Color.RED);
    }
}
