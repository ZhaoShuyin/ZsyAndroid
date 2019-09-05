package com.zsy.android.base.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.zsy.android.base.service.sample.IComService;
import com.zsy.android.base.service.sample.IVipService;

/**
 * Service样例
 * * ========== Start 和 Stop =========
 * E: onCreate:
 * E: onStart:
 * E: onDestroy:
 * <p>
 * * ========== Bind 和 UnBind =========
 * E: onCreate:
 * E: onBind:
 * E: onUnbind:
 * E: onDestroy:
 * * =======Start 和 Bind 至 UnBind 和 Stop===========
 * E: onCreate:
 * E: onStart:
 * E: onBind:=
 * E: onUnbind:
 * E: onDestroy:
 * ======= Bind 和 Start 至 UnBind 和 Stop===========
 * E: onCreate:
 * E: onBind:
 * E: onStart:
 * E: onUnbind:
 * E: onDestroy:
 * Created by zsy on 2017/7/25.
 */
public class ZService extends Service {

    private static final String TAG = "ZService";

    Handler handler = new Handler() {
        int i = 0;

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
                        binder.call(i + " s");
                    }
                    if (i <= 100) {
                        handler.sendEmptyMessageDelayed(1, 1000);
                    }
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
        Log.e(TAG, "onBind:= Service创建 binder==" + binder.toString());
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: " + this.toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e(TAG, "onStart: ");
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.e(TAG, "onRebind: ");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    public interface IService {
        public void callShow(String s);
    }

    public class ZBinder extends Binder implements IComService, IVipService {
        public IService iService;

        public void setiService(IService iService) {
            this.iService = iService;
        }

        public void start() {
            handler.sendEmptyMessage(0);
        }

        public void call(String s) {
            if (iService != null) {
                iService.callShow(s);
            }
        }

        @Override
        public void vip() {
            sVip();
        }

        @Override
        public void common() {
            sCommon();
        }
    }


    private void sVip() {
        if (binder != null) {
            binder.call("vip");
        }
    }

    private void sCommon() {
        if (binder != null) {
            binder.call("普通");
        }
    }
}
