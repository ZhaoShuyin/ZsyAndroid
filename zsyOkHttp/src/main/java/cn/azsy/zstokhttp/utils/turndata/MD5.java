package cn.azsy.zstokhttp.utils.turndata;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zsy on 2017/5/24.
 */

public class MD5 {
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

    //字符串转16进制数字,获取32位16进制字符串
    public static String md5TurnHexadecimal(String s,boolean up) {
        StringBuilder res = null;
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes());
            byte[] hash = m.digest();
            res = new StringBuilder();
            for (byte b : hash) {
//                System.out.print(" "+b);
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


    //测试
    private static String MD52(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
//            System.out.println("byte b[] == "+b);
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
//                System.out.println("i == "+i);
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
//            System.out.println("buf == "+buf);
//            System.out.println("result == " + result);
//            System.out.println("MD5(" + sourceStr + ",16) = " + buf.toString().substring(8, 24));
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }


    public static void main(String args[]){
        String dingdan = "merchantOutOrderNo=1709569953&merid=201708290002&noncestr=1234&notifyUrl=http://101.200.146.62:81/apicore/pay/receive_asyn_notify2&orderMoney=1.00&orderTime=2017090115012";
        String diangdanghao = "http://101.200.146.62:81/apicore/pay/pay_unit_api2?uid=391&paytype=3&money_sum=150.0&tag=7516";

       String A="merchantOutOrderNo=201708020001&merid=100001&noncestr=test&notifyUrl=http://jh.yizhibank.com/api/callback&orderMoney=1.00&orderTime=20170802132205";

        String stringsignTemp=A+"&key=wuhLhyqW4kB4Q4yOrwH80HuVnXNSehOr";

        String abc = "merchantOutOrderNo=201708020001&merid=100001&noncestr=test&notifyUrl=http://jh.yizhibank.com/api/callback&orderMoney=1.00&orderTime=20170802132205&key=wuhLhyqW4kB4Q4yOrwH80HuVnXNSehOr";

//        String sunzn = MD52(abc);

//        System.out.println("sunzn=="+sunzn);
//        String s = "这是字符串";
        System.out.println("获取16进制MD5== "+md5TurnHexadecimal("dasdasdas",false));
        System.out.println("获取16进制MD5== "+MD52("dasdasdas"));
//        System.out.println("异或加密== "+ xorEncrypt('k',s));
//        System.out.println("异或解密== "+ xorDecode('k', xorEncrypt('k',s)));
//        System.out.println(""+(0xFF&0xFF));
//        System.out.println(""+(0xFF));
    }

}
