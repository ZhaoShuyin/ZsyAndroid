package cn.azsy.zstokhttp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by zsy on 2017/5/25.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(Context context, String name, int version){
        this(context,name,null,version);
    }

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.d("datadatabase", "创建名为<"+name+">的数据库文件");
    }

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
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

    public Long dataQuery(String table,
                          String[] columns,
                          String selection,
                          String[] selectionArgs,
                          String groupBy,
                          String having,
                          String orderBy){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
        while(cursor.moveToNext()){
            String name = cursor.getString(0);
            String phone = cursor.getString(1);
            System.out.println("name"+name+"phone"+phone);
        }
        cursor.close();
        database.close();
        return null;
    }

    public long dataInsert(String tableName,String nullColumnHack,String[]valsues){
        SQLiteDatabase database = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("name","张三");
        values.put("photo","1861681685555");
        long insert = database.insert(tableName, nullColumnHack, values);
        return insert;
    }

}
