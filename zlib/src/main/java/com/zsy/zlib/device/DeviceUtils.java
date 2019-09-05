package com.zsy.zlib.device;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
//import android.support.compat.BuildConfig;
import com.zsy.zlib.BuildConfig;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.util.UUID;

/**
 * 获取手机设备的唯一识别码
 */

public class DeviceUtils {

    /**
     * </span></p>  * deviceID的组成为：渠道标志+识别符来源标志+hash后的终端识别符
     * <p>
     * 渠道标志为：
     * 1，andriod（a）
     * <p>
     * 识别符来源标志：
     * 1， wifi mac地址（wifi）；
     * 2， IMEI（imei）；
     * 3， 序列号（sn）；
     * 4， id：随机码。若前面的都取不到时，则随机生成一个随机码，需要缓存。
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        StringBuilder deviceId = new StringBuilder();
        // 渠道标志
        deviceId.append("a");
        try {
            //wifi mac地址
            /*  需要权限<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />*/
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            @SuppressLint("MissingPermission") WifiInfo info = wifi.getConnectionInfo();
            String wifiMac = info.getMacAddress();
            if (!TextUtils.isEmpty(wifiMac)) {
                deviceId.append("wifi");
                deviceId.append(wifiMac);
                if (BuildConfig.DEBUG) {
                    Log.e("getDeviceId : ", deviceId.toString());
                }
                return deviceId.toString();
            }
            //IMEI（imei）
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            @SuppressLint("MissingPermission") String imei = tm.getDeviceId();
            if (!TextUtils.isEmpty(imei)) {
                deviceId.append("imei");
                deviceId.append(imei);
                if (BuildConfig.DEBUG) {
                    Log.e("getDeviceId : ", deviceId.toString());
                }
                return deviceId.toString();
            }
            //序列号（sn）
            @SuppressLint("MissingPermission") String sn = tm.getSimSerialNumber();
            if (!TextUtils.isEmpty(sn)) {
                deviceId.append("sn");
                deviceId.append(sn);
                if (BuildConfig.DEBUG) {
                    Log.e("getDeviceId : ", deviceId.toString());
                }
                return deviceId.toString();
            }
            //如果上面都没有， 则生成一个id：随机码
            String uuid = getUUID(context);
            if (!TextUtils.isEmpty(uuid)) {
                deviceId.append("id");
                deviceId.append(uuid);
                if (BuildConfig.DEBUG) {
                    Log.e("getDeviceId : ", deviceId.toString());
                }
                return deviceId.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            deviceId.append("id").append(UUID.randomUUID().toString());
        }
        if (BuildConfig.DEBUG) {
            Log.e("getDeviceId : ", deviceId.toString());
        }
        return deviceId.toString();
    }

    /**
     * 得到全局唯一UUID
     * 使用Java,通过1日期,2时钟序列,3网卡MAC等生成唯一识别码
     */
    public static String getUUID(Context context) {
        String uuid = null;
        SharedPreferences mShare = PreferenceManager.getDefaultSharedPreferences(context);//, "sysCacheMap");
        if (mShare != null) {
            uuid = mShare.getString("uuid", "");
        }
        if (TextUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();//使用Java,通过1日期,2时钟序列,3网卡MAC等生成唯一识别码
            if (mShare != null) {
                mShare.edit().putString("uuid", uuid).commit();
            }
        }
        return uuid;
    }

    /**
     * 获取设备唯一标识 本方法调用需要READ_PHONE_STATE权限
     * 使用Java,通过1日期,2时钟序列,3网卡MAC等生成唯一识别码
     */
    @SuppressLint("MissingPermission")
    public static String getUUID2(Context context) {
        String tmDevice = "", tmSerial = "", tmPhone = "", androidId = "";
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {//检查读取手机信息权限
            try {
                final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

                tmDevice = "" + tm.getDeviceId();//IMEI
                tmSerial = "" + tm.getSimSerialNumber();//SIM卡唯一识别信息
                androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            } catch (Exception e) {
                Log.e("AppUtils", "exception:" + e.getMessage());
            }
        } else {
            Log.e("AppUtils", "没有 android.permission.READ_PHONE_STATE 权限");
            tmDevice = "device";
            tmSerial = "serial";
            androidId = "androidid";
        }


        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        if (BuildConfig.DEBUG) {
            Log.d("DeviceUtils", "getUUID2 : " + uniqueId);
        }

        return uniqueId;
    }


}
