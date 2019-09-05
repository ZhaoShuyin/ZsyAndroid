package cn.azsy.zstokhttp.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by zsy on 2017/7/25.
 */

public class ZBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        String resultData = getResultData();
//        Toast.makeText(context, resultData, Toast.LENGTH_SHORT).show();
//        Log.i("ZBroadCastReceiver", resultData+"+Z");
//        setResultData(resultData+"+Z");
        String data = intent.getStringExtra("data");
        Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
    }
}
