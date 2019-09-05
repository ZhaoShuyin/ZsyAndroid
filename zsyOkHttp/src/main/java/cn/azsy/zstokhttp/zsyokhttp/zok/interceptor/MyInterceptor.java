package cn.azsy.zstokhttp.zsyokhttp.zok.interceptor;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cn.azsy.zstokhttp.utils.turndata.MD5;
import cn.azsy.zstokhttp.zsyokhttp.zok.ZsyOk;
import cn.azsy.zstokhttp.zsyokhttp.zok.diskcache.DiskCacheHelper;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by zsy on 2017/6/2.
 */

public class  MyInterceptor {

    static String json = "{这是缓存\"retcode\":2000,\"msg\":\"获取成功！\",\"data\":[{\"link\":\"\\u82f9\\u679c\",\"img\":\"banner\\/20170228\\/10679607268103.jpg\",\"type\":\"1\"},{\"link\":\"\\u8bdd\\u8d39\",\"img\":\"banner\\/20170328\\/53323424671971.jpg\",\"type\":\"1\"},{\"link\":\"xxx\",\"img\":\"banner\\/20170328\\/92209139672033.jpg\",\"type\":\"3\"},{\"link\":\"apicore\\/info\\/pk_shop_list\",\"img\":\"banner\\/20170516\\/70544691904291.jpg\",\"type\":\"4\"}]}";

    /**
     * 自定义的拦截器类,修改响应头参数,对一般的Get请求进行缓存
     */
    public static class getCacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            Log.i("shshishi", "addNetworkInterceptor,原来的响应头 ==  " + response.headers() + "");
            Response response1 = response.newBuilder()
                    .removeHeader("Pragma")//默认是不使用缓存Pragma: no-cache
                    .addHeader("Pragma", "cache")//可修改为Pragma: cache
                    .removeHeader("Cache-Control")//删除原来的"Cache-Control"
                    //注:使用Header参数,控制缓存,只在网络状态不变,情况下适用
                    .header("Cache-Control", "max-age=" + 60)//缓存保持60秒内使用网络缓存,没有设置过时间的默认网络,缓存都使用
                    .build();
            Log.i("shshishi", "addNetworkInterceptor,修改的响应头 ==  " + response1.headers() + "");
            return response1;
        }
    }

    /**
     * 自定义post请求拦截器,通过diskLruCache文件进行缓存
     */
    public static class postCacheIntercptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            if (request.method().equals("POST")) {
                response = new Response.Builder()
                        .addHeader("Data", "Fri, 22 Jun 2017 02:47:26 GMT")//日期
                        .addHeader("Server", "Apache")
                        .addHeader("X-Powered-By", "PHP/5.3.29")
                        .addHeader("Set-Cookie", "PHPSESSID=r2meogrqi4s57q30geq9lbtnj1; path=/")
                        .addHeader("Expires", "Thu, 19 Nov 1981 08:52:00 GMT")
                        .addHeader("Cache-Control", "no-store, no-cache, must-revalidate, post-check=0, pre-check=0")
                        .addHeader("Pragma", "no-cache")
                        .addHeader("Vary", " Accept-Encoding")
                        .addHeader("Connection", "close")
                        .addHeader("Content-Type", "text/html;charset=utf-8")
                        .body(ResponseBody.create(MediaType.parse("application/json"), json.getBytes()))
                        .request(request)//对应特定的请求,必须使用
                        .protocol(Protocol.HTTP_1_1)//使用http/1.1标准
                        .message("OK")//响应信息OK
                        .code(200)  //响应码200
                        .build();
            }
            return response;
        }
    }

    /**
     * 自定义post请求拦截器,通过diskLruCache文件进行缓存
     */
    public static class postCacheIntercptor2 implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            boolean connect = judgeNetAble(ZsyOk.okContext);
            if (connect || request.method().equals("GET")) {
//                 response = chain.proceed(request);

            } else {
                response = new Response.Builder()
                        .addHeader("Data", "Fri, 22 Jun 2017 02:47:26 GMT")//日期
                        .addHeader("Server", "Apache")
                        .addHeader("X-Powered-By", "PHP/5.3.29")
                        .addHeader("Set-Cookie", "PHPSESSID=r2meogrqi4s57q30geq9lbtnj1; path=/")
                        .addHeader("Expires", "Thu, 19 Nov 1981 08:52:00 GMT")
                        .addHeader("Cache-Control", "no-store, no-cache, must-revalidate, post-check=0, pre-check=0")
                        .addHeader("Pragma", "no-cache")
                        .addHeader("Vary", " Accept-Encoding")
                        .addHeader("Connection", "close")
                        .addHeader("Content-Type", "text/html;charset=utf-8")
                        .body(ResponseBody.create(MediaType.parse("application/json"), json.getBytes()))
                        .message("OK")//响应信息OK
                        .code(200)  //响应码200
                        .build();
            }
            return response;
        }
    }

    /**
     * 自定义post请求拦截器,通过diskLruCache文件进行缓存
     */
    public static class postCacheIntercptor3 implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = null;
            int maxAge = 60; //缓存60秒
            Log.i("shshishi", "判断请求方式==" + request.method());
            if (request.method().equals("POST")) {//Post请求处理
                Log.i("shshishi", "判断为POST请求");
                String url = MD5.md5TurnHexadecimal(request.url().toString(),false);
                Log.i("shshishi", "判断网络连接----> " + judgeNetConnect(ZsyOk.okContext));
                if (judgeNetConnect(ZsyOk.okContext)) {
                    //有网络连接写入数据
                    Log.i("shshishi", "判断有网络连接");
                    if (DiskCacheHelper.isKeyHasSave(ZsyOk.okContext, "post", url)) {//有该url的post缓存
                        Log.i("shshishi", "判断有该url的post缓存");
                        response = chain.proceed(request);
                    } else {                          //没有该url的post缓存
                        Log.i("shshishi", "判断没有该url的post缓存,开始写入");
                        Response originalResponse = chain.proceed(request);//连接网络
                        MediaType type = originalResponse.body().contentType();//获取响应的编码格式
                        byte[] bs = originalResponse.body().bytes();
                        response = originalResponse.newBuilder()  //重构post
                                .removeHeader("Pragma")
                                .removeHeader("Cache-Control")
                                .header("Cache-Control", "public, max-age=" + maxAge)
                                //重新构建body，原因在于body只能调用一次，之后就关闭了。
                                .body(ResponseBody.create(type, bs))
                                .build();
                        DiskCacheHelper.writeJson(ZsyOk.okContext, "post", url, new String(bs));//写入数据
                    }
                } else {
                    //无网络连接,读取数据
                    Log.i("shshishi", "判断没有网络连接,读取缓存,重建response");
                    String json = DiskCacheHelper.readJson(ZsyOk.okContext, "post", url, "没有key对应的缓存");
                    Log.i("shshishi", "读取缓存json==  " + json);
//                    Response res = chain.proceed(request);
                    response = new Response.Builder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxAge)
                            .body(ResponseBody.create(MediaType.parse("sa"), json.getBytes()))
                            .request(request)
                            .protocol(Protocol.HTTP_1_1)
                            .code(200)
                            .message("OK")//响应信息OK
                            .build();
                   /* response =res.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + "maxStale")
                            .body(ResponseBody.create(MediaType.parse("sa"),json.getBytes()))
                            .request(request)
                            .protocol(Protocol.HTTP_1_1)
                            .message("OK")//响应信息OK
                            .code(200)
                            .build();*/

                }
            } else {  //Get等其他请求处理
                Log.i("shshishi", "判断为非POST请求");
                response = chain.proceed(request);
////                Log.i("shshishi", "addNetworkInterceptor,原来的响应头 ==  " + response.headers() + "");
//                response = response.newBuilder()
//                        .removeHeader("Pragma")//默认是不使用缓存Pragma: no-cache
//                        .addHeader("Pragma", "cache")//可修改为Pragma: cache
//                        .removeHeader("Cache-Control")//删除原来的"Cache-Control"
//                        .header("Cache-Control", "max-age=" + 60)//缓存保持60秒内使用网络缓存,没有设置过时间的默认网络,缓存都使用
//                        .build();
////                Log.i("shshishi", "addNetworkInterceptor,修改的响应头 ==  " + response.headers() + "");
            }
            return response;
        }

    }

    /**
     * 手动缓存网络数据,在无网状态下调用
     */
    public static class CacheIntercptor implements Interceptor {

        String method;
        String url;
        Response response = null;

        Context context;

        public CacheIntercptor(Context context) {
            this.context = context;
        }

        int maxAge = 60;//缓存60秒

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
//            Response response = null;
            method = request.method();
            url = md5TurnHexadecimal(request.url().toString());

            if (judgeNetConnect(context)) {//有网状态下
                Log.d("shshishi", "网络可以连接");
                boolean keyHasSave = DiskCacheHelper.isKeyHasWrite(context, method, url);
                if (!keyHasSave) {
                    Log.d("shshishi", "没有缓存过,写入缓存");
                    Response originalResponse = chain.proceed(request);//连接网络
                    MediaType type = originalResponse.body().contentType();//获取响应的编码格式
                    byte[] bs = originalResponse.body().bytes();
                    response = originalResponse.newBuilder()  //重构post
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .body(ResponseBody.create(type, bs))
                            .build();
                    DiskCacheHelper.writeJson(context, method, url, new String(bs));//写入数据
                } else {
                    Log.d("shshishi", "已经缓存过,正常使用");
                    response = chain.proceed(request);
                }
            } else {//无网状态下读取缓存
                Log.d("shshishi", "无网络连接,检查缓存");
                if (DiskCacheHelper.isKeyHasSave(context, method, url)) {
                    Log.d("shshishi", "有缓存,读取显示");
                    String json = DiskCacheHelper.readJson(context, method, url, "没有key对应的缓存");
                    response = new Response.Builder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxAge)
                            .body(ResponseBody.create(MediaType.parse("sa"), json.getBytes()))
                            .request(request)
                            .protocol(Protocol.HTTP_1_1)
                            .code(200)
                            .message("OK")//响应信息OK
                            .build();
                } else {
                    Log.d("shshishi", "无缓存,正常联网");
                    response = chain.proceed(request);
                }
            }
            return response;
        }
    }

    public static String md5TurnHexadecimal(String s) {
        StringBuilder res = null;
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes());
            byte[] hash = m.digest();
            res = new StringBuilder();
            for (byte b : hash) {
                if ((b & 0xFF) < 0xF) {//小于16只有一位就在前面补0
                    res.append("0");
                }
                res.append(Integer.toHexString(b & 0xFF).toUpperCase());
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return res.toString();
    }

    public static String md(String s) {
        String MD5 = null;
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes());
            byte[] hash = m.digest();
            MD5 = new String(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return MD5;
    }

    /**
     * 判断网络是否可用
     */
    public static boolean judgeNetAble(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }
        return false;
    }

    /**
     * 判断网络是否连接
     */
    public static boolean judgeNetConnect(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.getState() == NetworkInfo.State.CONNECTED;
        }
        return false;
    }
}
