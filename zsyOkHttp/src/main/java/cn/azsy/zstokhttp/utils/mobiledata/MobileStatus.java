package cn.azsy.zstokhttp.utils.mobiledata;

import android.app.ActivityManager;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by zsy on 2017/5/25.
 */

public class MobileStatus {

    /**
     * 判断网络是否连接
     */
    public static boolean judgeNetConnect(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo!=null){
            return networkInfo.getState()==NetworkInfo.State.CONNECTED;
        }
        return false;
    }

    /**
     * 判断网络是否可用
     */
     public static boolean judgeNetAble(Context context){
         ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo networkInfo = cm.getActiveNetworkInfo();
         if (networkInfo!=null){
             return networkInfo.isAvailable();
         }
         return false;
     }

    /**
     *判断是否是GPRS网络
     */
    public static boolean judge3G(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo !=null&&networkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
            return true;
        }
        return false;
    }

    /**
     *判断网络状态是不是WIFI
     */
    public static boolean judgeWifi(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo!=null&&networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
            return true;
        }
        return false;
    }

    /**
     *判断GPS是否打开
     */
    public static boolean judgeGPS(Context context){
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);
        return (providers!=null&&providers.size()>0);
    }

    /**
     *获取RAM内存可用大小
     */
    public static long getMemoryLong(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(info);
        long avaiMem = info.availMem;
        return avaiMem;
    }

    /**
     *获取RAM内存可用大小
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
     * 获取标准格式的系统时间
     */
    public static String getSystemTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     *获取手机号码
     */
    public static String getPhoneNumber(Context context) {
        String line1Number = "133";
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String subscriberId = tm.getSubscriberId();
        if (subscriberId==null||subscriberId.length()<=0){
            line1Number = "没有SIM卡";
        }else{
            line1Number = tm.getLine1Number();
        }
        return line1Number;
    }

    //获取手机SIM卡信息
    //<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
    public static String getSimSerNumber(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String simSerialNumber = tm.getSimSerialNumber();
        return simSerialNumber;
    }
}
