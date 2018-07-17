package com.mcxtzhang.buy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mcxtzhang.zrouter.ZRouterManager;
import com.zrouter.ZRouter;

@ZRouter("buy/main")
public class BuyMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_main);
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZRouterManager.jump(BuyMainActivity.this, "bbs/Main", null);
            }
        });

    }
}
