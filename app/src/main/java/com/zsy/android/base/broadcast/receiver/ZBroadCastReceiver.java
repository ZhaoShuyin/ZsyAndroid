package com.zsy.android.base.broadcast.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by zsy on 2017/7/25.
 */
public class ZBroadCastReceiver extends BroadcastReceiver {

    TextView textView;

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
//        String resultData = getResultData();
//        Toast.makeText(context, resultData, Toast.LENGTH_SHORT).show();
//        Log.i("ZBroadCastReceiver", resultData+"+Z");
//        setResultData(resultData+"+Z");
        String data = intent.getStringExtra("data");
        Log.e("ZBroadCastReceiver", "onReceive: data=="+data );
        Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
        if (textView!=null){
            textView.setText(data);
        }
    }
}
