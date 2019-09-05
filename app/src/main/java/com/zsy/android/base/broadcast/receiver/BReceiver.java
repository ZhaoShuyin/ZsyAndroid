package com.zsy.android.base.broadcast.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by zsy on 2017/7/25.
 */
public class BReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String resultData = getResultData();
        String msg = resultData + "+B";
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        Log.e("ZBroadCastReceiver", msg);
        setResultData(msg);
    }
}
