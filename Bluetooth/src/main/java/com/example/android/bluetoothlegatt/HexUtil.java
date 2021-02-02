package com.example.android.bluetoothlegatt;

/**
 * @Title com.example.app4
 * @date 2020/12/10
 * @autor Zsy
 */

public class HexUtil {
    public static String encodeHexStr(byte[] value) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < value.length; i++) {
            String str = Integer.toHexString(value[i]);
            builder.append(str.length() == 1 ? " 0" + str : " " + str);
        }
        return builder.toString();
    }
}
