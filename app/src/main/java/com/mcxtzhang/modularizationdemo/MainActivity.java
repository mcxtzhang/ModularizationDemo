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

    @ZParams(Key.KEY_INT)
    int mId;
    @ZParams(Key.KEY_INT)
    byte[] mId2;
    @ZParams(Key.KEY_INT)
    String mId3;

    Button mButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ZBindMainActivity.bindView(this);

        ZRouterBinder.bind(this);

        //ZRouterBindHelper.bind(this);

        findViewById(R.id.btnModule1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZRouterManager.jump(MainActivity.this, "bbs/Main");

            }
        });

        //Toast.makeText(MainActivity.this, "接受到的值是" + mId, Toast.LENGTH_SHORT).show();
        mButton2 = (Button) findViewById(R.id.btnModule2);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(Key.KEY_INT, 1024);
                ZRouterManager.jump(MainActivity.this, "buy/main", bundle);

            }
        });
    }

}
