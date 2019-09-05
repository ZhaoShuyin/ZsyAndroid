package com.zsy.jni;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.zsy.android.R;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:Jni
 * </p>
 * 配置javah https://www.cnblogs.com/lao-liang/p/9418818.html?utm_source=debugrun&utm_medium=referral
 * @author Zsy
 * @date 2019/7/18 16:15
 */

public class JniActivity extends Activity {

    TextView show;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);
        show = findViewById(R.id.tv_jni_show);
    }

    public void jni(View view) {
        String string = JniUtil.getString();
        show.setText(string);
    }

}
