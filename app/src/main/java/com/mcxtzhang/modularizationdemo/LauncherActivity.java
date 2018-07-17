package com.mcxtzhang.modularizationdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mcxtzhang.zrouter.ZRouterManager;
import com.zrouter.ZRouter;

@ZRouter(value = "launcher")
public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = new Bundle();
                ZRouterManager.jump(LauncherActivity.this, "main", bundle);
                finish();
            }
        }, 1000);

    }
}
