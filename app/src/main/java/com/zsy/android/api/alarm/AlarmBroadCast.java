package com.zsy.android.api.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.zsy.zlib.phone.NotifyUtil;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:
 * </p>
 *
 * @author Zsy
 * @date 2019/6/28 14:22
 */

public class AlarmBroadCast extends BroadcastReceiver {

    String TAG = "AlarmBroadCast";

    public AlarmBroadCast() {
        super();
        Log.e(TAG, "AlarmBroadCast: 创建了");
    }




    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String text = intent.getStringExtra("text");
        NotifyUtil.SimpleNotification(title == null ? "title" : title, text == null ? "text" : text);
    }

    @Override
    public IBinder peekService(Context myContext, Intent service) {
        return super.peekService(myContext, service);
    }
}
