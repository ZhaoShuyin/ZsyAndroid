package cn.azsy.zstokhttp.zsyokhttp.zok;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zsy on 2017/4/28.
 */

public class ZsyOk {

    public static Context okContext;//上下文在Application中初始化使用应用的缓存路径来存放网络缓存文件
    private static OkHttpClient okHttpClient;
    public static boolean useNew = true;
    private static int cacheSize = 5 * 1024 * 1024;
    static String json = "这是缓存";

    /**
     * 网络缓存方式注: 1.框架默认只缓存GET请求
     * <p>
     * 使用网络缓存注: 1.只有addNetworkInterceptor会写入本地缓存文件
     * 2.需要SD卡写入权限----WRITE_EXTERNAL_STORAGE
     * 3.通过修改,设置响应头中<Pragma>的方式来设置是否使用网络缓存,默认是同时使用
     * 4.通过修改,设置响应头中<Cache-Control>的方式设置网络缓存的使用时间等,默认是按照设置时间使用缓存
     * 5.3和4同时设置时,按照4时间设置使用网络
     * <p>
     * 网络缓存地址注:
     * 1.getCacheDir() /data/data/<application package>/cache目录
     * 2.getFilesDir() /data/data/<application package>/files目录
     * 3.getExternalCacheDir()路径 /sdcard/Android/data/<application package>/cache
     * 4.getCacheDir()路径/data/data/<application package>/cache
     */
    public static void initClient(Context context) {
        if (context != null) {
            okContext = context;
        } else {
            try {
                throw new Exception("You must init this tool's context in Application");//在没有初始化的情况下抛出异常
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        File sdcache = okContext.getExternalCacheDir();//获取SDCard/Android/data/包名/cache/目录,来存放临时的缓存数据.
        //缓存空间大小 5M //只有经过addNetworkInterceptor的网络请求,响应才会写入
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                //先执行addInterceptor,在联网过程中执行addNetworkInterceptor

                //无网情况下不会执行addNetworkInterceptor中拦截器
                //所以先在addInterceptor中加入缓存拦截器
//                .addInterceptor(new MyInterceptor.CacheIntercptor(context))
                //再执行addNetworkInterceptor,包裹在addInterceptor中
//                .addNetworkInterceptor(new MyInterceptor.postCacheIntercptor3())
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Log.d("shshishi", "addNetworkInterceptor == request");
                        Response proceed = chain.proceed(request);
                        Log.d("shshishi", "addNetworkInterceptor == Response");
                        return proceed;
                    }
                })
                .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize))
                .cookieJar(new CookieJar() {
                    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<String, List<Cookie>>();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        Log.d("shshishi", "保存cookie之 url==" + url + " , cookieslist==" + cookies.toString());
                        cookieStore.put(url.host(), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {

                        List<Cookie> cookies = cookieStore.get(url.host());
                        List<Cookie> cookies1 = cookies != null ? cookies : new ArrayList<Cookie>();
                        Log.d("shshishishshishi", "取出cookie之 url==" + url + " , cookies1==" + cookies1.toString());
                        return cookies1;
                    }
                })

                .build();
    }

    /**
     * 重新配置okHttpClient的方法
     */
    public static void setOkHttpClient(OkHttpClient okHttpClient) {
        ZsyOk.okHttpClient = okHttpClient;
    }

    /**
     * 获取okHttpClient
     */
    public static OkHttpClient getOkHttpClient() {
        judgeClient();
        return okHttpClient;
    }


    /**
     * 判断okHttpClient是否为空的方法
     */
    private static void judgeClient() {
        if (okHttpClient == null) {
            initClient(okContext);
        }
    }

    /**
     * 缓存如果不设置,默认是使用缓存的
     * 在只使用网络数据时,也是写入网络缓存的
     */
    public static void get(String getUrl, Boolean isNet, Callback callback) {
        judgeClient();
        if (isNet != null) {
            if (isNet) {
                Request request = new Request.Builder().url(getUrl).cacheControl(CacheControl.FORCE_NETWORK).build();
                okHttpClient.newCall(request).enqueue(callback);
            } else {
                Request request = new Request.Builder().url(getUrl).cacheControl(CacheControl.FORCE_CACHE).build();
                okHttpClient.newCall(request).enqueue(callback);
            }
        } else {
            Request request = new Request.Builder().url(getUrl).build();
            okHttpClient.newCall(request).enqueue(callback);
        }
    }

    /**
     * post请求传入URl地址 请求requestBody 和响应回调callback
     */
    public static void post(String postUrl, RequestBody requestBody, Callback callback) {
        judgeClient();
        Request request = new Request.Builder().url(postUrl)
                .post(requestBody).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * get使用证书RSA联网
     */
    public static void getCertification() {
        OkHttpClient client = ZsyOk.getOkHttpClient();
        HostnameVerifier verifier = client.hostnameVerifier();
//        verifier.verify();
        boolean verify = verifier.verify("", null);
    }

    public static SSLContext getSSLContext(InputStream inputStream) throws Exception {
        //定义KeyStore,用于存储证书或者密钥
        //当前的KeyStore实例加载系统默认的证书,我们要将默认的证书清除
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null);
        //获取默认的加密方式
        String algorithm = TrustManagerFactory.getDefaultAlgorithm();
        //认证KeyStore
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(algorithm);
        tmf.init(keyStore);

        //设置服务端给我们的公钥证书
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        Certificate certificate = factory.generateCertificate(inputStream);
        //将证书设置到keystore中
        keyStore.setCertificateEntry("server", certificate);

        //通过SSL协议进行网络设置
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, tmf.getTrustManagers(), null);
        return context;
    }


    public static void get1(String getUrl, Boolean isNet, Callback callback) {
        judgeClient();
        if (isNet != null) {
            if (isNet) {
                Request request = new Request.Builder().url(getUrl).cacheControl(CacheControl.FORCE_NETWORK).build();
                okHttpClient.newCall(request).enqueue(callback);
            } else {
                Request request = new Request.Builder().url(getUrl).cacheControl(CacheControl.FORCE_CACHE).build();
                okHttpClient.newCall(request).enqueue(callback);
            }
        } else {
            Request request = new Request.Builder().url(getUrl).build();
            okHttpClient.newCall(request).enqueue(callback);
        }
    }


}
