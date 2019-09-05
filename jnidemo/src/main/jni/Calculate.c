//
// Created by meridian on 2019/7/26.
//
#include <jni.h>

jint JNICALL Java_com_zsy_jni_CalculateTools_add
        (JNIEnv *env, jclass clazz, jint a, jint b) {
    return a + b;
}

jint JNICALL Java_com_zsy_jni_CalculateTools_subtract
        (JNIEnv *env, jclass clazz, jint a, jint b) {
    return a - b;
}

jint JNICALL Java_com_zsy_jni_CalculateTools_multiply
        (JNIEnv *env, jclass clazz, jint a, jint b) {
    return a * b;
}

jint JNICALL Java_com_zsy_jni_CalculateTools_divide
        (JNIEnv *env, jclass clazz, jint a, jint b) {
    return a / b;
}