package com.zsy.android.base.broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.zsy.android.R;
import com.zsy.android.base.broadcast.receiver.AReceiver;
import com.zsy.android.base.broadcast.receiver.BReceiver;
import com.zsy.android.base.broadcast.receiver.CReceiver;
import com.zsy.android.base.broadcast.receiver.DReceiver;
import com.zsy.android.base.broadcast.receiver.ZBroadCastReceiver;
import com.zsy.android.base.broadcast.sample.ScreenReceivcer;


/**
 * 广播的使用
 * Created by zsy on 2017/7/25.
 */

public class BroadcastActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private ZBroadCastReceiver castReceiver;
    private ScreenReceivcer receiver;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        textView = findViewById(R.id.broadcast_show);
        findViewById(R.id.receiver_a).setOnClickListener(this);
        findViewById(R.id.receiver_b).setOnClickListener(this);
        findViewById(R.id.receiver_c).setOnClickListener(this);
        findViewById(R.id.receiver_d).setOnClickListener(this);
        findViewById(R.id.receiver_ua).setOnClickListener(this);
        findViewById(R.id.receiver_ub).setOnClickListener(this);
        findViewById(R.id.receiver_uc).setOnClickListener(this);
        findViewById(R.id.receiver_ud).setOnClickListener(this);
    }

    //注册简单广播
    public void resgist(View view) {
        castReceiver = new ZBroadCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("zbrecivcer");
        registerReceiver(castReceiver, filter);
    }

    //注销简单广播
    public void unresgist(View view) {
        unregisterReceiver(castReceiver);
    }

    //发送简单广播
    public void BBTA(View view) {
        Intent intent = new Intent();
        intent.setAction("zbrecivcer");
        intent.putExtra("data", "发送广播了");
        sendBroadcast(intent);
    }

    //无序广播
    public void BBTB(View view) {
        Intent intent = new Intent();
        intent.setAction("zbrecivcer");
        intent.putExtra("data", "无序广播");
        sendBroadcast(intent);
    }


    //有序广播
    public void BBTC(View view) {
        Intent intent = new Intent();
        intent.setAction("abrecivcer");
        //处理resultReceiver 使用的handler  如果传null 最终这个resultReceiver会在主线程中处理
        Handler scheduler = null;
        int initialCode = Activity.RESULT_OK;
        String initialData = "有序广播";  //初始的数据
        Bundle initialExtras = null; //初始化的时候额外的数据
        sendOrderedBroadcast(intent,
                null, //接收这个广播需要的权限
                new DReceiver(),      // //最终的receiver 可以获取到优先级最低的广播接收者接收到的数据
                scheduler,
                initialCode,
                initialData,
                initialExtras);
    }

    BroadcastReceiver rA;
    BroadcastReceiver rB;
    BroadcastReceiver rC;
    BroadcastReceiver rD;

    @Override
    public void onClick(View v) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("abrecivcer");
        switch (v.getId()) {
            case R.id.receiver_a:
                rA = new AReceiver();
                filter.setPriority(1000);
                registerReceiver(rA, filter);
                break;
            case R.id.receiver_b:
                rB = new BReceiver();
                filter.setPriority(800);
                registerReceiver(rB, filter);
                break;
            case R.id.receiver_c:
                rC = new CReceiver();
                filter.setPriority(600);
                registerReceiver(rC, filter);
                break;
            case R.id.receiver_d:
                rD = new DReceiver();
                filter.setPriority(400);
                registerReceiver(rD, filter);
                break;
            case R.id.receiver_ua:
                unregisterReceiver(rA);
                break;
            case R.id.receiver_ub:
                unregisterReceiver(rB);
                break;
            case R.id.receiver_uc:
                unregisterReceiver(rC);
                break;
            case R.id.receiver_ud:
                unregisterReceiver(rD);
                break;
        }
    }

    //注册息屏广播监听
    public void BBTD(View view) {
        receiver = new ScreenReceivcer();
        //创建一个意图过滤器 给广播接收者添加要响应的具体广播事件
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.SCREEN_OFF");
        filter.addAction("android.intent.action.SCREEN_ON");
        //通过代码 动态注册广播接收者
        registerReceiver(receiver, filter);
    }

    //注销息屏广播监听
    public void BBTE(View view) {
        unregisterReceiver(receiver);
    }


}
