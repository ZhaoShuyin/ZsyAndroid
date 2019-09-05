package zsy.cn.rxokretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import zsy.cn.rxokretrofit.bean.BeanA;
import zsy.cn.rxokretrofit.net.NetApi;
//import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    String TAG = "RxOkRet";
    String Url = "http://192.168.1.105:8080/ZsyService/zsy";
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv_show);
        findViewById(R.id.bt_retrofit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrofit();
            }
        });
        findViewById(R.id.bt_retrofit_parameter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrofit("名字");
            }
        });
        findViewById(R.id.bt_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ok();
            }
        });
        findViewById(R.id.bt_rx_ret).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxret();
            }
        });
        try {
            initOk();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "onCreate: ");
        }
        initRetrofit();
    }


    public void rxret(){
        NetApi netApi = retrofit.create(NetApi.class);
        //IllegalArgumentException: Unable to create call adapter for io.reactivex.Observable<zsy.cn.rxokretrofit.bean.BeanA>
        //需要.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        io.reactivex.Observable<BeanA> observer = netApi.callObserver("张三李四");
        observer.subscribeOn(Schedulers.io())//IO线程加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Observer<BeanA>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(BeanA beanA) {
                        String s = beanA.toString()+"\n<<"+Thread.currentThread().getName()+">>";
                        textView.setText(s);
                        Log.i(TAG, "观察者获取结果: \n"+ s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void retrofit(String name) {
        NetApi netApi = retrofit.create(NetApi.class);
        Call<BeanA> beanACall = netApi.callB(name);
        beanACall.enqueue(new Callback<BeanA>() {
            @Override
            public void onResponse(Call<BeanA> call, Response<BeanA> response) {
                Log.i("RxOkRet", "response.headers(): \n" + response.headers());
                Log.i("RxOkRet", "beanA ==: " + response.body());
//                BeanA beanA = response.body();
//                Log.i("RxOkRet", "beanA ==: " + beanA.toString());
            }

            @Override
            public void onFailure(Call<BeanA> call, Throwable t) {
                Log.i("RxOkRet", "onFailure: " + t.toString());
            }
        });
    }


    public void ok() {
        Request request = new Request.Builder().url(Url).build();
        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.i(TAG, "okhttp3 onFailure: ========================");
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                Log.i(TAG, "Ok响应成功:===================== \n" + response.headers().toString() + "\n" + response.body().toString());
            }
        });
    }


    public void retrofit() {
        NetApi netApi = retrofit.create(NetApi.class);
        Call<BeanA> beanACall = netApi.callA();
        beanACall.enqueue(new Callback<BeanA>() {
            @Override
            public void onResponse(Call<BeanA> call, Response<BeanA> response) {
                Log.i("RxOkRet", "response.headers(): \n" + response.headers());
                Log.i("RxOkRet", "beanA ==: " + response.body());
//                BeanA beanA = response.body();
//                Log.i("RxOkRet", "beanA ==: " + beanA.toString());
            }

            @Override
            public void onFailure(Call<BeanA> call, Throwable t) {
                Log.i("RxOkRet", "onFailure: " + t.toString());
            }
        });
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()//
                .baseUrl("http://192.168.1.105:8080/ZsyService/")//以/结尾 将retrofit可以使用它与方法的相对路径拼接收一个完整路径。
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//Unable to create call adapter for需要添加
                .addConverterFactory(GsonConverterFactory.create())//需要将每一个的请求返回的json进行自动解析。
                .client(okHttpClient)
                .build();
    }

    private void initOk() throws IOException {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)//连接超时限制
                .writeTimeout(60, TimeUnit.SECONDS)//写入超时限制
                .readTimeout(60, TimeUnit.SECONDS)//读取超时限制
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        okhttp3.Request request = chain.request();
                        okhttp3.Response proceed = chain.proceed(request);
                        okhttp3.Response build = proceed.newBuilder().addHeader("zsy", "zsyHeader").build();
                        return build;
                    }
                })//添加网络过滤器
                .build();
    }

}
