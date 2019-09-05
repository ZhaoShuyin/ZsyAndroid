package cn.azsy.zstokhttp.zsyoktest;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.azsy.zstokhttp.R;
import cn.azsy.zstokhttp.zsyokhttp.zok.ZsyOk;
import cn.azsy.zstokhttp.zsyokhttp.zok.continueload.DownLoador;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zsy on 2017/6/20.
 http://192.168.1.104:8080/HttpsTest/test1
 */

public class ZsyOkTestActivity extends AppCompatActivity {

    String urlBanner = "http://101.200.146.62:81/apicore/index.php/index/get_banner";
    String urlStr = "http://192.168.1.106:8080/HttpsTest/test1";
    String url = "http://101.200.146.62:81/apicore/index/getcategory";
    String urlLogin = "http://192.168.1.106:8080/Login_demo/LoginServlet";
    String urlApk = "http://192.168.1.106:8080/HttpsTest/apk.apk";
    String upLoadUrl = "http://192.168.1.104:8080/UpLoad03/upload";
    String upLoadUrl2 = "http://192.168.1.104:8080/Day07_02_ServletHello/UploadServlet";//http://localhost:8080/Day07_02_ServletHello/UploadServlet


    private OkHttpClient mOkHttpClient;
    private EditText et1;
    private EditText et2;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private ProgressBar bar1;
    private ProgressBar bar2;
    private ProgressBar bar3;
    private TextView tvBar1;
    private TextView tvBar2;
    private TextView tvBar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zsyoktest);
        initView();
        initClient();

    }

    private void initClient() {
        File sdcache = getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
        mOkHttpClient = builder.build();
    }

    public void showStr1(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv1.setText(s);
            }
        });
    }

    public void showStr2(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv2.setText(s);
            }
        });
    }

    public void showStr3(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv3.setText(s);
            }
        });
    }

    public void reSet() {
        tv1.setText("默认值");
        tv2.setText("默认值");
        tv3.setText("默认值");
        bar1.setProgress(0);
        bar2.setProgress(0);
        bar3.setProgress(0);
        tvBar1.setText("0");
        tvBar2.setText("0");
        tvBar3.setText("0");
    }

    private void initView() {
        et1 = (EditText) findViewById(R.id.et_user);
        et2 = (EditText) findViewById(R.id.et_word);
        tv1 = (TextView) findViewById(R.id.tv_show1);
        tv2 = (TextView) findViewById(R.id.tv_show2);
        tv3 = (TextView) findViewById(R.id.tv_show3);
        bar1 = (ProgressBar) findViewById(R.id.pro_bar1);
        bar2 = (ProgressBar) findViewById(R.id.pro_bar2);
        bar3 = (ProgressBar) findViewById(R.id.pro_bar3);
        tvBar1 = (TextView) findViewById(R.id.tv_bar1);
        tvBar2 = (TextView) findViewById(R.id.tv_bar2);
        tvBar3 = (TextView) findViewById(R.id.tv_bar3);
    }

    public void bt0(View view) {
        reSet();
    }


    public void btClick1(View view) {
        ZsyOk.useNew = !ZsyOk.useNew;
        ZsyOk.get(urlBanner, null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showStr1("简单GET请求响应失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String net = "网络响应== " + response.networkResponse();
                String cache = "缓存响应== " + response.cacheResponse();
                String a = response.body().string();
                showStr1(a);
                showStr2(net);
                showStr3(cache);
            }
        });
    }

    public void btClick2(View view) {
        ZsyOk.get(url, true, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showStr1("GET只使用网络,实时数据响应失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String net = "网络响应== " + response.networkResponse();
                String cache = "缓存响应== " + response.cacheResponse();
                String a = response.body().string();
                showStr1(a);
                showStr2(net);
                showStr3(cache);
            }
        });
    }

    public void btClick3(View view) {
        ZsyOk.get(url, false, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showStr1("GET只使用使用网络缓存响应失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String net = "网络响应== " + response.networkResponse();
                String cache = "缓存响应== " + response.cacheResponse();
                String a = response.body().string();
                showStr1(a);
                showStr2(net);
                showStr3(cache);
            }
        });
    }

    public void btClick4(View view) {
        ZsyOk.get(urlLogin + "?username=abc&password=123", null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showStr1("GET使用参数登录测试响应失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String net = "网络响应== " + response.networkResponse();
                String cache = "缓存响应== " + response.cacheResponse();
                String a = response.body().string();
                showStr1(a);
                showStr2(net);
                showStr3(cache);
            }
        });
    }
    //简单Post默认只用网络
    public void btClick5(View view) {
        RequestBody requestBody = new FormBody.Builder()
                .add("username", "abc")
                .add("password", "123").build();
        ZsyOk.post(urlLogin, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showStr1("简单Post默认只使用网络响应失败");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String net = "网络响应== " + response.networkResponse();
                String cache = "缓存响应== " + response.cacheResponse();
                String a = response.body().string();
                showStr1(a);
                showStr2(net);
                showStr3(cache);
            }
        });
    }

    public void btClick6(View view) {
        ZsyOk.useNew = !ZsyOk.useNew;
        ZsyOk.get(upLoadUrl, null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showStr1("简单GET请求响应失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String net = "网络响应== " + response.networkResponse();
                String cache = "缓存响应== " + response.cacheResponse();
                String a = response.body().string();
                showStr1(a);
                showStr2(net);
                showStr3(cache);
            }
        });
    }

    public void btClick7(View view) {

    }

    public void btClick8(View view) {

    }
    //初始化下载
    private DownLoador downLoador;
    public void btClick9(View view) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                downLoador = new DownLoador(urlApk, new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/aaaaaaa.apk"),
                        new DownLoador.ProgressListener() {
                            @Override
                            public void progress(long progress) {
                                Log.i("loadload", "progress: == "+progress);
                                bar1.setProgress((int) progress);
//                                tvBar1.setText((int) progress);
                            }

                            @Override
                            public void setCntentLength(long contentLength) {
                                bar1.setMax((int)contentLength);
                            }

                            @Override
                            public void finish() {
                                showStr1("下载完成");
                            }
                        });
            }
        }.start();
    }
    //开始/继续下载
    public void btClick10(View view) {
//        bar1.setMax(6312092);
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    downLoador.startLoad();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    //取消/暂停下载
    public void btClick11(View view) {
        downLoador.stopLoad();
    }
    public void btClick12(View view) {

    }
    public void btClick13(View view) {

    }
    public void btClick14(View view) {

    }
    public void btClick15(View view) {

    }
    public void btClick16(View view) {

    }
    public void btClick17(View view) {

    }
    public void btClick18(View view) {

    }
    public void btClick19(View view) {
        ZsyOk.useNew = !ZsyOk.useNew;
        ZsyOk.get(upLoadUrl, null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showStr1("简单GET请求响应失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String net = "网络响应== " + response.networkResponse();
                String cache = "缓存响应== " + response.cacheResponse();
                String a = response.body().string();
                showStr1(a);
                showStr2(net);
                showStr3(cache);
            }
        });
    }
    public void btClick20(View view) {
        uploadFile();
    }


    public void uploadFile() {
        //手机端要上传的文件，首先要保存你手机上存在该文件
        String filePath = Environment.getExternalStorageDirectory()
                + "/upload.apk";

        File file = new File(filePath);

        long length = file.length();
        showStr1("选择的文件大小== " + length);

        /*RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", "wangshu")
                .addFormDataPart("image", "wangshu.jpg",
                        RequestBody.create(MediaType.parse("multipart/form-data"), file))
                .build();
        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + "...")
                .url("https://api.imgur.com/3/image")
                .post(requestBody)
                .build();*/
        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody fileBody2 = RequestBody.create(MediaType.parse("multipart/form-data"), file);//多文件上传
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("test", "test.apk", fileBody)
                .addFormDataPart("test", "test2.apk", fileBody2)//多文件上传
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.1.106:8080/ZupLoad/up")
                .post(requestBody)
                .build();
//        Request request = new Request.Builder()
//                .url("http://192.168.1.106:8080/ZupLoad/up")
////                .removeHeader("content-type")
////                .post(RequestBody.create(MediaType.parse("application/octet-stream"), file))
//                .post(RequestBody.create(MediaType.parse("multipart/form-data"), file))
//                .build();
        Log.i("okrequest", "请求头"+request.toString());
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("Okhttp3", "POST上传文件失败");
                showStr2("POST上传文件失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("Okhttp3", "POST上传文件成功");
                showStr2("POST上传文件成功");
//                Log.i("Okhttp3", response.body().string());
                showStr3(response.body().string());
            }
        });

    }


    private void uploadMultiFile() {
        final String url = "http://192.168.0.104:8080/UploadFileServer/upload";
        File file = new File("fileDir", "test.jpg");
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "test.jpg", fileBody)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();


        final okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = httpBuilder
                //设置超时
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("asd", "uploadMultiFile() e=" + e);
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("asd", "uploadMultiFile() response=" + response.body().string());
            }
        });
    }


}
