package com.zsy.ui;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zsy.jni.BsPatchUtil;
import com.zsy.jni.CalculateTools;
import com.zsy.jni.JniTest;
import com.zsy.jni.MonitorJni;
import com.zsy.jni.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

/**
 * JNI 即 Java Native Interface
 * ARMv5——armeabi
 * ARMv7 ——armeabi-v7a
 * ARMv8——arm64- v8a
 * x86——x86
 * MIPS ——mips
 * MIPS64——mips64
 * x86_64——x86_64
 * <p>
 * ndk_build 编译后 \build\intermediates\ndkBuild\debug\obj\local
 * <p>
 * Cmake方式 编译后 \build\intermediates\cmake\debug\obj
 */
public class MainActivity extends AppCompatActivity {

    TextView tvShow;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvShow = findViewById(R.id.tv_show);
        editText = findViewById(R.id.et_jni);
    }

    /**
     * 简单C获取字符串
     *
     * @param view
     */
    public void getString(View view) {
        String string = JniTest.getStringFromJni();
        tvShow.setText(string);
    }

    //加
    public void add(View view) {
        String s = editText.getText().toString();
        int anInt;
        try {
            anInt = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }
        int numebr = CalculateTools.add(anInt, 2);
        tvShow.setText(numebr + "");
    }

    //减
    public void sub(View view) {
        String s = editText.getText().toString();
        int anInt;
        try {
            anInt = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }
        int numebr = CalculateTools.subtract(anInt, 2);
        tvShow.setText(numebr + "");
    }

    //乘
    public void mul(View view) {
        String s = editText.getText().toString();
        int anInt;
        try {
            anInt = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }
        int numebr = CalculateTools.multiply(anInt, 2);
        tvShow.setText(numebr + "");
    }

    //除
    public void div(View view) {
        String s = editText.getText().toString();
        int anInt;
        try {
            anInt = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }
        int numebr = CalculateTools.divide(anInt, 2);
        tvShow.setText(numebr + "");
    }

    /**
     * 开始C调用(开子线程)
     *
     * @param view
     */
    public void startMonitor(View view) {
        final MonitorJni.MonitorListener listener = new MonitorJni.MonitorListener() {
            @Override
            public void call(final int value) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvShow.setText(String.valueOf(value));
                    }
                });
            }
        };
        new Thread() {
            @Override
            public void run() {
                MonitorJni.startJni(listener);
            }
        }.start();
    }

    /**
     * 停止C调用
     *
     * @param view
     */
    public void stopMonitor(View view) {
        MonitorJni.stop();
    }

    /**
     * Java方法,可供C调用
     *
     * @param value
     */
    public void setValue(int value) {
        tvShow.setText("monitor : " + value);
    }


    /**
     * Studio Jni 学习 http://www.mamicode.com/info-detail-1609637.html
     * 拓展学习https://blog.csdn.net/shichaosong/article/details/21179703
     */
    String oldpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch/old.txt";
    String newpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch/new.txt";
    String patchpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch/diff.patch";

    /**
     * 旧,新文件Content
     *
     * @param view
     */
    public void bsdiffiles(View view) {
        File oldfile = new File(oldpath);
        File filepatch = new File(patchpath);
        Toast.makeText(this, "" + oldfile.length(), Toast.LENGTH_SHORT).show();
        try {
            FileInputStream fileInputStream = new FileInputStream(oldfile);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "GBK");
            char[] ch = new char[2014];
            int len = inputStreamReader.read(ch);
            String s = new String(ch);
            tvShow.setText("旧文件 ==" + oldfile.length() + "\n" + s + "\n\n差分包==" + filepatch.length());
            inputStreamReader.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * 合并差分包
     *
     * @param view
     */
    public void patch(View view) {
        try {
            BsPatchUtil.bspatch(oldpath, newpath, patchpath);
        } catch (Exception e) {
            e.printStackTrace();
            tvShow.setText("合并异常 " + System.currentTimeMillis());
            return;
        }
        File file = new File(newpath);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "GBK");
            char[] ch = new char[2014];
            int len = inputStreamReader.read(ch);
            String s = new String(ch);
            tvShow.setText("新文件 ==" + file.length() + "\n" + s);
            inputStreamReader.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public void dele(View view) {
        File file = new File(newpath);
        if (file.exists()) {
            file.delete();
            tvShow.setText("删除了");
        }
    }


    /**
     * 简单C++调用
     *
     * @param view
     */
    public void cpptest(View view) {
        String content = "";
        try {
            File file = new File(oldpath);
            InputStream instream = new FileInputStream(file);
            if (instream != null) {
                InputStreamReader inputreader
                        = new InputStreamReader(instream, "UTF-8");
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line = "";
                //分行读取
                while ((line = buffreader.readLine()) != null) {
                    content += line + "\n";
                }
                instream.close();//关闭输入流
            }
            tvShow.setText(content);
        } catch (Exception e) {
            Log.d("TestFile", e.getMessage());
        }

    }

    /**
     * C++调用详情
     *
     * @param view
     */
    public void cppdetail(View view) {

    }


}
