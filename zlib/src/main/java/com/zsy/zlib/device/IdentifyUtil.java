package com.zsy.zlib.device;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zsy on 2018/2/28.
 */

public class IdentifyUtil {


    public static String getIdentify(Context context) {
        SharedPreferences sp = context.getSharedPreferences("identify", Context.MODE_PRIVATE);
        String identify = sp.getString("identify", "0");
        if (identify.equals("0")) {
            String imei = getImei(context);
            String id = getId(context);
            String address = getMAC(context);
            identify = getMD5(imei + id + address);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("identify", identify);
            editor.commit();
        }
        return identify;
    }

    private static String getMAC(Context context) {
        String macAddress = "0";
        try {
            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            macAddress = wm.getConnectionInfo().getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return macAddress;
    }

    //获取AndroidID,在8.0(26)之后各App获取值不同
    private static String getId(Context context) {
        String AndroidId = "0";
        try {
            AndroidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AndroidId;
    }

    private static String getImei(Context context) {
        String imei = "0";
        try {
            TelephonyManager mTm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            imei = mTm.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imei;
    }


    private static String getMD5(String string) {
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(string.getBytes(), 0, string.length());
        byte p_md5Data[] = m.digest();
        String m_szUniqueID = new String();
        for (int i = 0; i < p_md5Data.length; i++) {
            int b = (0xFF & p_md5Data[i]);
            if (b <= 0xF) {
                m_szUniqueID += "0";
            }
            m_szUniqueID += Integer.toHexString(b);
            m_szUniqueID = m_szUniqueID.toUpperCase();
        }
        return m_szUniqueID;
    }

}
