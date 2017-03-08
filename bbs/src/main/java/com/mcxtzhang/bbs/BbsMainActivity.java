package com.mcxtzhang.bbs;


import android.os.Bundle;

import com.example.ZRouter;
import com.mcxtzhang.common_lib.BaseActivity;

/**
 * 模块一的 主Activity
 */

@ZRouter(path = "bbs/Main")
public class BbsMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs_main);
    }
}
