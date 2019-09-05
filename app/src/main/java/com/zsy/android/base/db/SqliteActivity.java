package com.zsy.android.base.db;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zsy.android.R;
import com.zsy.android.base.db.helper.MyOpenHelper;
import com.zsy.android.base.db.helper.TransactionHelper;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:Sqlit 数据库操作样例
 * </p>
 *
 * @author Zsy
 * @date 2019/7/3 14:46
 */

public class SqliteActivity extends Activity {

    TextView tvShow, tVCommand;
    EditText editText;
    MyOpenHelper openHelper;
    TransactionHelper transactionHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openHelper = new MyOpenHelper(this);
        transactionHelper = new TransactionHelper(this);
        setContentView(R.layout.activity_db);
        tVCommand = findViewById(R.id.tv_command);
        tvShow = findViewById(R.id.tv_show);
        editText = findViewById(R.id.et_db);
    }

    private String[] names = new String[]{"张三", "李四", "王五", "赵六", "刘七", "周八", "林九"};

    private String getName() {
        String trim = editText.getText().toString().trim();
        if (trim.length() == 0) {
            int anInt = new Random().nextInt(names.length);
            return names[anInt];
        } else {
            return trim;
        }
    }

    private String getNumber() {
        return String.valueOf(System.currentTimeMillis());
    }

    // 插入数据的点击事件
    public void insert(View v) {
        SQLiteDatabase database = openHelper.getReadableDatabase();//只有获取DataBase后onCreat方法执行,创建数据库
        String sql = "insert into info(name,phone) values('" + getName() + "','" + getNumber() + "')";
        database.execSQL(sql);
        tVCommand.setText(sql);
        database.close();
    }

    // 查询数据的点击事件
    public void query(View v) {
        SQLiteDatabase database = openHelper.getReadableDatabase();
        String sql = "select * from info";
        String[] args = new String[]{"王五"};
        tVCommand.setText(sql);
        Cursor cursor = database.rawQuery(sql, null);
        StringBuilder builder = new StringBuilder();
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("_id"));
            String name = cursor.getString(1);
            String phone = cursor.getString(2);
            String x = "{ id:" + id + ", name:" + name + ", phone:" + phone + " }";
            builder.append(x + "\n");
        }
        cursor.close();
        database.close();
        tvShow.setText(builder.toString());
    }

    // 更新数据的点击事件
    public void update(View v) {
        SQLiteDatabase database = openHelper.getReadableDatabase();
        String sql = "update info set phone='" + getNumber() + "'where name='" + getName() + "'";
        tVCommand.setText(sql);
        database.execSQL(sql);
        database.close();
    }

    // 删除数据的点击事件
    public void delete(View v) {
        SQLiteDatabase database = openHelper.getReadableDatabase();
        String sql = "delete from info where name='" + getName() + "';";
        tVCommand.setText(sql);
        database.execSQL(sql);
        database.close();
    }



    public void insert1(View v) {
        SQLiteDatabase database = openHelper.getReadableDatabase();
        String table = "info";
        //为了避免出现列名为null的情况 搞这个nullColumnHack 给它传一个值可以为null的字段名
        String nullColumnHack = null;
        ContentValues values = new ContentValues();
        values.put("name", getName());
        values.put("phone", getNumber());
        long insert = database.insert(table, nullColumnHack, values);//插入的数据对应的id
        tVCommand.setText(values.toString() + "\n result(id)==" + insert);
        database.close();

    }

    public void query1(View v) {
        SQLiteDatabase database = openHelper.getReadableDatabase();
        String table = "info";
        String[] columns = new String[]{"_id", "name", "phone"};  //要查询的列名
        String selection = null;                           //要查询的where 条件 不要写where 要有?
        String[] selectionArgs = null;                     //跟where条件中? 对应的值
        String groupBy = null;                             //分组 的字段
        String having = null;                              //分组时候跟上的条件
        String orderBy = null;                              //排序
        Cursor cursor = database.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
        StringBuilder builder = new StringBuilder();
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String phone = cursor.getString(2);
            builder.append("{ id:" + id + ", name:" + name + ", phone:" + phone + " }");
        }
        cursor.close();
        database.close();
        tVCommand.setText("columns==" + Arrays.toString(columns));
        tvShow.setText(builder.toString());
    }

    public void update1(View v) {
        SQLiteDatabase database = openHelper.getReadableDatabase();
        String table = "info";
        ContentValues values = new ContentValues();  //用来保存新修改的值
        values.put("phone", "111111111");
        String whereClause = "name = ?";
        String[] whereArgs = new String[]{getName()};
        int update = database.update(table, values, whereClause, whereArgs);
        tVCommand.setText("<values==" + values + "> <whereClause==" + whereClause + "> <whereArgs==" + Arrays.toString(whereArgs) + ">\nupdate==" + update);
    }

    public void delete1(View v) {
        SQLiteDatabase database = openHelper.getReadableDatabase();
        String table = "info";
        String whereClause = "name = ?";
        String[] whereArgs = new String[]{getName()};
        int delete = database.delete(table, whereClause, whereArgs);
        tVCommand.setText("<whereClause==" + whereClause + "> <whereArgs==" + Arrays.toString(whereArgs) + ">\ndelete==" + delete);
        database.close();
    }


    public void transaction(View v) {
        SQLiteDatabase db = transactionHelper.getReadableDatabase();
        db.beginTransaction();
        try {
            String sql = "update count set money= money-200 where name=?";
            db.execSQL(sql, new String[]{"张三"});
            tVCommand.setText(sql);
            String sqladd = "update count set money= money+200 where name=?";
            db.execSQL(sqladd, new String[]{"李四"});
            tVCommand.setText(sqladd);
            db.setTransactionSuccessful();//给当前事务加上成功的标记
        } catch (Exception e) {
            Toast.makeText(this, "服务器忙,故障码(10031)", Toast.LENGTH_SHORT).show();
        } finally {
            db.endTransaction();//如果没有成功标记则 回滚到最初的状态
            db.close();
        }

    }

    /**
     * 事务的特点 一组操作 放到事务中 要么全部成功  要么全不成功
     * 要么都成功 要么都失败
     * beginTransaction() 开启一个事务
     * 在beginTransaction()和endTransaction()之间关于数据库的操作 要么都成功 要么都失败
     * 如何判断成功 和失败   看这个方法db.setTransactionSuccessful();是否被执行
     */
    public void transactionError(View view) {
        SQLiteDatabase db = transactionHelper.getReadableDatabase();

        db.beginTransaction();
        try {
            String sql = "update count set money= money-200 where name=?";
            db.execSQL(sql, new String[]{"张三"});
            tVCommand.setText(sql);
            int i = 100 / 0;
            String sql2 = "update count set money= money+200 where name=?";
            db.execSQL(sql2, new String[]{"李四"});
            tVCommand.setText(sql2);
            db.setTransactionSuccessful();//给当前事务加上成功的标记
        } catch (Exception e) {
            Toast.makeText(this, "服务器忙,故障码(10031)", Toast.LENGTH_SHORT).show();
        } finally {
            //当事务结束的时候 会检查setTransactionSuccessful()设置的标记 如果检查到了 说明成功
            db.endTransaction();//如果没有成功标记则 回滚到最初的状态
            db.close();
        }
    }

    public void transactionInit(View view) {
        SQLiteDatabase db = transactionHelper.getReadableDatabase();
        db.execSQL("update count set money='10000'where name='张三'");
        db.execSQL("update count set money='10000'where name='李四'");
        db.close();
    }

    public void transactionQury(View view) {
        SQLiteDatabase db = transactionHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from count", null);
        // 让指针不断向下移动 遍历整个结果集
        StringBuilder builder = new StringBuilder();
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("_id"));
            String name = cursor.getString(1);
            String phone = cursor.getString(2);
            String count = cursor.getString(3);
            String x = "id==" + id + " name==" + name + " count==" + count;
            builder.append(x + "\n");
        }
        // 关闭游标
        cursor.close();
        db.close();
        tvShow.setText(builder.toString());
    }


}
