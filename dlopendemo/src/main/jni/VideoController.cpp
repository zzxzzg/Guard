#include <jni.h>
#include <assert.h>
#include <android/log.h>
#include <stdio.h>
#include <queue>
#include "test.h"
#include <dlfcn.h>


#define LOG_TAG "sss"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)

static  int version;
std::queue<char*> _data_queue;

typedef int (*printcall)(test* prt,unsigned int a);

jlong init(JNIEnv *pEnv, jobject pObj,jint arg){
//	DecoderManager *manager=new DecoderManager();
//	manager->arg=arg;
//	manager->version=version;
//	return (long)manager;
	unsigned int st=10;
	printcall callback;
	char const*error;
	test mytest;

	void *handle=dlopen("libtest.so",RTLD_LAZY);
	dlerror();
	if(!handle){
		LOGI("dlopen fail");
	}
	callback=(printcall)dlsym(handle,"_ZN4test8my_printEi");
	if ((error = dlerror()) != NULL)  {
		LOGI("%s",error);
	  }else{
		  	LOGI("%d",(*callback)(&mytest,1));
	  }
	if(!callback){

	}

	dlclose(handle);
	return 0;
}

static JNINativeMethod gMethods[] = {
		{ "init", "(I)J",(void *) init },
};

static const char* const CONNECT_JAVA_PACKAGE =
		"com/guard/dlopendemo/MainActivity";

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

		jclass clazz = env->FindClass("android/os/Build$VERSION");
		jfieldID feild = env->GetStaticFieldID(clazz, "SDK_INT", "I");
		version = env->GetStaticIntField(clazz, feild);

		if (registerMethods(env) != 0) {
			LOGI("ERROR: PlatformLibrary native registration failed\n");
			goto fail;
		}
	}
	/* success -- return valid version number */
	result = JNI_VERSION_1_6;
	fail: return result;
}
