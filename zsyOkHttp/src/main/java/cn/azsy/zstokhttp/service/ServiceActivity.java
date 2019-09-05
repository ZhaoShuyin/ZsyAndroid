package cn.azsy.zstokhttp.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import cn.azsy.zstokhttp.R;

/**
 * Created by zsy on 2017/7/25.
 */

public class ServiceActivity extends AppCompatActivity {

    private static final String TAG = "ZService";

    TextView show;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceactivity);
        show = (TextView) findViewById(R.id.tv_sercive);
    }


    public void btA(View view) {
        startService(new Intent(this, ZService.class));
    }

    public void btB(View view) {
        stopService(new Intent(this, ZService.class));
    }


    private MyConn conn;

    public void btC(View view) {
        conn = new MyConn();
        //绑定一个服务
        bindService(new Intent(this, ZService.class), conn, BIND_AUTO_CREATE);
    }

    public void btD(View view) {
        unbindService(conn);
    }

    public void btE(View view) {
    }

    public void btF(View view) {
    }

    public void btG(View view) {
    }

    private class MyConn implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "oMyConn接收Binder == " + service.toString());
            ZService.ZBinder zBinder = (ZService.ZBinder) service;
            zBinder.setiService(new ZService.IService() {
                @Override
                public void callShow(final String s) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            show.setText(s);
                        }
                    });
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected  ");
        }
    }

}
