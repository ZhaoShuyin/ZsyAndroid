package com.zsy.zlib.cipher;

import android.util.Base64;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;


/**
 * Created by zsy on 2018/4/24.
 */

public class RSA {

    private static final String ALGORITHM = "RSA";
    private static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";
    private static String CHAR_SET = "utf-8";//默认为utf-8,(gbk)
    private static RSAPublicKey publicKey;
    private static RSAPrivateKey privateKey;

    public static final String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDysax0m0xdggnWkFwiltf4N69w0Srnm8p11WNApmVbvIRvV/6sknZPHeJRZXXI2XcJzCDGXxF8qub+yQ/r9dLiwNyW8Yk9+AwxOArpNe3J2v8BAiaiXbf59LqjkS3UyK5UPTJcmM5vFimPtUPCo8qt2d3qNkCkm7+vsoh5bXtBCwIDAQAB";

    public static final String privateKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCRSw2hn9S0V5JdXebb6IDdMErznl/tC4L71JAT1mPIQZiEpQfLiXvnMbryn2OtINOHZS+OuAbBMo1fGaBHNHSlJm/Xxtrn/IOilq81g/rGNO5uogGrPpSZNaqf/JoyZt6ks5OcEE6VhQtkNq/O4pflu6GqZR/AaCVuyfYxM6u1XwIDAQA";
    
    /**
     * 从字符串中加载公钥
     */
    private static RSAPublicKey loadPublicKey(String publicKeyStr) throws Exception {
        try {
            byte[] buffer = Base64.decode(publicKeyStr, Base64.DEFAULT);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("RSA 获取公钥无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("RSA 获取公钥公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("RSA 获取公钥公钥数据为空");
        }
    }

    /**
     * 从字符串中加载私钥
     */
    private static RSAPrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
        try {
            byte[] buffer = Base64.decode(privateKeyStr, Base64.DEFAULT);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * 公钥加密
     */
    public static String encryptByPublicKey(String publicKeyStr,String cleartext) {
        try {
            if (publicKey == null)
                publicKey = loadPublicKey(publicKeyStr);
            byte[] bytes = encryptByPublicKey(cleartext.getBytes(CHAR_SET), publicKey);
            String s = Base64.encodeToString(bytes, Base64.NO_WRAP);
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 公钥解密
     */
    public static String decryptByPublicKey(String publicKeyStr,String ciphertext) {
        try {
            if (publicKey == null)
                publicKey = loadPublicKey(publicKeyStr);
            byte[] decode = Base64.decode(ciphertext, Base64.NO_WRAP);
            byte[] bytes = decryptByPublicKey(decode, publicKey);
            String s = new String(bytes,CHAR_SET);
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 私钥加密
     */
    public static String encryptByPrivateKey(String privateKeyStr,String cleartext){
        try {
            if (privateKey == null)
                privateKey = loadPrivateKey(privateKeyStr);
            byte[] bytes = encryptByPrivateKey(cleartext.getBytes(CHAR_SET), privateKey);
            String s = Base64.encodeToString(bytes, Base64.NO_WRAP);
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 私钥解密
     */
    public static String decryptByPrivate(String privateKeyStr,String ciphertext) {
        try {
            if (privateKey == null)
                privateKey = loadPrivateKey(privateKeyStr);
            byte[] decode = Base64.decode(ciphertext, Base64.NO_WRAP);
            byte[] bytes = decryptByPrivateKey(decode, privateKey);
            String s = new String(bytes,CHAR_SET);
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 调用公钥加密
     */
    private static byte[] encryptByPublicKey(byte[] data, RSAPublicKey publicKey)
            throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 调用公钥解密
     */
    private static byte[] decryptByPublicKey(byte[] data, RSAPublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }


    /**
     * 调用私钥加密
     */
    private static byte[] encryptByPrivateKey(byte[] data, RSAPrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 调用私钥解密
     */
    private static byte[] decryptByPrivateKey(byte[] data, RSAPrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

}
