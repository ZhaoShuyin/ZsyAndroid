package com.zsy.android.base.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.zsy.android.base.db.helper.MyOpenHelper;

/**
 * Content提供者
 * 清单文件配置
 * <provider
 * android:name=".api.contentprovider.MyProvider"
 * android:authorities="cn.zsy.provider"
 * android:exported="true">
 * </provider>
 * <p>
 * 外界进程通过 URI(统一资源标识符) 找到对应的ContentProvider & 其中的数据，再进行数据操作
 * <p>
 * * Android 提供了3个用于辅助ContentProvide的工具类：
 * * ContentUris ContentUris.withAppendedId(uri, 7)添加参数
 * *             Uri.parse("Uri/7")解析参数
 * * UriMatcher  URIMatcher.addURI()添加匹配规则
 * *             URIMatcher.match() 解析匹配规则
 * *ContentObserver
 */
public class MyProvider extends ContentProvider {

    //URI 统一资源标识符  url 子父类 URI 爹  url儿子 http:// ftp:// https://
    //URI 可以自定义协议  cn.zsy.provider/update1
    private MyOpenHelper openHelper;
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int MATCH_UPDATE = 0;
    private static final int MATCH_QUERY = 1;
    private static final int MATCH_INSERT = 0;
    private static final int MATCH_DELETE = 0;

    static {
        //通过uri匹配器 添加匹配的路径规则 cn.zsy.provider/query 如果调用match方法匹配上了这个规则 那么就会返回MATCH_UPDATE
        //如果传入的uri没有匹配任何预先加入的uri 就会返回NO_MATCH
        sURIMatcher.addURI("cn.zsy.provider", "query", MATCH_QUERY);
        sURIMatcher.addURI("cn.zsy.provider", "update", MATCH_UPDATE);
        sURIMatcher.addURI("cn.zsy.provider", "insert", MATCH_INSERT);
        sURIMatcher.addURI("cn.zsy.provider", "delete", MATCH_DELETE);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int result = sURIMatcher.match(uri);
        if (result == MATCH_DELETE) {
            SQLiteDatabase db = openHelper.getReadableDatabase();
            int delete = db.delete("info", selection, selectionArgs);
            return delete;
        } else {
            return 0;
        }

    }

    @Override
    public String getType(Uri uri) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int result = sURIMatcher.match(uri);
        if (result == MATCH_INSERT) {
            SQLiteDatabase db = openHelper.getReadableDatabase();
            long insert = db.insert("info", null, values);
            //通过内容解析者 发送通知 告知内容发生变化 第一个参数 uri 通过这个uri确定是哪个内容提供者对应数据发生了改变
            //第二个 参数 ContentObserver 如果传入了一个内容观察者对象 那么 只有这个内容观察者能收到变化的消息
            //如果传了null 只要观察着第一个参数传入的uri的内容观察者都能收到变化的消息
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.parse(String.valueOf(insert));
        } else {
            return null;
        }
    }

    @Override
    public boolean onCreate() {
        openHelper = new MyOpenHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        //第一个参数 就是用来匹配具体路径 决定是否让对方访问相应方法
        int result = sURIMatcher.match(uri);
        if (result == MATCH_QUERY) {
            SQLiteDatabase db = openHelper.getReadableDatabase();
            Cursor cursor = db.query("info", projection, selection, selectionArgs, null, null, sortOrder);
            return cursor;
        } else {
            throw new IllegalStateException("口令不正确");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int result = sURIMatcher.match(uri);
        if (result == MATCH_UPDATE) {
            SQLiteDatabase db = openHelper.getReadableDatabase();
            int update = db.update("info", values, selection, selectionArgs);
            return update;
        } else {
            return 0;
        }
    }
}