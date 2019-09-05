package com.zsy.android.base.db.helper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 通过OpenHelper实现数据库的操作
 * @author Administrator
 *
 */
public class MyOpenHelper extends SQLiteOpenHelper {

	public MyOpenHelper(Context context) {
		//第一个参数 上下文
		//第二个参数 name 数据库的名字
		//第三个参数 游标工厂  用来创建查询之后返回的游标 正常情况下 使用系统默认的游标工厂 传null就可以了
		//第四个参数 数据库的版本号 必须从1开始
		super(context, "zsy.db", null, 2);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println("数据库被创建了MyOpenHelper的onCreate方法执行");
		String sql = "create table info(_id integer primary key autoincrement,name varchar(20),phone varchar(20))";
		//当数据库第一次创建的时候 会走这个oncreate方法
		//在这个方法中一般做 表的创建和数据初始化的操作
		db.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		//数据库升级的时候要考虑市场上存活的所有版本的数据库 通过oldVersion 和newVersion进行判断
		//不同版本升级业务逻辑可能不同
		//当数据库升级的时候会调用这个方法, 在这个方法中 处理数据库中表的增加 修改 删除的操作
		//当数据库版本号变大了 就说明数据库升级了
		System.out.println("onUpgrade被调用oldVersion=="+oldVersion+"newVersion==="+newVersion);
		//db.execSQL("alter table info add age integer");

	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//super.onDowngrade(db, oldVersion, newVersion);
		//数据库降级的时候会走onDowngrade 默认处理时抛异常 如果业务需要降级 那么重写这个方法
	}

}
