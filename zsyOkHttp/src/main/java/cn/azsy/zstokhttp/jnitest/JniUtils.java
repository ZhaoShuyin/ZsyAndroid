package cn.azsy.zstokhttp.jnitest;

/**
 * Created by zsy on 2017/6/26.
 */

public class JniUtils {
    public static native String getStringFromJni();
    static {
        System.loadLibrary("bsdiff");   //载入定义的JNI-SO库文件
    }
}
