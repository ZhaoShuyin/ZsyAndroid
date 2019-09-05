package cn.azsy.zstokhttp.utils.zsyutils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zsy on 2017/9/5.
 */

public class ZMD5 {
    // 使用单个字节加密字符串
    public static String xorEncrypt(char key, String value) {
        char[] a = value.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ key);
        }
        String s = new String(a);
        return s;
    }

    //使用单个字节解密字符串
    public static String xorDecode(char key, String value) {
        char[] a = value.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ key);
        }
        String s = new String(a);
        return s;
    }

    //测试
    private static String MD5Get32(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));//把数字转化为16进制的字符串形式
            }
            result = buf.toString();
//            System.out.println("MD5(" + sourceStr + ",32) = " + result);
//            System.out.println("MD5(" + sourceStr + ",16) = " + buf.toString().substring(8, 24));
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }


    //字符串转16进制数字,获取32位16进制字符串
    public static String md5Get16Num(String s) {
        StringBuilder res = null;
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes());
            byte[] hash = m.digest();
            res = new StringBuilder();
            for (byte b : hash) {
                System.out.print(" "+b);
                if ((b & 0xFF) < 0xF) {//小于16只有一位就在前面补0
                    res.append("0");
                }
                res.append(Integer.toHexString(b & 0xFF).toUpperCase());
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return res.toString();
    }

}
