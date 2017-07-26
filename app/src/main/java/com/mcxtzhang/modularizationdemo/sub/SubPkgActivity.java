package com.mcxtzhang.modularizationdemo.sub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.ZParams;
import com.example.ZRouter;
import com.mcxtzhang.modularizationdemo.Key;
import com.mcxtzhang.modularizationdemo.R;

@ZRouter(path = "subpkg")
public class SubPkgActivity extends AppCompatActivity {
    @ZParams(Key.KEY_INT)
    String mId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_pkg);
    }
}
