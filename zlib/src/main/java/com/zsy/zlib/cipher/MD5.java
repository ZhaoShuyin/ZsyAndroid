package com.zsy.zlib.cipher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zsy on 2017/5/24.
 */
public class MD5 {

    public static MessageDigest md;

    public static String MD5(String s) {
        StringBuilder res = null;
        try {
            if (md == null) {
                md = MessageDigest.getInstance("MD5");
            }
            md.update(s.getBytes());
            byte[] bytes = md.digest();
            res = new StringBuilder();
            //(b & 0xFF) 对 <0 的数据进行+=256处理  >0 则还是元数据
            // 小于15只有一位就在前面补0
            for (byte b : bytes) {
                if ((b & 0xFF) < 0xF) {
                    res.append("0");
                }
                res.append(Integer.toHexString(b & 0xFF));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res.toString();
    }

    //new StringBuilder(bytes.length * 2);
    // s.getBytes("utf-8")
    private static String md5(String s) {
        StringBuilder ret = null;
        try {
            if (md == null) {
                md = MessageDigest.getInstance("MD5");
            }
            ret = new StringBuilder();
            byte[] bytes = md.digest(s.getBytes());
            char[] chars = "0123456789ABCDEF".toCharArray();
            //
            // 判断小于15的右移置0(删除后4位二进制数字)
            // &0x0f 为置换为小于等于16的数字
            for (int i = 0; i < bytes.length; i++) {
                ret.append(chars[(bytes[i] >> 4) & 0x0f]);
                ret.append(chars[bytes[i] & 0x0f]);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ret.toString();
    }

    /**
     * 使用异或进行加解密字符串
     *
     * @param key   字符
     * @param value String
     * @return 加解密后的 String
     */
    public static String encryption(char key, String value) {
        char[] a = value.toCharArray();
        for (int i = 0; i < a.length; i++) {
            System.out.println(" <" + i + "> " + a[i]);
            a[i] = (char) (a[i] ^ key);
            System.out.println(" <" + i + "> " + a[i]);
        }
        return new String(a);
    }

}
