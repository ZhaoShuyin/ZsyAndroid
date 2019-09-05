package com.zsy.zlib.cipher;

import android.util.Base64;

import java.security.SecureRandom;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Created by Zsy on 2016/9/6.
 *
 * @description:
 */

public class DES {
    private static final String DES = "DES";   //AES 加密
    private static final String CIPHERMODE = "DES/CBC/PKCS5Padding";   //algorithm/mode/padding
    private static String CHAR_SET = "utf-8";//默认为utf-8,(gbk)
    private static HashMap<String, Cipher> cipherHashMap = new HashMap<>();

    /**
     * 使用 DES加密为16进制字符串格式
     *
     * @param key
     * @param clearText
     * @return
     */
    public static String encryptHex(String key, String clearText) {
        try {
            byte[] encrypt = encrypt(clearText.getBytes(CHAR_SET), key);
            return byte2hex(encrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用DES解密16进制字符串
     *
     * @param key
     * @param ciphertext
     * @return
     */
    public static String decryptHex(String key, String ciphertext) {
        try {
            byte[] bytes = hex2byte(ciphertext);
            byte[] decrypt = decrypt(bytes, key);
            return new String(decrypt, CHAR_SET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用DES加密为BASE64格式字符串
     *
     * @param key
     * @param clearText
     * @return
     */
    public static String encryptBase64(String key, String clearText) {
        try {
            byte[] encrypt = encrypt(clearText.getBytes(CHAR_SET), key);
            return Base64.encodeToString(encrypt, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用DES解密BASE64字符串
     *
     * @param key
     * @param ciphertext
     * @return
     */
    public static String decryptBase64(String key, String ciphertext) {
        try {
            byte[] decode = Base64.decode(ciphertext, Base64.NO_WRAP);
            byte[] decrypt = decrypt(decode, key);
            return new String(decrypt, CHAR_SET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 使用加密
     */
    private static byte[] encrypt(byte[] datasource, String key) throws Exception {
        Cipher cipher = getEncryptCipher(key);
        return cipher.doFinal(datasource); //按单部分操作加密或解密数据，或者结束一个多部分操作
    }

    /**
     * 使用解密
     */
    private static byte[] decrypt(byte[] src, String key) throws Exception {
        Cipher cipher = getDecryptCipher(key);
        return cipher.doFinal(src);
    }

    /**
     * 获取加密 Cipher
     * @param key
     * @return
     * @throws Exception
     */
    private static Cipher getEncryptCipher(String key) throws Exception {
        String hKey = key + "-e";
        if (cipherHashMap.containsKey(hKey)) {
            return cipherHashMap.get(hKey);
        } else {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);//DES/CBC/pkcs5padding
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance(DES);
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            cipherHashMap.put(hKey, cipher);
            return cipher;
        }
    }

    /**
     * 获取解密 Cipher
     * @param key
     * @return
     * @throws Exception
     */
    private static Cipher getDecryptCipher(String key) throws Exception {
        String hKey = key + "-d";
        if (cipherHashMap.containsKey(hKey)) {
            return cipherHashMap.get(hKey);
        } else {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);//DES/CBC/pkcs5padding
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance(DES);
            cipher.init(Cipher.DECRYPT_MODE, securekey, random);
            cipherHashMap.put(hKey, cipher);
            return cipher;
        }
    }


    /**
     * 将二进制数组转换为16进制字符串
     */
    private static String byte2hex(byte[] b) {
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
    private static byte[] hex2byte(String strhex) {
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
