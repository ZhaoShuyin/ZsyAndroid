package com.zsy.android.base.broadcast.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

/**
 * 短信广播监听
 * 清单文件注册
 * <receiver android:name=".SMSReceiver">
 * <intent-filter >
 * <action android:name="android.provider.Telephony.SMS_RECEIVED"/>
 * </intent-filter>
 * </receiver>
 */
public class SMSReceiver extends BroadcastReceiver {
    String address;
    String body;

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("短信来了");
        //短信的格式 text 不支持中文
        //pdu protocal data unit

        Object[] obj = (Object[]) intent.getExtras().get("pdus");
        for (Object obj1 : obj) {
            //创建短信对象
            SmsMessage message = SmsMessage.createFromPdu((byte[]) obj1);
            //获取短信内容和短信发送者
            body = message.getMessageBody();
            address = message.getOriginatingAddress();
            System.out.println("发送号码:" + address + "短信内容" + body);
            //判断是不是我的短信中心发来的短信

        }
        if ("12345".equals(address)) {
            //通知界面把短信内容设置到界面上
            Intent intent2 = new Intent();
            //给意图设置一个动作
            intent2.setAction("cn.itcast.getcode");
            //通过意图携带数据
            intent2.putExtra("code", body);
            //通过无序广播的形式发送意图
            context.sendBroadcast(intent2);
        }

    }

}
