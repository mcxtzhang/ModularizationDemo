package com.mcxtzhang.modularizationdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.ZRouter;
import com.mcxtzhang.zrouter.ZRouterManager;

@ZRouter(path = "launcher")
public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        getWindow().getDecorView().findViewById(R.id.activity_launcher).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(Key.KEY_INT, 250);
                ZRouterManager.jump(LauncherActivity.this, "main", bundle);
            }
        });

    }
}
