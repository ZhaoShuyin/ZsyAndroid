package com.zsy.android.base.broadcast.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 监听息屏广播
 * 动态注册
 * receiver = new ScreenReceivcer();
 * IntentFilter filter = new IntentFilter();
 * filter.addAction("android.intent.action.SCREEN_OFF");
 * filter.addAction("android.intent.action.SCREEN_ON");
 * registerReceiver(receiver, filter);
 */
public class ScreenReceivcer extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("android.intent.action.SCREEN_OFF".equals(action)) {
            Log.e("ScreenReceivcer", "息屏了: ");
        } else {
            Log.e("ScreenReceivcer", "亮屏了: ");
        }
    }
}
