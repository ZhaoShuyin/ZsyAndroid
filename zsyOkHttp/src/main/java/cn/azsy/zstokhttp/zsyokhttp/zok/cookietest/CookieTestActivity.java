package cn.azsy.zstokhttp.zsyokhttp.zok.cookietest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.azsy.zstokhttp.R;
import cn.azsy.zstokhttp.zsyokhttp.zok.ZsyOk;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zsy on 2017/6/12.
 */

public class CookieTestActivity extends AppCompatActivity {

    TextView result;
    private OkHttpClient client;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooiketest);
        result = (TextView) findViewById(R.id.tv_cookie_test);
        initClient();
    }


    public void reSetText(View view) {
        result.setText("默认值");
    }

    String urlLogin = "http://192.168.1.103:8080/Login_demo/LoginServlet";
    String cookie = null;

    public void postLogin(View view) {
        RequestBody requestBody = null;
        if (cookie == null) {
            Log.i("zsyokhttp", "加入userpassword请求request.cookie==" + cookie);
            requestBody = new FormBody.Builder()
                    .add("username", "abc")
                    .add("password", "123").build();

            ZsyOk.post(urlLogin, requestBody, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    showResult("简单Post响应失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
//                    cookie=response.header("sessionid");
                    String s = response.header("sessionid");
                    Log.i("zsyokhttp", "获取cookie--header--sessionid== " + s);

                    cookie = s;

                    Log.i("zsyokhttp", "Headers=== " + response.headers().toString());
                    String a = response.body().string();
                    showResult(a);
                }
            });
        } else {
            Log.i("zsyokhttp", "加入cooike请求request.cookie==" + cookie);
            requestBody = new FormBody.Builder()
                    .add("username", "abc")
                    .add("password", "123").build();
            Request request = new Request.Builder().url(urlLogin)
                    .addHeader("cooike", cookie)
                    .post(requestBody).build();
            ZsyOk.getOkHttpClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    showResult("cookie响应失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.i("zsyokhttp", "cookie==Headers=== " + response.headers().toString());
                    String a = response.body().string();
                    showResult(a);
                }
            });
        }

    }

    private void showResult(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                result.setText(s);
            }
        });
    }


    static String urlhHost;

    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<String, List<Cookie>>();

    private void initClient() {
        client = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        Log.d("cookiecc", "SS-HttpUrl==" + url.toString());
                        if (cookies != null) {
                            Log.d("cookiecc", "SSSSSsaveFromResponse: " + cookies.get(0));
                        } else {
                            Log.d("cookiecc", "SSSSSsaveFromResponse: cookies==null");
                        }
                        urlhHost = url.host();
                        cookieStore.put(url.host(), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        Log.d("cookiecc", "LL-HttpUrl==" + url.toString());
                        List<Cookie> cookies = cookieStore.get(url.host());
                        if (cookies != null) {
                            Log.d("cookiecc", "LLLLLLLLLloadForRequest: " + cookies.get(0));
                        } else {
                            Log.d("cookiecc", "LLLLLLLLLloadForRequest: cookies==null");
                        }
                        List<Cookie> cookieList = cookies != null ? cookies : new ArrayList<Cookie>();

                        return cookieList;
                    }
                })

                .build();
    }

    //    Cookie cookie = new Cookie("名字","值");/**/
    public void postLogin2(View view) {


        RequestBody requestBody = new FormBody.Builder()
                .add("username", "abc")
                .add("password", "123").build();

        Request request = new Request.Builder().url(urlLogin)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String a = "onFailure";
                showResult(a);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String a = response.body().string();
                /*Log.d("cookiecc",response.headers().toString());
                String cookie = response.header("sessionid");
                if (cookie!=null){
                    cokcok = cookie;
                    Log.d("cookiecc","本地保存cookie=="+cokcok);
                }else{
                    Log.d("cookiecc","没有保存cookie=="+cokcok);
                }*/
                showResult(a);
            }
        });
    }

    public void localCookie(View view) {
        if (urlhHost != null) {
//            List<Cookie> cookies = cookieStore.get(urlhHost);
            int s = cookieStore.size();
            showResult("urlhHost==" + urlhHost.toString() + " size ==" + s);
        } else {
            Toast.makeText(this, "urlhHost==null", Toast.LENGTH_SHORT).show();
        }

    }
}
