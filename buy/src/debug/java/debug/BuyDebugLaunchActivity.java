package debug;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.mcxtzhang.buy.BuyMainActivity;

/**
 * Created by zhangxutong on 2018/7/17.
 */

public class BuyDebugLaunchActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setText("BuyDebugLaunchActivity");
        setContentView(textView);
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(BuyDebugLaunchActivity.this, BuyMainActivity.class));
                finish();
            }
        }, 1000);

    }
}
