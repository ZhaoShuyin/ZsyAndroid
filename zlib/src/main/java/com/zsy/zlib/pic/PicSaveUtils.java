package com.zsy.zlib.pic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by zsy on 16/3/9.
 * <p/>
 * 图片相关的工具类(保存图片到SD卡,需要写SD卡权限)
 */
public class PicSaveUtils {

    /**
     * @param bitmap  保存的Bitmap源数据
     * @param dir     保存的地址
     * @param name    保存的文件名称
     * @param context 传入上下文,通知图库更新,可以为空
     * @return 返回保存结果
     */
    public static boolean saveBitmapToSD(Bitmap bitmap, String dir, String name, Context context) {
        File path = new File(dir);
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(path + "/" + name);
        if (file.exists()) {
            file.delete();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return true;
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100,
                    fileOutputStream);
            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 其次把文件插入到系统图库
        if (context != null) {
            try {
                MediaStore.Images.Media.insertImage(context.getContentResolver(),
                        file.getAbsolutePath(), name, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 最后通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file)));
        }

        return true;
    }

    public static boolean saveResToSD(Context context, int resID, String dir, String name) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resID);
        return saveBitmapToSD(bitmap, dir, name, null);
    }

}
