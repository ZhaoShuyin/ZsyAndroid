package cn.azsy.zstokhttp.webjs;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import cn.azsy.zstokhttp.R;

/**
 * Created by zsy on 2017/6/10.
 */

public class WebJsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "tiaozhuan" ;
    WebView webView ;

    ProgressBar webPro;

    Button bt1,bt2,bt3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webjs);
        getIntentData();
        initView();
//        initSetting();
        initWebSetting();
//        loadUrlFile();//载入本地html文件
    }

    private void getIntentData() {
        Uri uri = getIntent().getData();
        if (uri != null) {
            // 完整的url信息
            String url = uri.toString();
            Log.e(TAG, "url: " + uri);
            // scheme部分
            String scheme = uri.getScheme();
            Log.e(TAG, "scheme: " + scheme);
            // host部分
            String host = uri.getHost();
            Log.e(TAG, "host: " + host);
            //port部分
            int port = uri.getPort();
            Log.e(TAG, "host: " + port);
            // 访问路劲
            String path = uri.getPath();
            Log.e(TAG, "path: " + path);
            List<String> pathSegments = uri.getPathSegments();
            // Query部分
            String query = uri.getQuery();
            Log.e(TAG, "query: " + query);
            //获取指定参数值
            String goodsId = uri.getQueryParameter("goodsId");
            Log.e(TAG, "goodsId: " + goodsId);
        }
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.web_zok);
        webPro = (ProgressBar) findViewById(R.id.pro_bar_web);
        webPro.setMax(100);
        bt1 = (Button) findViewById(R.id.bt1);
        bt1.setOnClickListener(this);
        bt2 = (Button) findViewById(R.id.bt2);
        bt2.setOnClickListener(this);
        bt3 = (Button) findViewById(R.id.bt3);
        bt3.setOnClickListener(this);
    }

    private void initWebSetting() {
        //支持javascript
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
        //任意比例缩放==调整图片至适合webview的大小
        webView.getSettings().setUseWideViewPort(true);//某些手机不设置会布局混乱
        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //缩放至屏幕的大小
        webView.getSettings().setLoadWithOverviewMode(true);
        //设置WEbView不使用缓存
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //允许webView弹窗处理
//        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebChromeClient(new MyWebChromeClient());
        //允许JS通过接口调用java
        webView.addJavascriptInterface(this,"wst");
    }


    @SuppressLint("JavascriptInterface")
    private void initSetting() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(this,"wst");
    }

//    private void loadUrlFile() {
//        webView.loadUrl("file:///android_asset/tianzhuan.html");
//
//    }
//    private void loadUrlFile() {
//        webView.loadUrl("file:///android_asset/callalipay.html");
//    }
    private void loadUrlFile() {
        webView.loadUrl("http://testpay.yizhibank.com/");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt1://载入第一个html文件
                loadUrlFile();
                break;
            case R.id.bt2:
                String alipays = "alipays://platformapi/startApp?appId=10000011&url=" +
                        "http%3a%2f%2fjh.yizhibank.com%2fapi%2f" +
                        "createOrder%3f+merchantOutOrderNo%3d201708020001%26merid%3d100001%26noncestr%3" +
                        "dtest%26notifyUrl%3dhttp%3a%2f%2fjh.yizhibank.com%2fapi%2fcallback%26orderMoney%3d" +
                        "1.00%26orderTime%3d20170802132205%26sign%3d8c284fa8fa7146abe53cd753c4427ba9";
                webView.loadUrl(alipays);
                break;
            case R.id.bt3:
//                webView.loadUrl("javascript:javacalljs()");
                webView.loadUrl("file:///android_asset/tianzhuan.html");
                break;
        }
    }


    @JavascriptInterface
    public void javaMethod(){
        Toast.makeText(this, "js无参调用了java函数", Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void javaMethod(String s){
        Toast.makeText(this, "js无参调用了java函数,参数=="+s, Toast.LENGTH_SHORT).show();
    }

    class MyWebChromeClient extends WebChromeClient{

        //当前网页加载速度,范围between 0 and 100
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            Log.i("webweb", "newProgress: == "+newProgress);
            webPro.setProgress(newProgress);
        }
        //当前网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, final String message, JsResult result) {
//            return super.onJsAlert(view, url, message, result);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialog.Builder(WebJsActivity.this)
                            .setTitle("自定义的标题>>提示")
                            .setMessage("message == "+message)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    webView.reload();//重写刷新页
//                                    finish();
                                }
                            })
                            .setNegativeButton("取消",null)
                            .show();
                }
            });
            result.confirm();//这里必须调用，否则页面会阻塞造成假死
            return true;


        }
    }


}
