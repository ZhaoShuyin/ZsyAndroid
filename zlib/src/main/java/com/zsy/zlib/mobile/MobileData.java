package com.zsy.zlib.mobile;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.text.format.Formatter;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * String state = Environment.getExternalStorageState()
 * state.equals(Environment.MEDIA_MOUNTED)
 * 如果状态是Environment.MEDIA_MOUNTED 说明sd卡可读可写 其他都是不能用的
 *
 * Created by zsy on 2017/5/25.
 */

public class MobileData {

    /**
     * 获取dp,密度比算出具体px像素值
     */
    public static int getDpPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取sp,密度比算出sp像素值
     */
    public static int getSpPx(Context context, float spValve) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValve * fontScale + 0.5f);
    }

    /**
     * 获取系统版本信息
     */
    public static int getAndroidVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 获取RAM内存可用大小
     */
    public static String getMemoryString(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(info);
        long avaiMem = info.availMem;
        String avaiMemory = Formatter.formatFileSize(context, avaiMem);
        return avaiMemory;
    }

    ///获取SD卡剩余大小-->long
    public static long getSDUseableSizeLong(Context context) {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        File file = new File(externalStorageDirectory.getAbsolutePath());
        long freeSpace = file.getFreeSpace();
        return freeSpace;
    }

    ///获取SD卡剩余大小 -->String
    public static String getSDUseableSizeString(Context context) {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        File file = new File(externalStorageDirectory.getAbsolutePath());
        long freeSpace = file.getFreeSpace();
        String size = Formatter.formatFileSize(context, freeSpace);
        return size;
    }

    //获取SD卡全部大小 -->long
    public static long getSDTotalSizeLong(Context context) {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        File file = new File(externalStorageDirectory.getAbsolutePath());
        long totalSpace = file.getTotalSpace();
        return totalSpace;
    }

    //获取SD卡全部大小 -->String
    public static String getSDTotalSizeString(Context context) {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        File file = new File(externalStorageDirectory.getAbsolutePath());
        long totalSpace = file.getTotalSpace();
        String size = Formatter.formatFileSize(context, totalSpace);
        return size;
    }

    /**
     * 获取单个APP运行内存限制大小
     */
    public static long getSimpleMemoryData() {
        long l = Runtime.getRuntime().maxMemory();
        return l;
    }

    /**
     * 是否Dalvik模式
     */
    public static boolean isDalvik() {
        return "Dalvik".equals(getCurrentRuntimeValue());
    }

    /**
     * 是否ART模式
     */
    public static boolean isART() {
        String currentRuntime = getCurrentRuntimeValue();
        return "ART".equals(currentRuntime) || "ART debug build".equals(currentRuntime);
    }

    /**
     * 获取手机当前的Runtime
     *
     * @return 正常情况下可能取值Dalvik, ART, ART debug build;
     */
    public static String getCurrentRuntimeValue() {
        try {
            Class<?> systemProperties = Class.forName("android.os.SystemProperties");
            try {
                Method get = systemProperties.getMethod("get",
                        String.class, String.class);
                if (get == null) {
                    return "WTF?!";
                }
                try {
                    final String value = (String) get.invoke(
                            systemProperties, "persist.sys.dalvik.vm.lib",
                        /* Assuming default is */"Dalvik");
                    if ("libdvm.so".equals(value)) {
                        return "Dalvik";
                    } else if ("libart.so".equals(value)) {
                        return "ART";
                    } else if ("libartd.so".equals(value)) {
                        return "ART debug build";
                    }

                    return value;
                } catch (IllegalAccessException e) {
                    return "IllegalAccessException";
                } catch (IllegalArgumentException e) {
                    return "IllegalArgumentException";
                } catch (InvocationTargetException e) {
                    return "InvocationTargetException";
                }
            } catch (NoSuchMethodException e) {
                return "SystemProperties.get(String key, String def) method is not found";
            }
        } catch (ClassNotFoundException e) {
            return "SystemProperties class is not found";
        }
    }
}
