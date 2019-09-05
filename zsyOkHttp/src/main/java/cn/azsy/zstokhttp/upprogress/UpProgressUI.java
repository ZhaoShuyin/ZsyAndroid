package cn.azsy.zstokhttp.upprogress;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;

import cn.azsy.zstokhttp.R;
import cn.azsy.zstokhttp.zsyokhttp.zok.ZsyOk;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zsy on 2018/6/20.
 */

public class UpProgressUI extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_progress);
    }

    private static final String TAG = "TAGATAG";

    public void upload(View view) {
        File externalStorageDirectory = new File(Environment.getExternalStorageDirectory
                ().getAbsoluteFile(), "nihao.apk");
        Log.e(TAG, "upload: " + externalStorageDirectory.toString());

        RequestBody requestBody = RequestBody.create(MediaType.parse
                ("application/octet-stream"), externalStorageDirectory);
        ProxyRequestBody proxyRequestBody = new ProxyRequestBody(requestBody, new
                ProxyRequestBody.UploadListener() {
                    @Override
                    public void onUpload(double progress) {
                        Log.e(TAG, "onUpload: " + progress);
                    }
                });
        MultipartBody body = new MultipartBody.Builder()
                .addFormDataPart("file", "apk.apk", proxyRequestBody) //这里要传递代理对象
                .build();
        Request request = new Request.Builder().url("http://192.168.1.106:8080/ZsyService/upload")
                .post(body).build();
        ZsyOk.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: " + call.toString());
                Log.e(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG, "onResponse: " + response.body().string());
            }
        });
    }

}
