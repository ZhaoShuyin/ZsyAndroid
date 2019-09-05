package com.zsy.android.api.def;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.zsy.android.R;

import java.io.File;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:测试作为文件打开的默认程序
 * </p>
 * *  text/plain（纯文本）
 * *  text/html（HTML文档）
 * *  application/xhtml+xml（XHTML文档）
 * *  image/gif（GIF图像）
 * *  image/jpeg（JPEG图像）【PHP中为：image/pjpeg】
 * *  image/png（PNG图像）【PHP中为：image/x-png】
 * *  video/mpeg（MPEG动画）
 * *  application/octet-stream（任意的二进制数据）
 * *  application/pdf（PDF文档）
 * *  application/msword（Microsoft Word文件）
 * *  message/rfc822（RFC 822形式）
 * *  multipart/alternative（HTML邮件的HTML形式和纯文本形式，相同内容使用不同形式表示）
 * *  application/x-www-form-urlencoded（使用HTTP的POST方法提交的表单）
 * *  multipart/form-data（同上，但主要用于表单提交时伴随文件上传的场合）
 *
 * @author Zsy
 * @date 2019/7/17 15:50
 */

public class DefaultProgramActivity extends Activity {

    TextView tvpath;
    TextView tvContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defalut_program);
        tvpath = findViewById(R.id.tv_file_path);
        tvContent = findViewById(R.id.tv_file_content);
        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {

            String dataString = intent.getDataString();

            Uri uri = intent.getData();
            String str = Uri.decode(uri.getEncodedPath());
            String filePath = getRealFilePath(this, uri);
            File file = new File(filePath);

            tvpath.setText("dataString== " + dataString + "\n" +
                    "uri==" + uri + "\n" +
                    "str== " + str + "\n" +
                    "file== " + file.length() + "\n" +
                    filePath);


        } else {
            tvpath.setText("null");
        }


    }


    public String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

}
