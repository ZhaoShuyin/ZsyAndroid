/**
 * Copyright 2014 Zhenguo Jin
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zsy.zlib.phone;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;

/**
 * 手机组件调用工具类
 * 对于7.0文件管理
 * 方法一://取消严格模式  FileProvider
 * if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
 * StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
 * StrictMode.setVmPolicy( builder.build() );
 * }
 * 方法二:
 * A:清单文件配置
 * <provider
 * .  android:name="android.support.v4.content.FileProvider"
 * .  android:authorities="com.zsy.android.provider"
 * .  android:exported="false"
 * .  android:grantUriPermissions="true">
 * . <meta-data
 * .   android:name="android.support.FILE_PROVIDER_PATHS"
 * .   android:resource="@xml/provider_paths"/>
 * </provider>
 * B:res路径配置path文件
 * <paths xmlns:android="http://schemas.android.com/apk/res/android">
 * .<!--未设置的默认路径 content://com.zsy.android.provider/external_storage_root/Android/data/com.zsy.android/cache/111.jpg-->
 * .<!--设置后的使用路径 content://com.zsy.android.provider/files_root/cache/111.jpg-->
 * .  <external-path name="files_root" path="Android/data/com.zsy.android/" />
 * .  <external-path name="external_storage_root" path="." />
 * </paths>
 * C:使用
 * .    FileProvider.getUriForFile(context, paackage, file) 获取Uri
 * D:Intent
 * . intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
 * . intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
 *
 * @author zsy
 */
public final class PhoneUtil {

    /**
     * Don't let anyone instantiate this class.
     */
    private PhoneUtil() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * 调用系统发短信界面
     */
    public static void sendMessage(Context activity, String phoneNumber, String smsContent) {
        if (phoneNumber == null || phoneNumber.length() < 4) {
            return;
        }
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", smsContent);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(it);
    }

    /**
     * 调用系统打电话界面
     * 需要权限<uses-permission android:name="android.permission.CALL_PHONE" />
     */
    @SuppressLint("MissingPermission")
    @TargetApi(Build.VERSION_CODES.M)
    public static void callPhones(Context context, String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() < 1) {
            return;
        }
        Uri uri = Uri.parse("tel:" + phoneNumber);
//        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (context.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        context.startActivity(intent);
    }

    /**
     * 调用系统相机界面
     */
    @SuppressLint("NewApi")
    public static void callCamera(Activity activity, File file, int camera_res_code, boolean isProvider) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        //在7.0以后切没有配置provider的取消严格模式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N & !isProvider) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriFromFile(activity, file, isProvider));
        activity.startActivityForResult(intent, camera_res_code);
    }


    /**
     * 调用系统相册选择图片
     */
    public static void callGallery(Activity activity, int gallery_res_code) {
        // 激活系统图库，选择一张图片
        /*Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image*//*");
        activity.startActivityForResult(intent, 102);*/
        Intent intent;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        }
        activity.startActivityForResult(intent, gallery_res_code);
    }


    /**
     * 调用系统剪切图片
     */
    public static void callCrop(Activity activity, Uri uri, int crop_res_code) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        activity.startActivityForResult(intent, crop_res_code);
    }


    /**
     * 调用系统日历选择日期
     */
    public static void callCalendar(final Context context) {
        final Calendar cal = Calendar.getInstance();//获取日历对象
        final DatePickerDialog dialog = new DatePickerDialog(context, null, 1990, 0, 1);
        //手动设置按钮
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "完成", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dial, int which) {
                DatePicker datePicker = dialog.getDatePicker();
                cal.set(Calendar.YEAR, datePicker.getYear());
                cal.set(Calendar.MONTH, datePicker.getMonth());
                cal.set(Calendar.DATE, datePicker.getDayOfMonth());
                long timeInMillisend = cal.getTimeInMillis();
                Toast.makeText(context, "选中的日期 = " + timeInMillisend, Toast.LENGTH_SHORT).show();
            }
        });
        //取消按钮，如果不需要直接不设置即可
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    /**
     * 调用系统安装apk
     */
    public static void installApk(Context context, File file, boolean isProvider) {
        Intent intent = new Intent();
        //在7.0以后切没有配置provider的取消严格模式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N & !isProvider) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uriFromFile = getUriFromFile(context, file, isProvider);
        intent.setDataAndType(uriFromFile, "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent);
    }

    /**
     * 安装apk
     * https://blog.csdn.net/y505772146/article/details/55255344/
     */
    public static void installApk(Context context, Uri uri) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }


    /**
     * 卸载apk
     */
    public static void uninstallApk(Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        Uri packageURI = Uri.parse("package:" + packageName);
        intent.setData(packageURI);
        context.startActivity(intent);
    }

    /**
     *
     */
    private static Uri getUriFromFile(Context context, File file, boolean isProvider) {
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N & isProvider) {
            String name = "com.zsy.zlib" + ".provider";
            Context applicationContext = context.getApplicationContext();
            uri = FileProvider.getUriForFile(applicationContext, name, file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

}
