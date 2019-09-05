#include <jni.h>

#include<stdlib.h>
#include <unistd.h>

//需要<stdlib.h>库
int getPressure() {
    return rand() % 101;
}

int flag = 0;
int i = 0;

JNIEXPORT void JNICALL Java_com_zsy_jni_MonitorJni_start
        (JNIEnv *env, jclass obj) {
    jclass clazz = (*env)->FindClass(env, "com/zsy/jni/MonitorJni");

//    jmethodID methed = (*env)->GetMethodID(env, clazz, "setValue", "(I)V");
    jmethodID methed = (*env)->GetStaticMethodID(env, clazz, "setValue", "(I)V");

    flag = 1;

    while (flag) {
        sleep(1);
        i++;
//        (*env)->CallVoidMethod(env, obj, methed, getPressure());
        (*env)->CallStaticVoidMethod(env, obj, methed, i);
    }
}


JNIEXPORT void JNICALL Java_com_zsy_jni_MonitorJni_stop
        (JNIEnv *env, jclass zclass) {
    flag = 0;
}