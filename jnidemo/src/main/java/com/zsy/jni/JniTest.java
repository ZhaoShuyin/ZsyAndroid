package com.zsy.jni;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:简单的通过JNI从C获取字符串
 * </p>
 *
 * @author Zsy
 * @date 2019/7/23 14:24
 */

public class JniTest {
    static {
        System.loadLibrary("zsyso");
    }

    public native static String getStringFromJni();

}
