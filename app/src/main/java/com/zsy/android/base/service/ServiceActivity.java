package com.zsy.android.base.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zsy.android.R;
import com.zsy.android.base.service.sample.IComService;
import com.zsy.android.base.service.sample.IVipService;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:Service服务的使用
 * </p>
 *
 * @author Zsy
 * @date 2019/8/26 9:30
 */

public class ServiceActivity extends Activity {

    String TAG = "ZService";
    private TextView textView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        textView = findViewById(R.id.service_show);
    }


    public void start(View view) {
        Intent service = new Intent(this, ZService.class);
        startService(service);
    }

    public void stop(View view) {
        Intent service = new Intent(this, ZService.class);
        stopService(service);
    }


    public void bindstart(View view) {
        //创建服务的意图对象
        Intent service = new Intent(this, ZService.class);
        conn = new MyConn();
        //最后一个参数 flag标志位  一般传BIND_AUTO_CREATE 当连接之后 会创建service
        bindService(service, conn, BIND_AUTO_CREATE);
    }

    public void bindstop(View view) {
        unbindService(conn);
    }

    public void bindmethod(View view) {
        if (zBinder!=null){
            zBinder.start();
        }else{
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }
    }

    public void commonmethod(View view){
        if (comService!=null){
            comService.common();
        }else{
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }
    }

    public void vipmethod(View view){
        if (vipService!=null){
            vipService.vip();
        }else{
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }
    }

    private MyConn conn;

    private ZService.ZBinder zBinder;
    private IComService comService;
    private IVipService vipService;
    private class MyConn implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            Log.e(TAG, "oMyConn接收Binder == " + service.toString());
            zBinder = (ZService.ZBinder) service;
            comService = (IComService) service;
            vipService = (IVipService) service;
            //设置回调
            zBinder.setiService(new ZService.IService() {
                @Override
                public void callShow(final String s) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(s);
                        }
                    });
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected  ");
        }
    }
}
