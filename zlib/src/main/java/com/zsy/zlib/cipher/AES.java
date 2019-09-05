package com.zsy.zlib.cipher;

import android.text.TextUtils;
import android.util.Base64;

import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Zsy on 2016/9/6.
 *
 * @description:
 */

public class AES {
    private static final String AES = "AES";   //AES 加密
    private static final String CIPHERMODE = "AES/CBC/PKCS5Padding";   //algorithm/mode/padding
    private static String CHAR_SET = "utf-8";//默认为utf-8,(gbk)
    private static HashMap<String, Cipher> cipherHashMap = new HashMap<>();

    /**
     * 使用 AES加密为16进制字符串
     *
     * @param key
     * @param cleartext
     * @return
     */
    public static String encryptHex(String key, String cleartext) {
        if (TextUtils.isEmpty(cleartext)) {
            return cleartext;
        }
        try {
            byte[] result = encrypt(key, cleartext.getBytes(CHAR_SET));
            return byte2hex(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用AES加密为BASE64格式字符串
     *
     * @param key
     * @param cleartext
     * @return
     */
    public static String encryptBase64(String key, String cleartext) {
        if (TextUtils.isEmpty(cleartext)) {
            return cleartext;
        }
        try {
            byte[] result = encrypt(key, cleartext.getBytes());
            return Base64.encodeToString(result, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 使用AES解密16进制格式字符串
     *
     * @param key
     * @param encrypted
     * @return
     */
    public static String decryptHex(String key, String encrypted) {
        if (TextUtils.isEmpty(encrypted)) {
            return encrypted;
        }
        try {
            byte[] enc = hex2byte(encrypted);
            byte[] result = decrypt(key, enc);
            return new String(result, CHAR_SET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用AES解密BASE64格式字符串
     *
     * @param key
     * @param encrypted
     * @return
     */
    public static String decryptBase64(String key, String encrypted) {
        if (TextUtils.isEmpty(encrypted)) {
            return encrypted;
        }
        try {
            byte[] enc = Base64.decode(encrypted, Base64.NO_WRAP);
            byte[] result = decrypt(key, enc);
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 加密使用
     */
    private static byte[] encrypt(String key, byte[] clear) throws Exception {
        String hKey = key + "-e";
        if (cipherHashMap.containsKey(hKey)) {
            return cipherHashMap.get(hKey).doFinal(clear);
        } else {
            byte[] raw = key.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
            Cipher cipher = Cipher.getInstance(CIPHERMODE);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            cipherHashMap.put(hKey, cipher);
            return cipher.doFinal(clear);
        }
    }

    /**
     * 解密使用
     */
    private static byte[] decrypt(String key, byte[] clear) throws Exception {
        String hKey = key + "-d";
        if (cipherHashMap.containsKey(hKey)) {
            return cipherHashMap.get(hKey).doFinal(clear);
        } else {
            byte[] raw = key.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
            Cipher cipher = Cipher.getInstance(CIPHERMODE);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            cipherHashMap.put(hKey, cipher);
            return cipher.doFinal(clear);
        }
    }


    /**
     * 将二进制数组转换为16进制字符串
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    /**
     * 将16进制字符串转换为二进制数组
     */
    public static byte[] hex2byte(String strhex) {
        if (strhex == null) {
            return null;
        }
        int l = strhex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),
                    16);
        }
        return b;
    }
}
