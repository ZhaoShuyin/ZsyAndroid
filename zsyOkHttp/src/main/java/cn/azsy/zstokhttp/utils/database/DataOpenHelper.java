package cn.azsy.zstokhttp.utils.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by zsy on 2017/5/25.
 */

public class DataOpenHelper extends SQLiteOpenHelper {

    public DataOpenHelper(Context context, String name, int version){
        this(context,name,null,version);
    }

    public DataOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.d("datadatabase", "创建名为<"+name+">的数据库文件");
    }

    public DataOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String creatSql = "create table info(_id integer primary key autoincrement,name varchar(20),phone varchar(20))";
        db.execSQL(creatSql);
        Log.d("datadatabase", "创建名为<info>的表");
    }

    /**
     * 升级数据库时的方法
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 降级数据库时的方法
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
