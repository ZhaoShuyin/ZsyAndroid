package com.zsy.android.base.broadcast.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zsy.android.MainActivity;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:
 * </p>
 * 清单文件注册
 * <receiver android:name=".BootReceiver">
 * <intent-filter >
 * <action android:name="android.intent.action.BOOT_COMPLETED"/>
 * </intent-filter>
 * </receiver>
 *
 * @author Zsy
 * @date 2019/8/26 14:12
 */
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("收到了开机广播");
        //在开机广播中可以处理 把跟服务端建立的长连接 在系统一运行起来的时候一同运行起来
        Intent intent2 = new Intent(context, MainActivity.class);
        //在广播接收者中创建第一个activity 此时没有activity的任务栈
        //所以intent通过设置一个flag的方式通知系统在创建activity之前 先创建一个新的任务栈
        //并且把activity放到这个任务栈中
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent2);
    }
}