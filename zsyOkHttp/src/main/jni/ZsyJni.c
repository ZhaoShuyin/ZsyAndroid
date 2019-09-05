//
// Created by Administrator on 2017/6/26.
//
#include "cn_azsy_zstokhttp_jnitest_JniUtils.h"
/*
 * Class:     io_github_yanbober_ndkapplication_NdkJniUtils
 * Method:    getCLanguageString
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_cn_azsy_zstokhttp_jnitest_JniUtils_getStringFromJni
        (JNIEnv *env, jobject obj){
    return (*env)->NewStringUTF(env,"HaHa,这是从C中获取的字符串");
}
