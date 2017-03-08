package com.mcxtzhang.modularizationdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.ZRouter;

@ZRouter(path = "main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnModule1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JumpUtils.jumpToModule1(MainActivity.this);
            }
        });
    }

}
