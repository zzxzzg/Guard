#include <jni.h>
#include <assert.h>
#include <stdio.h>
#include <pthread.h>
#include "YUVBuffer.h"
#include "EncoderManager.h"
#include "util.h"


EncoderManager *manager;

void init(JNIEnv *pEnv, jobject pObj,jint width,jint height){
    manager=new EncoderManager();
    manager->setParam(width,height);
}

static void nativePutLocalDate(JNIEnv *env, jobject thiz,jbyteArray yuvData, jint width, jint height) {
    jint len = env->GetArrayLength(yuvData);
    unsigned char *byteBuf = (unsigned char*) env->GetByteArrayElements(
            yuvData, 0);
    manager->putDate(byteBuf,width,height);
    env->ReleaseByteArrayElements(yuvData, (jbyte*) byteBuf, 0);
}

static void nativeStop(JNIEnv *env, jobject thiz){

}

static JNINativeMethod gMethods[] = {
        { "init", "(II)V",(void *) init },
        { "nativePutLocalDate", "([BII)V", (void *) nativePutLocalDate },
        {"nativeStop","()V",(void*)nativeStop}
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