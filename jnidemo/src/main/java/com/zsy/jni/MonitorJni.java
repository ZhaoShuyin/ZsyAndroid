package com.zsy.jni;

import android.util.Log;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:  通过Jni 持续 响应参数
 * </p>
 *
 * @author Zsy
 * @date 2019/7/26 15:31
 */

public class MonitorJni {

    static {
        System.loadLibrary("zsyso");
    }

    public static MonitorListener monitorListener;

    public static void startJni(MonitorListener listener) {
        monitorListener = listener;
        start();
    }

    public static native void start();

    public static native void stop();

    public static void setValue(int value) {
        if (monitorListener != null) {
            monitorListener.call(value);
        }
        Log.e("jnitest", "value==: "+value);
    }

    public interface MonitorListener {
        void call(int value);
    }
}

