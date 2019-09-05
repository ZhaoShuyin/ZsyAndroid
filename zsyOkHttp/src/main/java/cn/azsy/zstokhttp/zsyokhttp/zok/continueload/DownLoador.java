package cn.azsy.zstokhttp.zsyokhttp.zok.continueload;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by zsy on 2017/5/5.
 */

public class DownLoador {
    String fileUrl;
    File file;
    Long position = 0L;
    Long contentLength;
    ProgressListener listener;
    OkHttpClient okHttpClient;
    Call call;

    public DownLoador(String fileUrl, File file, ProgressListener listener) {
        this.fileUrl = fileUrl;
        this.file = file;
        this.listener = listener;
        okHttpClient = new OkHttpClient.Builder().build();
        contentLength = getFileLength();
    }

    public void stopLoad() {
        if (call != null) {
            call.cancel();
        }
    }

    public void startLoad() throws IOException {
        if (listener!=null){
            listener.setCntentLength(contentLength);
        }
        Request request = new Request.Builder()
                //确定下载的范围,添加此头,则服务器就可以跳过已经下载好的部分
                .addHeader("RANGE", "bytes=" + position + "-" + contentLength)//指示下载的区间
                .url(fileUrl)
                .build();
        call = okHttpClient.newCall(request);
        Response response = call.execute();
        saveFile(response);
    }

    /**
     * 通过随机访问流写入数据到文件,同时接口回调监听更新进度
     * @param response
     */
    private void saveFile(Response response) {
        ResponseBody body = response.body();
        InputStream in = body.byteStream();//获取响应体中的流
        RandomAccessFile accessFile = null;
        FileChannel channelOut = null;
        try {
            accessFile = new RandomAccessFile(file, "rwd");
            channelOut = accessFile.getChannel();
            MappedByteBuffer map = channelOut.map(FileChannel.MapMode.READ_WRITE, position, body.contentLength());//文件全部长度
            byte[] buffer = new byte[2048];//中转缓存内存2KB
            int len;
            while ((len = in.read(buffer)) != -1) {
                map.put(buffer, 0, len);
                position += len;
                listener.progress(position);//接口回调显示进度
                Log.i("shshishi", "写入进度== "+position);
            }
            listener.finish();//调用回调完成
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {          //当取消Call时也关闭IO流
            try {
                in.close();
                accessFile.close();
                channelOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public interface ProgressListener {
        public void progress(long progress);
        public void setCntentLength(long contentLength);
        public void finish();
    }

    public long getFileLength() {
        Request request = new Request.Builder()
                .url(fileUrl)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response != null && response.isSuccessful()) {

                long contentLength = response.body().contentLength();//通过响应体的长度获取文件总长度
                response.close();
                return contentLength == 0 ? 0 : contentLength;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }




}
