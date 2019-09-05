package cn.zsy.bspatch;

/**
 * Created by zsy on 2017/7/6.
 */
public class BsPatch {
    //设置JNI参数
   /*   Gradle配置在
   defaultConfig{
         ....加入配置....
        ndk {
            moduleName = 'bsdiff'//生成的so名字,只能有一个名字
            abiFilters "armeabi", "armeabi-v7a", "x86"  //输出指定三种abi体系结构下的so库。目前可有可无。
            }
    }
    */
    //在此导入本地so库
    static {
        System.loadLibrary("bsdiff");
    }

    public static native int bspatch(String oldApk, String newApk, String patch);
}
