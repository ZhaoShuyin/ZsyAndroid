package com.zsy.android.base.contentprovider;

import android.app.Activity;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zsy.android.R;
import com.zsy.android.base.db.helper.MyOpenHelper;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:
 * </p>
 *
 * @author Zsy
 * @date 2019/7/17 10:19
 */

public class ContentProviderActivity extends Activity {

    private TextView tvShow;
    private MyOpenHelper helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        tvShow = findViewById(R.id.tv_content_provider_show);
        helper = new MyOpenHelper(this);
        Uri uri = Uri.parse("content://cn.zsy.provider");
        MyObserver observer = new MyObserver(new Handler());
        //通过contentresolver来注册内容观察者
        getContentResolver().registerContentObserver(uri, true, observer);
    }


    private class MyObserver extends ContentObserver {

        public MyObserver(Handler handler) {
            super(handler);
            // TODO Auto-generated constructor stub
        }
        @Override
        public void onChange(boolean selfChange, Uri uri) {
            Log.e("contentobserver","Content观察者 uri = "+uri+"变化了");
            //收到数据库内容变化的通知 在界面上刷新 展示最新的数据
        }
    }


    public void showContent(View view){
        StringBuilder buidler = new StringBuilder();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from info", null);
        while (cursor.moveToNext()){
            String name = cursor.getString(1);
            String phone = cursor.getString(2);
            buidler.append(name +" - "+phone +"\n");

        }
        tvShow.setText(buidler.toString());
    }

    public void smscontect(View view){
        startActivity(new Intent(this,SmsAndContactActivity.class));
    }

}
