LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := skumodel
LOCAL_C_INCLUDES += $(LOCAL_PATH)/include/ \

LOCAL_SRC_FILES:= SKUHelper.cpp jsoncpp.cpp
LOCAL_LDLIBS +=  -llog
LOCAL_CPPFLAGS += -pthread

include $(BUILD_SHARED_LIBRARY)
