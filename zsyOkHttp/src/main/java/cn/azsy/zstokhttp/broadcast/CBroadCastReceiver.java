package cn.azsy.zstokhttp.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by zsy on 2017/7/25.
 */

public class CBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String resultData = getResultData();
        Toast.makeText(context, resultData, Toast.LENGTH_SHORT).show();
        Log.i("ZBroadCastReceiver", resultData+"+C");
        setResultData(resultData+"+C");
    }
}
