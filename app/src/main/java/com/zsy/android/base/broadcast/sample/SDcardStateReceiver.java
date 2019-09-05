package com.zsy.android.base.broadcast.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * SDK装载状态广播监听
 * 清单文件注册
 * <receiver android:name=".SDcardStateReceiver">
 * <intent-filter >
 * <action android:name="android.intent.action.MEDIA_MOUNTED"/>
 * <action android:name="android.intent.action.MEDIA_UNMOUNTED"/>
 * <data android:scheme="file"/>
 * </intent-filter>
 * </receiver>
 */
public class SDcardStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("收到广播");
        String action = intent.getAction();
        if ("android.intent.action.MEDIA_MOUNTED".equals(action)) {
            System.out.println("sd卡挂上了");
        } else {
            System.out.println("sd卡卸下来");
        }
    }

}
