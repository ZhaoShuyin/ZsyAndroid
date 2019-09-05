package com.zsy.android.view;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class MySmartImageView extends ImageView {
	protected static final int GET_IMAGE_SUCCESS = 0;
	protected static final int GET_IMAGE_ERROR = 1;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case GET_IMAGE_SUCCESS:
					Bitmap bm = (Bitmap) msg.obj;
					setImageBitmap(bm);
					break;
				case GET_IMAGE_ERROR:
					int resId = (Integer) msg.obj;
					setImageResource(resId);
					break;


			}

		};
	};

	/**
	 * 三个参数的构造  系统在解析xml文件时 创建view对象调用
	 * @param context
	 * @param attrs
	 * @param defStyle 样式
	 */
	public MySmartImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 两个参数的构造    系统解析xml布局文件 创建view对象的时候调用
	 * @param context
	 * @param attrs AttributeSet 属性集 在xml布局文件中 写控件时配置的xml属性 都会在解析时封装成一个AttributeSet对象作为参数传递进来
	 */
	public MySmartImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 一个参数的构造 传上下文  程序员在java代码中动态创建布局的时候调用一个参数的构造
	 * @param context
	 */
	public MySmartImageView(Context context) {
		super(context);
	}

	public void setImageUrl(final String url){
		new Thread(){
			public void run() {
				try {
					URL path = new URL(url);
					HttpURLConnection connetion = (HttpURLConnection) path.openConnection();
					connetion.setRequestMethod("GET");
					connetion.setConnectTimeout(6000);
					int code = connetion.getResponseCode();
					if(code == 200){
						//从网络获取图片对应的流
						InputStream inputStream = connetion.getInputStream();
						//通过流转换成bitmap对象
						Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
						//通过handler把bitmap发送到主线程
						Message msg = Message.obtain();
						msg.obj = bitmap;
						handler.sendMessage(msg);

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};

		}.start();

	}

	/**
	 * 通过url显示网络图片 如果加载失败显示默认图
	 * @param url 网络图片的地址
	 * @param resID 默认图的资源id
	 */
	public void setImageUrl(final String url,final int resID){
		new Thread(){
			public void run() {
				try {
					URL path = new URL(url);
					HttpURLConnection connetion = (HttpURLConnection) path.openConnection();
					connetion.setRequestMethod("GET");
					connetion.setConnectTimeout(6000);
					int code = connetion.getResponseCode();
					if(code == 200){
						//从网络获取图片对应的流
						InputStream inputStream = connetion.getInputStream();
						//通过流转换成bitmap对象
						Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
						//通过handler把bitmap发送到主线程
						Message msg = Message.obtain();
						msg.obj = bitmap;
						msg.what = GET_IMAGE_SUCCESS;
						handler.sendMessage(msg);

					}else{
						//如果响应码不是200说明图片加载不成功 显示默认图
						Message msg = Message.obtain();
						msg.obj = resID;
						msg.what = GET_IMAGE_ERROR;
						handler.sendMessage(msg);
					}
				} catch (Exception e) {
					//如果走异常说明图片加载不成功 显示默认图
					Message msg = Message.obtain();
					msg.obj = resID;
					msg.what = GET_IMAGE_ERROR;
					handler.sendMessage(msg);
				}
			};

		}.start();
	}
}
