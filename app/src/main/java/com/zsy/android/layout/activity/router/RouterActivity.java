package com.zsy.android.layout.activity.router;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

/**
 * Title: 通过Router,不使用Activity.class跳转界面
 * <p>
 * Description:
 * </p>
 *
 * @author Zsy
 * @date 2019/7/30 9:26
 */
@RouterNode(host = "other")
public class RouterActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setTextSize(200);
        textView.setText("这是Router跳转过来的");
        setContentView(textView);
    }
}
