package com.mcxtzhang.modularizationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mcxtzhang.zrouter.RouterManager;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        getWindow().getDecorView().findViewById(R.id.activity_launcher).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterManager.jump(LauncherActivity.this, "bbs/Main", null);
            }
        });

    }
}
