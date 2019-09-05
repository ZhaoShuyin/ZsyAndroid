package com.zsy.android.view;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RangeDemo {
	public static void main(String[] args) {
		//要访问的数据
		String path = "http://127.0.0.1:8080/news.xml";
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(10000);
			//通过range请求头通知服务器 我要获取一部分数据 具体范围就是从第10个byte 到第100个byte
			conn.setRequestProperty("Range", "bytes="+10+"-"+100);
			int code = conn.getResponseCode();
			if(code == 206){//请求部分数据 成功返回的响应码是206
				InputStream inputStream = conn.getInputStream();
				String string = getStringFromStream(inputStream);
				System.out.println(string);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getStringFromStream(InputStream inputStream) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len = -1;
		byte[] buffer = new byte[1024];
		while((len = inputStream.read(buffer))!=-1){
			baos.write(buffer, 0, len);
		}
		inputStream.close();
		String result = new String(baos.toByteArray(),"utf-8");
		return result;

	}

}
