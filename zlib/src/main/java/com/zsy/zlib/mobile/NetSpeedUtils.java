package com.zsy.zlib.mobile;

import android.content.Context;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zsy on 2016/10/13.
 */
public class NetSpeedUtils {
    private Context context;
    private Handler mHandler;

    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;
    private Timer timer;

    public NetSpeedUtils(Context context, Handler mHandler) {
        this.context = context;
        this.mHandler = mHandler;
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            showNetSpeed();
        }
    };

    public void startShowNetSpeed() {
        lastTotalRxBytes = getTotalRxBytes();
        lastTimeStamp = System.currentTimeMillis();
        timer = new Timer();
        timer.schedule(task, 1000, 1000); // 1s后启动任务，每1s执行一次
    }

    public void stopShowNetSpeed() {
        timer.cancel();
    }

    private long getTotalRxBytes() {
        return TrafficStats.getUidRxBytes(context.getApplicationInfo().uid) == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalRxBytes() / 1024);//转为KB
    }

    /**
     *
     */
    private void showNetSpeed() {
        long nowTotalRxBytes = getTotalRxBytes();
        long nowTimeStamp = System.currentTimeMillis();
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换
        long speed2 = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 % (nowTimeStamp - lastTimeStamp));//毫秒转换

        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;

        Message msg = mHandler.obtainMessage();
        msg.what = 100;
        msg.obj = String.valueOf(speed) + "." + String.valueOf(speed2) + " kb/s";
        mHandler.sendMessage(msg);//更新界面
    }
}
