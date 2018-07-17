package com.mcxtzhang.buy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.mcxtzhang.zrouter.ZRouterBinder;
import com.mcxtzhang.zrouter.ZRouterManager;
import com.zrouter.ZParams;
import com.zrouter.ZRouter;

@ZRouter("buy/main")
public class BuyMainActivity extends AppCompatActivity {
    @ZParams("KEY_INT")
    int mParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_main);

        ZRouterBinder.bind(this);
        Toast.makeText(this, "接收到的参数是:" + mParam, Toast.LENGTH_SHORT).show();

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZRouterManager.jump(BuyMainActivity.this, "bbs/Main", null);
            }
        });

    }
}
