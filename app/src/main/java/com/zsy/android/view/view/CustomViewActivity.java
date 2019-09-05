package com.zsy.android.view.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zsy.android.R;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:
 * </p>
 *
 * @author Zsy
 * @date 2019/6/21 10:26
 */

public class CustomViewActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
    }
}
