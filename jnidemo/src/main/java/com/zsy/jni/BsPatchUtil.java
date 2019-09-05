package com.zsy.jni;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:
 * </p>
 *
 * @author Zsy
 * @date 2019/7/29 13:45
 */

public class BsPatchUtil {
    //在此导入本地so库
    static {
        System.loadLibrary("zsyso");
    }

    public static native int bspatch(String oldFile, String newFile, String patch);
}
