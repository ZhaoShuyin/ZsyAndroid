package com.example.assist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:
 * </p>
 *
 * @author Zsy
 * @date 2019/7/23 14:02
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
