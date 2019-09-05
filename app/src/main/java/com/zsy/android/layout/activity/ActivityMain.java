package com.zsy.android.layout.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zsy.android.R;
import com.zsy.android.layout.activity.router.RouterUtil;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:
 * </p>
 *
 * @author Zsy
 * @date 2019/7/30 9:25
 */

public class ActivityMain extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);
        RouterUtil.init(this);
    }

    public void router(View view) {
        RouterUtil.start(this, "other");
    }
}
