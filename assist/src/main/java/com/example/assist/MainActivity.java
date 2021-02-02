package com.example.assist;

import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assist.util.NotifyUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

//import com.zsy.mavenlib.TestUtil;
// getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST);

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv_show);
        findViewById(R.id.bt_01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Properties properties = new Properties();
//                try {
////                    properties.load(getAssets().open("region.properties"));
////                    String property = properties.getProperty("110102");
//                    InputStream inputStream = getAssets().open("region.properties");
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                    properties.load(bufferedReader);
//                    String property = properties.getProperty("110102");
//                    textView.setText(property);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                //原型模式clone
                Intent intent = new Intent(MainActivity.this, CallAidlActivity.class);
                Log.e("clone", "intent: == " + intent);
                Intent intent2 = (Intent) intent.clone();
                Log.e("clone", "intent2: == " + intent2);
                startActivity(intent2);
            }
        });
        //aidl
        findViewById(R.id.bt_aidl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CallAidlActivity.class));
            }
        });
        //内容解析者
        findViewById(R.id.bt_content_resolver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ContentResolverActivity.class));
            }
        });
        //注册内容观察者
        findViewById(R.id.bt_content_observer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://cn.zsy.provider");
                observer = new MyObserver(new Handler());
                //通过contentresolver来注册内容观察者
                getContentResolver().registerContentObserver(uri, true, observer);
            }
        });
        //注销内容观察者
        findViewById(R.id.bt_content_observer_false).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContentResolver().unregisterContentObserver(observer);
            }
        });
        NotifyUtil.init(this);
    }

    private MyObserver observer;

    /**
     * content观察者
     */
    private class MyObserver extends ContentObserver {

        public MyObserver(Handler handler) {
            super(handler);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            //收到数据库内容变化的通知 在界面上刷新 展示最新的数据
            Log.e("ContentObserver", "uri = " + uri + "变化了");
        }
    }


    /**
     * 配置清单文件 (跳转到其他App的Activity)
     * <intent-filter>
     * <action android:name="dakaduobao.goods" />
     * <data android:scheme="goods" />
     * <category android:name="android.intent.category.DEFAULT" />
     * </intent-filter>
     * <p>
     * 在打开的界面
     * intent.getData().getHost() //获取数据
     *
     * @param view
     */
    public void openOtherActiity(View view) {
        try {
            Intent intent = new Intent("dakaduobao.goods", Uri.parse("goods://1"));
//                    intent.setAction("dakaduobao.goods")
//                    intent.setData(Uri.parse("goods://1"));
//                    intent.setData().
            //调用A中的Main
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void notifyA(View view) {
        NotifyUtil.SimpleNotification("标题", "简单");
    }

    public void notifyB(View view) {
        Intent intent = new Intent();
        intent.setClass(this, CallAidlActivity.class);
        NotifyUtil.IntentNotification("标题", "意图", intent);
    }

    public void notifyC(View view) {
        Intent intent = new Intent();
        intent.setClass(this, CallAidlActivity.class);
        NotifyUtil.ProgressNotification("标题", ++id);
    }

    public void notifyD(View view) {
        Intent intent = new Intent();
        intent.setClass(this, CallAidlActivity.class);
        NotifyUtil.FixedNotification("标题", "固定通知", intent);
    }

    public void notifyE(View view) {
        Intent intent = new Intent();
        intent.setClass(this, CallAidlActivity.class);
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        NotifyUtil.InboxNotification(
                "title",
                "text",
                "lineTitle",
                "summaryText",
                list,
                intent);
    }

    int id;

    public void notifyF(View view) {
        NotifyUtil.clearNotification(++id);
        Toast.makeText(this, "取消" + id, Toast.LENGTH_SHORT).show();
    }


    public void notifyG(View view) {
        NotifyUtil.createAllNotificationChannels(this);
    }

}
