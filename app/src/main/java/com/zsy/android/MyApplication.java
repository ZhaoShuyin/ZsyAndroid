package com.zsy.android;

import android.app.Application;
import android.content.Context;


/**
 * Title: ZsyAndroid
 * <p>
 * Description:
 * </p>
 *
 * @author Zsy
 * @date 2019/7/2 9:06
 */

public class MyApplication extends Application {
    public static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static Context getApplication() {
        return application;
    }
}
