#include <jni.h>
#include <assert.h>
#include <stdio.h>
#include <pthread.h>
#include "YUVBuffer.h"
#include "Openh264Encoder.h"
#include "util.h"


Openh264Encoder *manager;

void initOpenH264Encoder(JNIEnv *pEnv, jobject pObj,jint width,jint height){
    manager=new Openh264Encoder();
}

void startOpenH264Encoder(JNIEnv *pEnv, jobject pObj){
    manager->start();
}

static void putYUVDate(JNIEnv *env, jobject thiz,jbyteArray yuvData, jint width, jint height) {
    jint len = env->GetArrayLength(yuvData);
    unsigned char *byteBuf = (unsigned char*) env->GetByteArrayElements(
            yuvData, 0);
    manager->putDate(byteBuf,width,height);
    env->ReleaseByteArrayElements(yuvData, (jbyte*) byteBuf, 0);
}

static void stopOpenH264Encoder(JNIEnv *env, jobject thiz){
    manager->stop();
}

static void setOpenH264Param(JNIEnv *env, jobject thiz,jint width,jint height,jint frameRate,jint bitrate){
    manager->setParam(width,height,frameRate,bitrate);
}

static JNINativeMethod gMethods[] = {
        { "nativeInitOpenH264Encoder", "()V",(void *) initOpenH264Encoder },
        { "nativePutYUVDate", "([BII)V", (void *) putYUVDate },
        { "nativeStopOpenH264Encoder","()V",(void*)stopOpenH264Encoder},
        { "nativeStartOpenH264Encoder","()V",(void*)startOpenH264Encoder},
        { "nativeSetOpenH264Param","(IIII)V",(void*)setOpenH264Param},

};

static const char* const CONNECT_JAVA_PACKAGE =
        "com/guard/openh264demo/OpenH264Model";

static int registerMethods(JNIEnv* env) {
    jclass clazz;

    /* look up the class */
    clazz = env->FindClass(CONNECT_JAVA_PACKAGE);

    if (clazz == NULL) {
        return -1;
    }

    /* register all the methods */
    if (env->RegisterNatives(clazz, gMethods,
                             sizeof(gMethods) / sizeof(gMethods[0])) != JNI_OK) {
        env->DeleteLocalRef(clazz);
        return -1;
    }

    env->DeleteLocalRef(clazz);

    /* fill out the rest of the ID cache */
    return 0;
}

void JNI_OnUnload(JavaVM *vm, void *reserved){

}

jint JNI_OnLoad(JavaVM* vm, void* reserved) {
    JNIEnv* env = NULL;
    jint result = -1;
    if (vm->GetEnv((void**) &env, JNI_VERSION_1_6) == JNI_OK) {
        assert(env != NULL);
        if (registerMethods(env) != 0) {
            LOGI("ERROR: PlatformLibrary native registration failed\n");
            goto fail;
        }
    }
    /* success -- return valid version number */
    result = JNI_VERSION_1_6;
    fail: return result;
}