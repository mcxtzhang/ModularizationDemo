package com.mcxtzhang.bbs;


import android.os.Bundle;

import com.mcxtzhang.common_lib.BaseActivity;
import com.zrouter.ZRouter;

/**
 * 模块一的 主Activity
 */

@ZRouter("bbs/Main")
public class BbsMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs_main);
    }
}
