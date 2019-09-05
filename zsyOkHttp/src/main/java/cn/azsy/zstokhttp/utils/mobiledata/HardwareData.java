package cn.azsy.zstokhttp.utils.mobiledata;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * Created by zsy on 2017/5/24.
 */

public class HardwareData {

    /**
     *通过各种硬件信息获取唯一标识号
     */
    public static String getHardwareData(){
        StringBuffer sb =new StringBuffer();
        sb.append("主板："+Build.BOARD);
        sb.append("\n系统启动程序版本号："+ Build.BOOTLOADER);
        sb.append("\n系统定制商："+Build.BRAND);
        sb.append("\ncpu指令集："+Build.CPU_ABI);
        sb.append("\ncpu指令集2："+Build.CPU_ABI2);
        sb.append("\n设置参数："+Build.DEVICE);
        sb.append("\n显示屏参数："+Build.DISPLAY);
        sb.append("\n无线电固件版本："+Build.getRadioVersion());
        sb.append("\n硬件识别码："+Build.FINGERPRINT);
        sb.append("\n硬件名称："+Build.HARDWARE);
        sb.append("\nHOST:"+Build.HOST);
        sb.append("\n修订版本列表："+Build.ID);
        sb.append("\n硬件制造商："+Build.MANUFACTURER);
        sb.append("\n版本："+Build.MODEL);
        sb.append("\n硬件序列号："+Build.SERIAL);
        sb.append("\n手机制造商："+Build.PRODUCT);
        sb.append("\n描述Build的标签："+Build.TAGS);
        sb.append("\nTIME:"+Build.TIME);
        sb.append("\nbuilder类型："+Build.TYPE);
        sb.append("\nUSER:"+Build.USER);
        return sb.toString();
    }

    /**
     * 获取BlueTooth地址
     * 需要权限
     * <uses-permission android:name="android.permission.BLUETOOTH" />
     */
    public static String getBlueToothAddress(Context context){
        BluetoothAdapter adapter = null; // Local Bluetooth adapter
        adapter = BluetoothAdapter.getDefaultAdapter();
        String address = adapter.getAddress();
        return address;
    }

    /**
     * 获取ANdroidId
     */
    public static String getAndroidId(Context context){
        String aId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return aId;
    }

    /**
     * 获取手机IMEI号
     */
    public static String getIMEI(Context context){
        String imei = "0";
        TelephonyManager mTm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        imei = mTm.getDeviceId();
        return imei;
    }


    /**
     * 获取手机MAC地址
     * 需要权限
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     */
    public static String getMacAdress(Context context){
        String mac = null;
        WifiManager wm = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        mac = wm.getConnectionInfo().getMacAddress();
        return mac;
    }

    //获取手机SIM卡信息
    //<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
    public static String getSimSerNumber(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String simSerialNumber = tm.getSimSerialNumber();
        return simSerialNumber;
    }
}
