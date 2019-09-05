package cn.azsy.zstokhttp.zsyokhttp.zok.diskcache;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by zsy on 2017/6/5.
 */

public class DiskCacheHelper {
    public static int helperVersion = 0;//应用的版本号,在版本号发生更改时数据也会发生更新
    static int cacheSize = 5 * 1024 * 1024;//缓存文件的大小限制
    public static int valueCount = 1;//每个key对应几个value值

    static DiskLruCache diskLruCache;//缓存操作对象

    /**
     *获取到的字符串信息以流的形式写入缓存文件
     * 1当已有该key对应文件存在
     * 2app版本未做更改
     * 这两种情况下不会再次重复写入
     */
    public static void writeJson(Context context, String folderName, String key, String value) {
        JudgeObject(context, folderName);
        if (diskLruCache.lruEntries.containsKey(key)&&diskLruCache.appVersion==helperVersion) {
//            Toast.makeText(context, "缓存文件已存在,不用再次写入", Toast.LENGTH_SHORT).show();
        } else {/*Toast.makeText(context, "开始写入了!!! diskLruCache.appVersion=="+diskLruCache.appVersion+
                        "helperVersion=="+helperVersion, Toast.LENGTH_SHORT).show();*/
                OutputStream stream = null;
                try {
                    DiskLruCache.Editor editor = diskLruCache.edit(key);
                    stream = editor.newOutputStream(0);
                    stream.write(value.getBytes());
                    editor.commit();
                    diskLruCache.flush();//完成写入刷新,避免对同一文件重复覆盖写入
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (stream != null) {
                        try {
                            stream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        }
    }

    /**
     * 读取缓存文件中的字符串信息,没有缓存是返回默认值
     */
    public static String readJson(Context context, String folderName, String key, String defValue) {
        JudgeObject(context, folderName);
        String result = null;
        if (diskLruCache.lruEntries.containsKey(key)) {
            InputStream inputStream = null;
            try {
                DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
                inputStream = snapshot.getInputStream(0);
                result = new String(input2byte(inputStream));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            result = defValue;
        }
        return result;
    }

    /**
     * InputStream通过ByteArrayOutputStream转换为字节数组
     */
    private static final byte[] input2byte(InputStream inStream)throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }

    /**
     * 判断初始化helperVersion,判断初始化diskLruCache,
     */
    private static void JudgeObject(Context context, String folderName) {
        if (helperVersion == 0) {//如果本类变量没有被初始化就获取应用版本号
            try {
                helperVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            Log.d("shshishi", "初始化--helperVersion-- == "+helperVersion);
        }
        if (diskLruCache == null) {
            Log.d("shshishi", "创建--diskLruCache--缓存对象");
            try { //当传入的appVersion不等于存储的值时会清空该文件夹所有数据文件,然后重新开始写入
                diskLruCache = DiskLruCache.open(getDiskCacheDir(context, folderName), helperVersion, valueCount, cacheSize);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取本类应用缓存地址目录
     */
    private static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        //如果sd卡存在并且没有被移除
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();//获取Android/data/包名/cache路径
        } else {
            cachePath = context.getCacheDir().getPath();//获取data/data/包名/cache路径
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 判断缓存文件是否存在
     */
    public static boolean queryFile(Context context, String folderName, String key, String position) {
        File file = getDiskCacheDir(context, folderName + File.separator + key + "." + position);
        return file.exists();
    }

    /**
     * 判断某key是否有缓存
     */
    public static boolean isKeyHasSave(Context context, String folderName, String key){
        JudgeObject(context, folderName);
        Log.d("shshishi","判断缓存containsKey(key) == "+diskLruCache.lruEntries.containsKey(key)+"" +
                "版本一致 == "+(helperVersion==diskLruCache.appVersion)+"/n"+
                "helperVersion == "+helperVersion +" , diskLruCache.appVersion =="+diskLruCache.appVersion
             );
        return diskLruCache.lruEntries.containsKey(key)&&helperVersion==diskLruCache.appVersion;
    }

    /**
     * 判断某key是否写入过
     */
    public static boolean isKeyHasWrite(Context context, String folderName, String key){
        JudgeObject(context, folderName);
        Log.d("shshishi","判断缓存containsKey(key) == "+diskLruCache.lruEntries.containsKey(key)+"" +
                "版本一致 == "+(helperVersion==diskLruCache.appVersion)+"/n"+
                "helperVersion == "+helperVersion +" , diskLruCache.appVersion =="+diskLruCache.appVersion
        );
        return diskLruCache.lruEntries.containsKey(key)&&helperVersion==diskLruCache.appVersion;
    }
}
