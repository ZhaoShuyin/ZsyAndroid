package com.zsy.mvp.net;

import android.util.Log;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Title com.zsy.mvp.net
 * @date 2021/2/1
 * @autor Zsy
 */

public class Net {

    private static String TAG = "NETNET";

    private static OkHttpClient okHttpClient;

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient().newBuilder()
                    //设置Header
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();
                            Request.Builder requestBuilder = original.newBuilder();
                            //添加Token
                            //.header("token", "");
                            Log.e(TAG, "intercept: 拦截过滤器");
                            Request request = requestBuilder.build();
                            return chain.proceed(request);
                        }
                    })
                    //设置拦截器
                    .addInterceptor(
                            new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                                @Override
                                public void log(String message) {
                                    Log.e(TAG, "log: " + message);
                                }
                            })
                                    .setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                    .build();
        }
        return okHttpClient;
    }

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()//
                    .baseUrl("http://localhost:8080/javaweb/")//以/结尾 将retrofit可以使用它与方法的相对路径拼接收一个完整路径。
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())//Unable to create call adapter for需要添加
                    .addConverterFactory(GsonConverterFactory.create())//需要将每一个的请求返回的json进行自动解析。
                    .client(getOkHttpClient())
                    .build();
        }
        return retrofit;
    }


    public static <T> T getApi(Class<T> aClass) {
        return getRetrofit().create(aClass);
    }

}
