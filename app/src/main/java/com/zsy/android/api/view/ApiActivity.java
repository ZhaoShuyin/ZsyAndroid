package com.zsy.android.api.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.zsy.android.MainActivity;
import com.zsy.android.R;
import com.zsy.zlib.phone.AppUtils;
import com.zsy.zlib.phone.PhoneUtil;

import java.io.File;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:自动补全下拉框EditView
 * </p>
 *
 * @author Zsy
 * @date 2019/7/1 16:06
 */

public class ApiActivity extends Activity {

    private String[] names = {"laozhang", "laoli", "laowang", "xiaoqiang", "xiaomao","张三","张武"};
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);
//        String host = getIntent().getData().getHost();
//        Toast.makeText(this, "getIntent().getData().getHost()\n" + host, Toast.LENGTH_SHORT).show();
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.actv_text);
        actv.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, names));
        imageView = findViewById(R.id.iv_api);
    }

    File file;

    public void testClick(View view) {
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "123.jpg");
        PhoneUtil.callGallery(this, 102);
    }

    public void install(View view) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "123.jpg");
        PhoneUtil.installApk(this, file, true);
        PhoneUtil.uninstallApk(this, "com.example.assist");
    }

    public void unInstall(View view) {
        PhoneUtil.uninstallApk(this, "com.example.assist");
    }

    public void camera(View view) {
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "123.jpg");
        PhoneUtil.callGallery(this, 101);
    }

    public void gallery(View view) {
        PhoneUtil.callGallery(this, 102);
    }

    public void crop(View view) {
        Uri imageUri = FileProvider.getUriForFile(this, "com.zsy.zlib" + ".provider", file);
        PhoneUtil.callCrop(this, imageUri, 103);
    }

    public void calendar(View view) {
        PhoneUtil.callCalendar(this);
    }

    public void callphone(View view) {
        PhoneUtil.callPhones(this, "10086");
    }

    public void message(View view) {
        PhoneUtil.sendMessage(this, "10086", "hello");
    }


    public void wh() {
        Point outSize = new Point();
        //这一行代码执行之后 point对象中就包含了屏幕的宽度和高度
        getWindowManager().getDefaultDisplay().getSize(outSize);
        int screenWidth = outSize.x;
        int screenHeight = outSize.y;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 102) {
            if (data != null) {
                Uri uri = data.getData();
                PhoneUtil.callCrop(this, uri, 103);
            }
        }
        if (requestCode == 101) {
//            Uri uri = Uri.fromFile(file);
            Uri imageUri = FileProvider.getUriForFile(this, "com.zsy.zlib" + ".provider", file);
            PhoneUtil.callCrop(this, imageUri, 103);
        }
        if (requestCode == 103) {
            Bitmap bitmap = data.getExtras().getParcelable("data");
            imageView.setImageBitmap(bitmap);
        }
    }
}
