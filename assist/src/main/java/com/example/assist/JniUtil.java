package com.example.assist;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:
 * </p>
 *
 * @author Zsy
 * @date 2019/7/18 16:55
 */

public class JniUtil {
    static {
        System.loadLibrary("zsyso");
    }

    public static native String getString();
}
