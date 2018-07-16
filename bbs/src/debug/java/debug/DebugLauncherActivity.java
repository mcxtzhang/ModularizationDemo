package debug;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
        Button button = new Button(this);
        button.setText("Debug Launcher");
        setContentView(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DebugLauncherActivity.this, BbsMainActivity.class));
                finish();
            }
        });
    }
}
