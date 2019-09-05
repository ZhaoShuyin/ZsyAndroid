package com.zsy.zlib.mobile;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by zsy on 2018/6/28.
 */
public class SpeedService extends Service {
    private final String TAG = "SpeedService";
    TextView tvSpeed;
    TextView tvLog;
    Button btClear;
    int statusBarHeight = -1; //状态栏高度

    private Handler mHnadler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    String text = msg.obj.toString();
                    tvSpeed.setText(text);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private NetSpeedUtils netSpeedUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        showFlowWindow();
        netSpeedUtils = new NetSpeedUtils(this, mHnadler);
        netSpeedUtils.startShowNetSpeed();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.removeView(tvSpeed);
        netSpeedUtils.stopShowNetSpeed();
        Log.d(TAG, "onDestroy");
    }

    /**
     * 展示悬浮窗
     */
    public void showFlowWindow() {
        final WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        tvSpeed = new TextView(getApplicationContext());
        tvSpeed.setTextColor(Color.BLACK);
        tvSpeed.setBackgroundColor(0xff99D9EA);
        tvSpeed.setWidth(200);
        tvSpeed.setHeight(80);
        tvSpeed.setText("speed");
        /*final View inflate = LayoutInflater.from(MyApplication.getApplication()).inflate(R.layout.float_window, null);
        tvSpeed = (TextView) inflate.findViewById(R.id.tv_speed);
        tvLog = (TextView) inflate.findViewById(R.id.tv_log);
        btClear = (Button) inflate.findViewById(R.id.vt_clear_log);*/
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.format = PixelFormat.RGBA_8888;
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.width = 200;
        params.height = 60;
        params.x = 300;
        params.y = 200;
        windowManager.addView(tvSpeed, params);

        //用于检测状态栏高度.
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        tvSpeed.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getRawX() - 100;
                int y = (int) event.getRawY() - 30-statusBarHeight;

                params.x = x;//宽度一半,中心点
                params.y = y;//高度一半,中心点(减去状态栏高度)

                windowManager.updateViewLayout(tvSpeed, params);
//                Log.i(TAG, "< " + params.x + " > == " + (int) event.getRawX() + "- 100\n" +
//                        "< " + params.y + " > === " + (int) event.getRawY() + " - 40 - " + statusBarHeight);
                return false;
            }



        });
    }

}
