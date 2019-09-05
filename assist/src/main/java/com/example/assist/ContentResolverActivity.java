package com.example.assist;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:Content解析者,操作其他应用Content提供者的方法
 * <p>
 * Android 提供了3个用于辅助ContentProvide的工具类：
 * *ContentUris
 * * UriMatcher
 * *ContentObserver
 * </p>
 *
 * @author Zsy
 * @date 2019/7/17 9:57
 */

public class ContentResolverActivity extends Activity {

    private SQLiteDatabase db;
    private TextView tvShow;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_resolver);
        tvShow = findViewById(R.id.tv_content_resolver_show);
        editText = findViewById(R.id.et_content_resolver);
        // String path = "data/data/cn.zsy.providedata/databases/zsy.db";
        // db = SQLiteDatabase.openDatabase(path, null,SQLiteDatabase.OPEN_READONLY);
    }

    public void query(View v) {
        // 获取contentresolver对象 内容解析者
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://cn.zsy.provider/query");
        Cursor cursor = resolver.query(uri, null, null, null, null);
        // Cursor cursor = db.query("info", null, null, null, null, null, null);
        if (cursor == null) {
            tvShow.setText("cursor == null");
            return;
        }
        StringBuilder builder = new StringBuilder();
        while (cursor.moveToNext()) {
            String name1 = cursor.getString(0);
            String name2 = cursor.getString(1);
            String name3 = cursor.getString(2);
            builder.append("<" + name1 + " - " + name2 + " - " + name3 + ">\n");
        }
        tvShow.setText(builder.toString());
    }

    public void insert(View v) {
        //获取内容解析者
        ContentResolver resolver = getContentResolver();
        Uri url = Uri.parse("content://cn.zsy.provider/insert");
        ContentValues values = new ContentValues();
        values.put("name", getData());
        values.put("phone", getNum() + "");
        Uri insert = resolver.insert(url, values);
        tvShow.setText("values==" + values + "\nresult= " + insert.toString());
    }

    public void delete(View v) {
        ContentResolver resolver = getContentResolver();
        Uri url = Uri.parse("content://cn.zsy.provider/delete");
        //要删除的条件 delete from info where name = ?  where不用写
        String where = "name = ?";
        String name = getData();
        String[] selectionArgs = {name};
        int delete = resolver.delete(url, where, selectionArgs);
        tvShow.setText("删除了< selectionArgs==" + Arrays.toString(selectionArgs) + "> " + +delete + "条");
    }

    public void update(View v) {
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://cn.zsy.provider/update");
        ContentValues values = new ContentValues();
        String name = getData();
        values.put("name", name);
        values.put("phone", String.valueOf(getNum()));
        String where = "name = ?";
        String[] selectionArgs = {name};
        int update = resolver.update(uri, values, where, selectionArgs);
        tvShow.setText("更新了< values==" + values + "> -> < selectionArgs==" + Arrays.toString(selectionArgs) + " > " + update + "条");
    }

    public String getData() {
        String trim = editText.getText().toString().trim();
        return trim.length() == 0 ? "empty" : trim;
    }


    public int getNum() {
        Random random = new Random();
        return random.nextInt(5) + 1;
    }
}
