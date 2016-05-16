//
// Created by yxwang on 16/5/13.
//

#include "SKUHelper.h"
#include "util.h"
#include "json/json.h"

void initSKU(JNIEnv *env, jobject thiz,jstring json){
//   const char* str;
//   str = env->GetStringUTFChars(json, false);
//   if(str == NULL) {
//       return ; /* OutOfMemoryError already thrown */
//   }
   //TODO for str
//   LOGI("%s",str);
//   env->ReleaseStringUTFChars(json, str);
}

static JNINativeMethod gMethods[] = {
     { "initSKU", "(Ljava/lang/String)V",(void *) initSKU },

};

static const char* const CONNECT_JAVA_PACKAGE =
        "com/carme/caruser/utils/SKUHelper";

static int registerMethods(JNIEnv* env) {
    jclass clazz;
    /* look up the class */
    clazz = env->FindClass(CONNECT_JAVA_PACKAGE);

    if (clazz == nullptr) {
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