LOCAL_PATH:=$(call my-dir)


include $(CLEAR_VARS)
LOCAL_SRC_FILES:= \
 	test.cpp
LOCAL_SHARED_LIBRARIES := \
	libcutils
LOCAL_MODULE:= libtest
include $(BUILD_SHARED_LIBRARY)




include $(CLEAR_VARS)

LOCAL_SRC_FILES:= \
 	VideoController.cpp 
 		
LOCAL_SHARED_LIBRARIES := \
	libcutils \
	libtest \
	libdl
	
LOCAL_C_INCLUDES := \
	libstdc++\
	bionic \
	external/stlport/stlport \
	$(JNI_H_INCLUDE)

LOCAL_STATIC_LIBRARIES := \
	libstlport_static

APP_STL := stlport_static
	
############

	

#this for ndk-build
LOCAL_LDLIBS +=  -llog
##############

LOCAL_MODULE:= libmaketest


include $(BUILD_SHARED_LIBRARY)