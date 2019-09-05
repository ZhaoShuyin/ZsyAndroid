package cn.azsy.zstokhttp.broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.azsy.zstokhttp.R;

/**
 * Created by zsy on 2017/7/25.
 */

public class BroadcastActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);


    }

    public void BBTA(View view) {
        Intent intent = new Intent();
//        intent.setAction("zbrecivcer");
        intent.putExtra("data", "发送广播了");
        sendBroadcast(intent);
    }


    public void BBTB(View view) {
        Intent intent = new Intent();
        intent.setAction("zbrecivcer");
        intent.putExtra("data", "发送广播了");
        sendBroadcast(intent);
    }


    public void BBTC(View view) {
        //意图 通过意图指定一个广播的action
        Intent intent = new Intent();
        intent.setAction("abrecivcer");
        //接收这个广播需要的权限
        String receiverPermission = null;
        //最终的receiver 可以获取到优先级最低的广播接收者接收到的数据
        BroadcastReceiver resultReceiver = new DBroadCastReceiver();
        //处理resultReceiver 使用的handler  如果传null 最终这个resultReceiver会在主线程中处理
        Handler scheduler = null;
        int initialCode = Activity.RESULT_OK;
        //初始的数据
        String initialData = "有序广播";
        //初始化的时候额外的数据
        Bundle initialExtras = null;
        sendOrderedBroadcast(intent, receiverPermission, resultReceiver, scheduler, initialCode, initialData, initialExtras);
    }

    ScreenReceivcer receiver;

    public void BBTD(View view) {
        receiver = new ScreenReceivcer();
        //创建一个意图过滤器 给广播接收者添加要响应的具体广播事件
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.SCREEN_OFF");
        filter.addAction("android.intent.action.SCREEN_ON");
         //通过代码 动态注册广播接收者
        registerReceiver(receiver, filter);
    }

    public void BBTE(View view) {
        unregisterReceiver(receiver);
    }
}
