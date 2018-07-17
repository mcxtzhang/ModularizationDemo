package com.mcxtzhang.modularizationdemo.sub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.mcxtzhang.modularizationdemo.Key;
import com.mcxtzhang.modularizationdemo.R;
import com.mcxtzhang.zrouter.ZRouterBinder;
import com.zrouter.ZParams;
import com.zrouter.ZRouter;

@ZRouter(value = "subpkg")
public class SubPkgActivity extends AppCompatActivity {
    @ZParams(Key.KEY_INT)
    String mId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_pkg);
        ZRouterBinder.bind(this);
        Toast.makeText(this, "我接收到的值:" + mId, Toast.LENGTH_SHORT).show();
    }
}
