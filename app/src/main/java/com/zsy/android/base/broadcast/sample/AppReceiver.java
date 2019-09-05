package com.zsy.android.base.broadcast.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:
 * </p>
 * 清单文件注册
 * <receiver android:name="AppReceiver">
 * <intent-filter >
 * <action android:name="android.intent.action.PACKAGE_ADDED"/>
 * <action android:name="android.intent.action.PACKAGE_INSTALL"/>
 * <action android:name="android.intent.action.PACKAGE_REMOVED"/>
 * <data android:scheme="package"/>
 * </intent-filter>
 * <p>
 * </receiver>
 *
 * @author Zsy
 * @date 2019/8/26 14:20
 */

public class AppReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("有应用被卸载安装了");
        Uri data = intent.getData();
        String action = intent.getAction();
        if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
            System.out.println("added" + data);
        } else if ("android.intent.action.PACKAGE_INSTALL".equals(action)) {
            //安装的时候走的是PACKAGE_ADDED 这个广播  PACKAGE_INSTALL是系统预留的字段
            System.out.println("INSTALL");
        } else if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
            System.out.println("REMOVED" + data);
        }
    }
}
