package com.zsy.android.api.keyboard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.os.Environment;

public class LoginUtils {

	public static boolean saveInfo(String username,String pwd){
		//拼装用户名密码
		String temp = username+"##"+pwd;
		//把用户名密码保存到 data/data/当前应用包名 目录下
		File file = new File("/data/data/cn.itcast.login/info.txt");
		try {
			FileOutputStream fos = new FileOutputStream(file);
			//通过流写数据
			fos.write(temp.getBytes());
			fos.close();
			//成功关闭 说明保存成功
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			//如果有异常说明保存失败
			return false;
		}
	}

	/**
	 * 获取保存的用户名密码
	 * @return String数组 如果不为空说明获取成功  数组的第一个元素就是用户名 第二个元素是密码
	 */
	public static String[] readInfo(){
		//到保存的位置读相应的文件
		String[] result = null;
		File file = new File("/data/data/cn.itcast.login/info.txt");
		try {
			FileInputStream fis = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			String temp = reader.readLine();
			reader.close();
			result = temp.split("##");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 通过上下文api 获取相关路径 保存用户名密码
	 * @param context 上下文
	 * @param username 用户名
	 * @param pwd 密码
	 * @return
	 */
	public static boolean saveInfo(Context context,String username,String pwd){
		//拼装用户名密码
		String temp = username+"##"+pwd;
		//把用户名密码保存到 data/data/当前应用包名 目录下
		//File file = new File(context.getFilesDir(),"info.txt");
		try {
			//openFileOutput 两个参数 第一个参数 要操作的文件名字
			//第二个参数 操作的模式 默认传递 Context.MODE_PRIVATE 私有的    Context.MODE_APPEND 追加
			// Context.MODE_WORLD_READABLE 所有人可读  Context.MODE_WORLD_WRITEABLE 所有人可写
			FileOutputStream fos = context.openFileOutput("info2.txt", Context.MODE_PRIVATE);
			//通过流写数据
			fos.write(temp.getBytes());
			fos.close();
			//成功关闭 说明保存成功
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			//如果有异常说明保存失败
			return false;
		}
	}

	public static String[] readInfo(Context context){
		//到保存的位置读相应的文件
		String[] result = null;
		//File file = new File("/data/data/cn.itcast.login/info.txt");
		try {
			//FileInputStream fis = new FileInputStream(file);
			//通过上下文获取输入流 实际上操作的是 getFilesDir()所返回的路径
			FileInputStream fis = context.openFileInput("info2.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			String temp = reader.readLine();
			reader.close();
			result = temp.split("##");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 保存用户名密码到sd卡
	 * @param username
	 * @param pwd
	 * @return
	 */
	public static boolean saveInfo2SDCard(String username,String pwd){
		//拼装用户名密码
		String temp = username+"##"+pwd;
		//把用户名密码保存到 data/data/当前应用包名 目录下
		File file = new File(Environment.getExternalStorageDirectory(),"infosd.txt");
		try {
			FileOutputStream fos = new FileOutputStream(file);
			//通过流写数据
			fos.write(temp.getBytes());
			fos.close();
			//成功关闭 说明保存成功
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			//如果有异常说明保存失败
			return false;
		}
	}

}