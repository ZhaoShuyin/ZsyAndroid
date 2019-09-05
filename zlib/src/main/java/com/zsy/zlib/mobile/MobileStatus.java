package com.zsy.zlib.mobile;

import android.app.ActivityManager;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

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

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     * @param context
     * @return true 表示开启
     */
    public static boolean gpsIsOpen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }

}
