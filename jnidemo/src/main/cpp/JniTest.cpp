#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring
JNICALL Java_com_zsy_jni_JniTest_getStringFromJni(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "HaHa,这是从C中获取的字符串(Cmake方式)";
    return env->NewStringUTF(hello.c_str());
}

