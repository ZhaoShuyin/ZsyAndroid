package com.zsy.android.base.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TransactionHelper extends SQLiteOpenHelper {

    public TransactionHelper(Context context) {
        super(context, "transaction.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table count (_id integer primary key autoincrement,name varchar(20),phone varchar(20),money varchar(20))";
        db.execSQL(sql);
        db.execSQL("insert into count ('name','phone','money') values ('张三','123','2000')");
        db.execSQL("insert into count ('name','phone','money') values ('李四','123','5000')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
