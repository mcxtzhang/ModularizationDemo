package com.mcxtzhang.modularizationdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.mcxtzhang.zrouter.ZRouterBinder;
import com.mcxtzhang.zrouter.ZRouterManager;
import com.zrouter.ZParams;
import com.zrouter.ZRouter;

@ZRouter(value = "main")
public class MainActivity extends AppCompatActivity {
    public static final String KEY_INT = "KEY_INT";

    @ZParams(KEY_INT)
    int mId;
    @ZParams(KEY_INT)
    byte[] mId2;
    @ZParams(KEY_INT)
    String mId3;

    Button mButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //auto-bind
        ZRouterBinder.bind(this);

        findViewById(R.id.btnModule1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZRouterManager.jump(MainActivity.this, "bbs/Main");

            }
        });
        mButton2 = (Button) findViewById(R.id.btnModule2);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(KEY_INT, 1024);
                ZRouterManager.jump(MainActivity.this, "buy/main", bundle);

            }
        });

        findViewById(R.id.btnModule3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.frameLayout);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, ZRouterManager.getFragmentV4(MainActivity.this, "fragment"))
                        .commit();
            }
        });
    }

}
