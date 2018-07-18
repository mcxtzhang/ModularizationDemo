package debug;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.mcxtzhang.bbs.BbsMainActivity;
import com.mcxtzhang.common_lib.BaseActivity;

/**
 * 介绍：只在debug时才出现的Activity
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2017/1/9.
 */

public class DebugLauncherActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setText("BbsDebugLaunchActivity");
        textView.setTextColor(Color.BLACK);

        setContentView(textView);
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(DebugLauncherActivity.this, BbsMainActivity.class));
                finish();
            }
        }, 2000);


    }
}
