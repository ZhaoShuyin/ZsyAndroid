package com.zsy.android.function.xml;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zsy.android.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:Xml的约束,解析等
 * </p>
 *
 * @author Zsy
 * @date 2019/8/15 10:02
 */

public class XmlActivity extends Activity {
    TextView tShow;
    String TAG = "XmlActivity";
    File file;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xml);
        tShow = findViewById(R.id.tv_show);
        file = new File(getCacheDir().getAbsolutePath() + "/book.xml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                Log.e(TAG, "onCreate: 创建file文件");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "onCreate: file文件已存在");
        }
        try {
            InputStream inputStream = getAssets().open("book.xml");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            int l;
            while ((l = inputStream.read()) != -1) {
                fileOutputStream.write(l);
            }
            fileOutputStream.close();
            inputStream.close();
            Log.e(TAG, "onCreate: 写入文件完毕" + file.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder builder = new StringBuilder();
        try {
            FileReader reader = new FileReader(file);
            char[] chs = new char[1024];
            int len = 0;                // 作用: 记录读取到的有效的字符个数
            while ((len = reader.read(chs)) != -1) {
                builder.append(new String(chs, 0, len));
            }
            tShow.setText(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
       /* AssetManager assetManager = getAssets();
        try {
            AssetFileDescriptor fileDescriptor1 = assetManager.openFd("book.xml");
            Log.e(TAG, "fileDescriptor1: " + fileDescriptor1.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "异常异常 " + e.toString());
        }*/
    }

    public void domAll(View view) {
        if (file == null) {
            Toast.makeText(this, "file == null", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            String s = DomParse.domParseTest(file, "书");
            tShow.setText(s);
        } catch (Exception e) {
            e.printStackTrace();
            tShow.setText(e.toString());
        }
//        InputStream inputStream = getResources().openRawResource(R.raw.book);
    }

}
