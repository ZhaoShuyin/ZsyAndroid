package com.zsy.android.function.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zsy.android.R;

/**
 *
 */
public class BaseNetnctivity extends Activity {

    private EditText edittext;
    private Button button;
    private TextView textview;
    private ImageView imageView;
    private String strurl = "http://localhost:8080/hello";
    private String picurl = "http://localhost:8080/pic.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_net);

        edittext = (EditText) findViewById(R.id.edittext);
        imageView = findViewById(R.id.iv_net);
        button = (Button) findViewById(R.id.button);
        textview = (TextView) findViewById(R.id.textview);
        edittext.setText(strurl);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                baseNset();
            }
        });
        findViewById(R.id.button_pic).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                netPic();
            }
        });
    }

    private void baseNset() {
        final String strurl2 = edittext.getText().toString();
        if (TextUtils.isEmpty(strurl2)) {
            Toast.makeText(getApplicationContext(), "网址不能为空", Toast.LENGTH_SHORT).show();
        } else {
            new Thread() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(strurl2);
                        show(strurl2);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setConnectTimeout(6000);
                        connection.setRequestMethod("GET");
                        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        connection.setRequestProperty("Content-Length", "".length()+"");
                        if (200 == connection.getResponseCode()) {
                            InputStream stream = connection.getInputStream();
                            String result = Utils.getStr(stream);
                            show(result);
                        } else {
                            show("error");
                        }
                    } catch (Exception e) {
                    }
                }
            }.start();
        }
    }

    private final int GET_DATA_SUCCESS = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case GET_DATA_SUCCESS:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    imageView.setImageBitmap(bitmap);
                    break;
                default:
                    break;
            }

        }

        ;
    };

    private void netPic() {
        //缓存目录
        final String cachePath = getCacheDir().getAbsolutePath() + "/" + "logo.png";
        final File file = new File(cachePath);
        if (file != null && file.length() > 0) {
            Bitmap bm = BitmapFactory.decodeFile(cachePath);
            imageView.setImageBitmap(bm);
            show("使用图片缓存");
            return;
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(picurl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(6000);
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = connection.getInputStream();
                        //先缓存图片文件
                        FileOutputStream fos = new FileOutputStream(file);
                        int len = -1;
                        byte[] buffer = new byte[1024];
                        while ((len = inputStream.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                        }
                        fos.close();
                        inputStream.close();
                        Bitmap bitmap = BitmapFactory.decodeFile(cachePath);
                        Message msg = Message.obtain();
                        msg.what = GET_DATA_SUCCESS;
                        msg.obj = bitmap;
                        handler.sendMessage(msg);
                    } else {
                        show("error");
                    }
                } catch (Exception e) {
                    show("error");
                }
            }

            ;
        }.start();
    }


    private void show(final String result) {
        runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              textview.setText(result);
                          }
                      }
        );
    }

}
