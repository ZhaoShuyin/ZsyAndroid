package cn.azsy.zstokhttp.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenReceivcer extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if("android.intent.action.SCREEN_OFF".equals(action)){
			Log.i("ScreenReceivcer", "息屏了: ");
		}else{
			Log.i("ScreenReceivcer", "亮屏了: ");
		}
	}
}
