package cn.azsy.zstokhttp.jnitest;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;

import cn.azsy.zstokhttp.R;
import cn.azsy.zstokhttp.utils.turndata.FileMd5;
import cn.zsy.bspatch.BsPatch;

/**
 * Created by zsy on 2017/6/25.
 */

public class JniTestActivity extends AppCompatActivity {
    TextView tvJniShow;
    EditText etJni;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni_test);
        tvJniShow = (TextView) findViewById(R.id.tv_jni_show);
        etJni = (EditText) findViewById(R.id.et_jni);
    }

    public void reSetText(View view) {
        tvJniShow.setText("恢复默认");
    }

    //    native void callJni();
//    public native String helloC();
    public void callJni(View view){
        String jniStr = JniUtils.getStringFromJni();
        tvJniShow.setText(jniStr);
    }

    public void jniPatchFile(View view){
        int bspatch = BsPatch.bspatch(Environment.getExternalStorageDirectory() + File.separator + "456" + File.separator + "oldold.apk",
                Environment.getExternalStorageDirectory() + File.separator + "456" + File.separator + "newnew.apk",
                Environment.getExternalStorageDirectory() + File.separator + "456" + File.separator + "diff.patch");
        tvJniShow.setText("合并文件结果"+bspatch);
    }


    public void getMD51(View view){
        String fileMD5 = FileMd5.getFileMD5(new File(Environment.getExternalStorageDirectory() + File.separator + "123" + File.separator + "new.apk"));
        tvJniShow.setText("校验文件MD5 == "+fileMD5);
    }

    public void getMD52(View view){
        String fileMD5 = FileMd5.getFileMD5(new File(Environment.getExternalStorageDirectory() + File.separator + "456" + File.separator + "diff.patch"));
        tvJniShow.setText("patch文件MD5 == "+fileMD5);
    }

    public void getMD53(View view){
        String fileMD5 = FileMd5.getFileMD5(new File(Environment.getExternalStorageDirectory() + File.separator + "456" + File.separator + "oldold.apk"));
        tvJniShow.setText("旧文件MD5 == "+fileMD5);
    }

    public void getMD54(View view){
        String fileMD5 = FileMd5.getFileMD5(new File(Environment.getExternalStorageDirectory() + File.separator + "456" + File.separator + "newnew.apk"));
        tvJniShow.setText("新文件MD5 == "+fileMD5);
    }
}
