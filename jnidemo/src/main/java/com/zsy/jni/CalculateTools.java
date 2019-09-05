package com.zsy.jni;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:JNi 通过 C 计算
 * </p>
 *
 * @author Zsy
 * @date 2019/7/26 12:58
 */
public class CalculateTools {

    static {
        System.loadLibrary("zsyso");
    }

    public static native int add(int a,int b);
    public static native int subtract(int a,int b);
    public static native int multiply(int a,int b);
    public static native int divide(int a,int b);
}
