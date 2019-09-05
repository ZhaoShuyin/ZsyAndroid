package com.zsy.android.function.net;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MultiThreadDownLoad {
	public static String path = "http://192.168.69.104:8080/FeiQ.exe";
	public static int threadCount = 3;
	public static void main(String[] args) {
		//① 联网获取要下载的文件大小
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(10000);
			int code = conn.getResponseCode();
			if(code == 200){
				//获取要下载文件的长度
				int length = conn.getContentLength();
				//② 在本地创建一个文件 跟要下载的文件大小相同
				//RandomAccessFile 构造两个参数
				//第一个参数 文件要保存的路径
				//第二个参数 文件访问的模式  "r" 只读模式 "rw" "rws" 会把文件和元数据 直接写到磁盘 不通过缓存
				//"rwd" 把文件直接保存到磁盘 不走缓存
				RandomAccessFile rafile = new RandomAccessFile(getFileName(path), "rw");
				rafile.setLength(length);
				//③ 计算每个线程要下载的数据范围
				//3.1计算每个线程要下载多大数据
				int blockSize = length/threadCount;
				for(int i = 0;i<threadCount;i++){
					int startIndex = i*blockSize;
					int endIndex = (i+1)*blockSize-1;
					if(i == threadCount-1){
						endIndex = length-1;
					}
					//④ 开启多个线程 每个线程下载对应范围的数据(通过randomAccessFile保存数据)
					new DownLoadThread(startIndex, endIndex, i).start();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}





	}

	private static class DownLoadThread extends Thread{
		//声明成员变量 用来保存 每个线程要下载的开始数据索引和结束索引值
		private int startIndex;
		private int endIndex;
		private int threadID;
		public DownLoadThread(int startIndex, int endIndex, int threadID) {
			super();
			this.startIndex = startIndex;
			this.endIndex = endIndex;
			this.threadID = threadID;
		}

		public void run() {
			try {
				URL url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(1000);
				//给当前线程设置range请求头 告诉服务端 我要获取的数据范围
				conn.setRequestProperty("Range", "bytes="+startIndex+"-"+endIndex);
				int code = conn.getResponseCode();
				if(code == 206){//注意 请求部分数据 要判断206
					System.out.println("线程"+threadID+"开始下载");
					InputStream inputStream = conn.getInputStream();
					RandomAccessFile raf = new RandomAccessFile(getFileName(path), "rw");
					//注意 这里要设置当前线程要开始写的位置 从计算好的位置开始写文件
					raf.seek(startIndex);
					int len = -1;
					byte[] buffer = new byte[1024*1024];
					while((len = inputStream.read(buffer))!=-1){
						raf.write(buffer,0,len);
					}
					raf.close();
					inputStream.close();
					System.out.println("线程"+threadID+"下载结束");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}

	public static String getFileName(String path){
		//http://192.168.69.104:8080/FeiQ.exe 用 / 截取路径
		//截取之后 取出返回值的最后一个元素  就是要获取的文件名字
		String[] temp = path.split("/");
		return temp[temp.length-1];
	}

}
