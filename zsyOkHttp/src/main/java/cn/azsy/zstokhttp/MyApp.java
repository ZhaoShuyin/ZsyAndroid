package cn.azsy.zstokhttp;

import android.app.Application;
import android.content.Context;

import cn.azsy.zstokhttp.zsyokhttp.zok.ZsyOk;

/**
 * Created by zsy on 2017/4/28.
 */

public class MyApp extends Application {

    public static MyApp myApp;
    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
        ZsyOk.initClient(this);
    }

    public static Context getIntstance() {
        return myApp;
    }
}
