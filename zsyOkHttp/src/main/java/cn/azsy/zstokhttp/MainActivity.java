package cn.azsy.zstokhttp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import cn.azsy.zstokhttp.animation.AnimationActivity;
import cn.azsy.zstokhttp.broadcast.BroadcastActivity;
import cn.azsy.zstokhttp.chart.ChartActivity;
import cn.azsy.zstokhttp.customswipe.CustomSwipeActivity;
import cn.azsy.zstokhttp.database.DataBaseActivity;
import cn.azsy.zstokhttp.game.GameActivity;
import cn.azsy.zstokhttp.generic.GenericActivity;
import cn.azsy.zstokhttp.jnitest.JniTestActivity;
import cn.azsy.zstokhttp.jsonparse.JsonParseActivity;
import cn.azsy.zstokhttp.judgeapear.JudgeApearActivity;
import cn.azsy.zstokhttp.popuwidow.SelectPicturePopupWindow;
import cn.azsy.zstokhttp.provide.ProvideActivity;
import cn.azsy.zstokhttp.recycleview.RecycleActivity;
import cn.azsy.zstokhttp.service.ServiceActivity;
import cn.azsy.zstokhttp.surface.SurfaceActivity;
import cn.azsy.zstokhttp.surface.dem.testSurfaceView;
import cn.azsy.zstokhttp.textureview.TextureViewUI;
import cn.azsy.zstokhttp.upprogress.UpProgressUI;
import cn.azsy.zstokhttp.utiltest.UtilsTestActivity;
import cn.azsy.zstokhttp.views.ViewsActvity;
import cn.azsy.zstokhttp.webjs.WebJsActivity;
import cn.azsy.zstokhttp.zsyokhttp.zok.cookietest.CookieTestActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String urlBanner = "http://101.200.146.62:81/apicore/index.php/index/get_banner";
    String urlStr = "http://192.168.1.103:8080/HttpsTest/test1";
    String url = "http://101.200.146.62:81/apicore/index/getcategory";
    String urlLogin = "http://192.168.1.103:8080/Login_demo/LoginServlet";
    String urlApk = "http://192.168.1.103:8080/HttpsTest/apk.apk";
    private String urlHttps = "http://192.168.1.103:8080/HttpsTest/test1";


    ProgressBar progressBar1;
    Button bt0, bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, bt10, bt11, bt12,
            bt13, bt14, bt15, bt16, bt17, bt18, bt19, bt20, bt21, bt22, bt23, bt24;
//    private DownLoador downLoador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt0 = (Button) findViewById(R.id.bt0);
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bt3 = (Button) findViewById(R.id.bt3);
        bt4 = (Button) findViewById(R.id.bt4);
        bt5 = (Button) findViewById(R.id.bt5);
        bt6 = (Button) findViewById(R.id.bt6);
        bt7 = (Button) findViewById(R.id.bt7);
        bt8 = (Button) findViewById(R.id.bt8);
        bt9 = (Button) findViewById(R.id.bt9);
        bt10 = (Button) findViewById(R.id.bt10);
        bt11 = (Button) findViewById(R.id.bt11);
        bt12 = (Button) findViewById(R.id.bt12);
        bt13 = (Button) findViewById(R.id.bt13);
        bt14 = (Button) findViewById(R.id.bt14);
        bt15 = (Button) findViewById(R.id.bt15);
        bt16 = (Button) findViewById(R.id.bt16);
        bt17 = (Button) findViewById(R.id.bt17);
        bt18 = (Button) findViewById(R.id.bt18);
        bt19 = (Button) findViewById(R.id.bt19);
        bt20 = (Button) findViewById(R.id.bt20);
        bt21 = (Button) findViewById(R.id.bt20);
        bt22 = (Button) findViewById(R.id.bt20);
        bt23 = (Button) findViewById(R.id.bt20);
        bt24 = (Button) findViewById(R.id.bt20);
        bt0.setOnClickListener(this);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);
        bt6.setOnClickListener(this);
        bt7.setOnClickListener(this);
        bt8.setOnClickListener(this);
        bt9.setOnClickListener(this);
        bt10.setOnClickListener(this);
        bt11.setOnClickListener(this);
        bt12.setOnClickListener(this);
        bt13.setOnClickListener(this);
        bt14.setOnClickListener(this);
        bt15.setOnClickListener(this);
        bt16.setOnClickListener(this);
        bt17.setOnClickListener(this);
        bt18.setOnClickListener(this);
        bt19.setOnClickListener(this);
        bt20.setOnClickListener(this);
        bt21.setOnClickListener(this);
        bt22.setOnClickListener(this);
        bt23.setOnClickListener(this);
        bt24.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt0:
                break;
            case R.id.bt1://网络测试
//                startActivity(new Intent(this,ZsyOkTestActivity.class));
                startActivity(new Intent(this,UpProgressUI.class));
                break;
            case R.id.bt2://工具类测试
                startActivity(new Intent(this,UtilsTestActivity.class));
                break;
            case R.id.bt3://json解析
                startActivity(new Intent(this, JsonParseActivity.class));
                break;
            case R.id.bt4://cookie测试
                startActivity(new Intent(this, CookieTestActivity.class));
                break;
            case R.id.bt5://数据库
                startActivity(new Intent(this, DataBaseActivity.class));
                break;
            case R.id.bt6://RecycleView
                startActivity(new Intent(this, RecycleActivity.class));
                break;
            case R.id.bt7://自定义View
                startActivity(new Intent(this, ViewsActvity.class));
                break;
            case R.id.bt8://JS
                startActivity(new Intent(this, WebJsActivity.class));
                break;
            case R.id.bt9://刷新
                startActivity(new Intent(this,CustomSwipeActivity.class));
                break;
            case R.id.bt10://SurfaceView
                startActivity(new Intent(this,SurfaceActivity.class));
                break;
            case R.id.bt11://图表
                startActivity(new Intent(this,ChartActivity.class));
                break;
            case R.id.bt12://动画
                startActivity(new Intent(this,AnimationActivity.class));
                break;
            case R.id.bt13://Jni
                startActivity(new Intent(this,JniTestActivity.class));
                break;
            case R.id.bt14:
                startActivity(new Intent(this,GameActivity.class));
                break;
            case R.id.bt15:
                startActivity(new Intent(this,testSurfaceView.class));
                break;
            case R.id.bt16:
//                popuWidow();
                startActivity(new Intent(this,TextureViewUI.class));
                break;
            case R.id.bt17:
                startActivity(new Intent(this,JudgeApearActivity.class));
                break;
            case R.id.bt18:
                startActivity(new Intent(this,ServiceActivity.class));
                break;
            case R.id.bt19:
                startActivity(new Intent(this,BroadcastActivity.class));
                break;
            case R.id.bt20:
                startActivity(new Intent(this,ProvideActivity.class));
                break;
            case R.id.bt21:
                startActivity(new Intent(this,GenericActivity.class));
                break;
            case R.id.bt22:
                startActivity(new Intent(this,ProvideActivity.class));
                break;
            case R.id.bt23:
                startActivity(new Intent(this,ProvideActivity.class));
                break;
            case R.id.bt24:
                startActivity(new Intent(this,ProvideActivity.class));
                break;
        }
    }
    private SelectPicturePopupWindow mSelectPicturePopupWindow;
    public void popuWidow(){
        mSelectPicturePopupWindow = new SelectPicturePopupWindow(this);
        mSelectPicturePopupWindow.setOnSelectedListener(new SelectPicturePopupWindow.OnSelectedListener() {
            @Override
            public void OnSelected(View v, int position) {
                switch (position) {
                    case 0:
                        // "拍照"按钮被点击了
                        Toast.makeText(MainActivity.this, "点击PupoWindow拍照", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        // "从相册选择"按钮被点击了
                        Toast.makeText(MainActivity.this, "点击PupoWindow相册", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        // "取消"按钮被点击了
                        mSelectPicturePopupWindow.dismissPopupWindow();
                        break;
                }
            }
        });
        mSelectPicturePopupWindow.showPopupWindow(this);
    }

    /*private void showHeadersMessage(Response response) {
        Log.i("zsyokhttp","使用== "+ ZsyOk.useNew+" , response.toString()== "+response.toString()+"\n");
        Log.i("zsyokhttp","response.headers==  "+response.headers());
        Log.i("zsyokhttp","response.message==  "+response.message());
        Log.i("zsyokhttp","response.code==  "+response.code());
        Log.i("zsyokhttp","response.protocol==  "+response.protocol());
    }*/

    /*//简单GET请求
    private void BT1() {
        startActivity(new Intent(this, ZsyOkTestActivity.class));
        ZsyOk.useNew = !ZsyOk.useNew;
        ZsyOk.get(urlBanner, null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showResult("简单GET请求响应失败","","");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                showHeadersMessage(response);
                String net = "网络响应== " + response.networkResponse();
                String cache = "缓存响应== " + response.cacheResponse();
                String a = response.body().string();
                showResult(a,net,cache);
            }
        });
    }*/



  /*  //GET只使用网络,实时数据
    private void BT2() {
        ZsyOk.get(url, true, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showResult("GET只使用网络,实时数据响应失败","","");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String net = "网络响应== " + response.networkResponse();
                String cache = "缓存响应== " + response.cacheResponse();
                String a = response.body().string();
                showResult(a,net,cache);
            }
        });
    }*/

    /*//GET只使用使用网络缓存
    private void BT3() {
        ZsyOk.get(url, false, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showResult("GET只使用使用网络缓存响应失败","","");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String net = "网络响应== " + response.networkResponse();
                String cache = "缓存响应== " + response.cacheResponse();
                Log.i("zsyokhttp", net);
                Log.i("zsyokhttp", cache);
                String a = response.body().string();
                showResult(a,net,cache);
            }
        });
    }*/

   /* //GET使用参数登录测试
    private void BT4() {
        ZsyOk.get(urlLogin+"?username=abc&password=123", null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showResult("GET使用参数登录测试响应失败","","");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String net = "网络响应== " + response.networkResponse();
                String cache = "缓存响应== " + response.cacheResponse();
                Log.i("zsyokhttp", net);
                Log.i("zsyokhttp", cache);
                String a = response.body().string();
                Log.i("zsyokhttp", a);
                showResult(a,net,cache);
            }
        });
    }*/

    /*//简单Post默认只用网络
    private void BT5() {
        RequestBody requestBody = new FormBody.Builder()
                .add("username", "abc")
                .add("password", "123").build();
        ZsyOk.post(urlLogin, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showResult("简单Post响应失败","","");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String net = "网络响应== " + response.networkResponse();
                String cache = "缓存响应== " + response.cacheResponse();
                Log.i("zsyokhttp", net);
                Log.i("zsyokhttp", cache);
                String a = response.body().string();
                showResult(a,net,cache);
            }
        });
    }*/

   /* //post只用缓存
    private void BT7() {

    }*/

   /* 查询
   private void BT8() {
        String s = DiskCacheHelper.readJson(this, "文件夹名",user.getText().toString(), "默认值");
        if (s.equals("默认值")){
            Toast.makeText(this, "查询缓存没有该key和value", Toast.LENGTH_SHORT).show();
        }else{
            showResult(s,s,s);
        }
        startActivity(new Intent(this, WebJsActivity.class));
    }*/

   /* //断点续传开始/继续
    private void BT9() {
        progressBar1.setMax(6312092);
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
    }*/
   /*
    //断点续传暂停/取消
    private void BT10() {
        downLoador.stopLoad();
    }*/

    /**
     *初始化下载对象
     *//*
    private void BT11() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                downLoador = new DownLoador(urlApk, new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/aaaaaaa.apk"),
                        new DownLoador.ProgressListener() {
                            @Override
                            public void progress(long progress) {
                                Log.i("loadload", "progress: == "+progress);
                                progressBar1.setProgress((int) progress);
                                showProgressText((int) progress);
                            }
                            @Override
                            public void finish() {
                                showResult("下载完成","下载完成","下载完成");
                            }
                        });
            }
        }.start();
    }*/

   /* private void BT12() {
//        ZsyOk.useNew = !ZsyOk.useNew;

//        showString("网络可用== "+MobileStatus.judgeNetAble(this));
    }*/

  /*  private void BT13() {
//        SpSave.spPutString(this,user.getText().toString(),word.getText().toString());
        showString("网络连接=="+MobileStatus.judgeNetConnect(this));
    }*/


   /* private void BT14() {
//        String s = SpSave.spGetString(this, user.getText().toString(), "默认值");
//        showString("SP==== "+s);
    }*/

   /* private void BT15() {
        showString("计算dp值== "+MobileData.getDpPx(this,100f));
    }*/

   /* private void BT16() {
        showString("计算sp值== "+MobileData.getSpPx(this,100f));
    }*/


    /*private void BT17() {
        showString("标准格式系统时间== "+MobileStatus.getSystemTime());
    }*/

   /* private void BT18() {
        *//*
        SQLiteDatabase database2 = openHelper.getReadableDatabase();
			Cursor cursor = database2.rawQuery("select * from info", null);
			while(cursor.moveToNext()){
				String name = cursor.getString(1);
				String phone = cursor.getString(2);
				person = new Person();
				person.name = name;
				person.phone = phone;
				//把person 保存到集合中
				persons.add(person);
				//把person对象置空 等待下一个数据传入
				person = null;
			}
			for(Person person1:persons){
				System.out.println(person1);
			}
			//游标遍历之后 数据集合中已经有数据了 可以通过listview来展示
			lv_person.setAdapter(adapter);
			//关闭游标和数据库
			cursor.close();
			database2.close();
        * *//*
        Log.d("datadatabase","开始查询");
        SQLiteDatabase database = helper.getReadableDatabase();
//        Cursor cursor = database.rawQuery("select * from info where name = ? ; ", new String[]{"王五","张三","李四"});
        Cursor cursor = database.rawQuery("select * from info where name = ? ; ", null);
        while(cursor.moveToNext()){
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                String s = cursor.getString(i);
                Log.d("datadatabase", "cursor查询--->> "+s);
            }
        }
        database.close();
    }*/

    /*private void BT19() {
        SQLiteDatabase database = helper.getReadableDatabase();
        database.execSQL("insert into info(name,phone) values('张三','13777777')");
        database.close();
    }*/
/*
    private void BT20() {
        helper = new DataOpenHelper(this,"zhaosy",1);
    }*/
//    private DataOpenHelper helper;

    /*private boolean downloadImg(final String urlStr,
                                final OutputStream outputStream) {
        HttpURLConnection conn = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(conn.getInputStream(), 8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            int len = 0;
            while ((len = in.read()) != -1) {
                out.write(len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                conn.disconnect();
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }*/

    /**
     * 写入图片
     */
    /*private void BT21() {
        String trim = user.getText().toString().trim();
        try {
            diskLrucache = DiskLruCache.open(getDiskCacheDir(this,"tupian"), 1, 1, 5*1024 * 1024);
            //缓存目录下的文件夹名称
            DiskLruCache.Editor editor = diskLrucache.edit(trim);//会以key作为文件名保存
            final OutputStream outputStream = editor.newOutputStream(0);//该key引向的某个索引文件
            new Thread(new Runnable() {
                @Override
                public void run() {
                    downloadImg("http://f.hiphotos.baidu.com/image/pic/item/58ee3d6d55fbb2fbfe951a134d4a20a44623dc71.jpg",outputStream);
                }
            }).start();
            editor.commit();
            diskLrucache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

//    private void BT22() {
//        String trim = user.getText().toString().trim();
//        try {
//            diskLrucache.remove(trim);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    DiskLruCache diskLrucache = null;

    /**
     *写入文件
     */
    /*private void BT23() {
        startActivity(new Intent(this, RecycleActivity.class));
        String trim = user.getText().toString().trim();
        String value = word.getText().toString().trim();
        try {
            diskLrucache = DiskLruCache.open(getDiskCacheDir(this,"mingzi"), 1, 1, 1024 * 1024);
                                                                                             //缓存目录下的文件夹名称
            DiskLruCache.Editor editor = diskLrucache.edit(trim);//会以key作为文件名保存
            OutputStream outputStream = editor.newOutputStream(0);//该key引向的某个索引文件
            outputStream.write(value.getBytes());
            editor.commit();
            diskLrucache.flush();//如果不刷新,就会重复覆盖写入入同一个文件中
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "存入", Toast.LENGTH_SHORT).show();
    }*/

    /**
     * 读取文件
     */
   /* private void BT24() {
        startActivity(new Intent(this, ViewsActvity.class));
        String trim = user.getText().toString().trim();

        String pathname = diskLrucache.getDirectory()  + File.separator + trim + "." + 0;
        File file = new File(pathname);
        if (file.exists()){
            Toast.makeText(this, pathname+"文件存在", Toast.LENGTH_SHORT).show();
            getRes(trim);
        }else{
            Toast.makeText(this, pathname+"文件不存在", Toast.LENGTH_SHORT).show();
        }
    }*/

    /*private void getRes(String trim) {
        try {
            DiskLruCache.Snapshot snapshot = diskLrucache.get(trim);
            InputStream inputStream = snapshot.getInputStream(0);//该key引向的某个索引文件
            byte[] bytes = input2byte(inputStream);
            String s = new String(bytes);
            showResult(s,s,s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "取出", Toast.LENGTH_SHORT).show();
    }*/

    /*private File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        //如果sd卡存在并且没有被移除
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }*/

    /*public static final byte[] input2byte(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }*/
}
