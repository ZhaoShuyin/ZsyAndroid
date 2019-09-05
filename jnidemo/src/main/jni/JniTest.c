//
// Created by Administrator on 2017/6/26.
//jstring 需要<jni.h>
//
#include <jni.h>

/*
 * Class:     io_github_yanbober_ndkapplication_NdkJniUtils
 * Method:    getCLanguageString
 * Signature: ()Ljava/lang/String;
 */
jstring JNICALL Java_com_zsy_jni_JniTest_getStringFromJni
        (JNIEnv *env, jobject obj) {
    return (*env)->NewStringUTF(env, "HaHa,这是从C中获取的字符串(ndk-build方式)");
}
