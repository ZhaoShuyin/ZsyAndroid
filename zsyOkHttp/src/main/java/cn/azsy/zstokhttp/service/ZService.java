package cn.azsy.zstokhttp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by zsy on 2017/7/25.
 */

public class ZService extends Service {

    private static final String TAG = "ZService";

    Handler handler = new Handler() {
        int i=0;
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    handler.sendEmptyMessageDelayed(1, 1000);
                    break;
                case 1:
                    i++;
                    if (binder != null) {
                        binder.call(i+"a");
                    }
                    handler.sendEmptyMessageDelayed(1, 1000);
                    break;
                case 2:

                    break;
            }
        }
    };
    private ZBinder binder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        binder = new ZBinder();
        Log.i(TAG, "onBind:= Service创建 binder==" + binder.toString());
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler.sendEmptyMessage(0);
        Log.i(TAG, "onCreate: " + this.toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i(TAG, "onStart: ");
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i(TAG, "onRebind: ");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    public interface IService{
        public void callShow(String s);
    }

    public class ZBinder extends Binder {
        public IService iService;
        public void setiService(IService iService){
            this.iService = iService;
        }

        public void call(String s) {
            if (iService!=null){
                iService.callShow(s);
            }
        }
    }
}
