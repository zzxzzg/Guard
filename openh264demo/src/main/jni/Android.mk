LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := openh264
LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/include/svc
LOCAL_SRC_FILES := lib/$(TARGET_ARCH_ABI)/libopenh264.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)

LOCAL_MODULE := openh264encoder
LOCAL_SRC_FILES := lib/$(TARGET_ARCH_ABI)/libencoder.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)

LOCAL_MODULE := openh264common
LOCAL_SRC_FILES := lib/$(TARGET_ARCH_ABI)/libcommon.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)

LOCAL_MODULE := openh264processing
LOCAL_SRC_FILES := lib/$(TARGET_ARCH_ABI)/libprocessing.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)

LOCAL_MODULE := openh264decoder
LOCAL_SRC_FILES := lib/$(TARGET_ARCH_ABI)/libdecoder.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)

LOCAL_MODULE := openh264model
#LOCAL_C_INCLUDES += $(LOCAL_PATH)/include/ \

LOCAL_SRC_FILES:= Openh264.cpp YUVBuffer.cpp Openh264Encoder.cpp
LOCAL_SHARED_LIBRARIES := openh264
LOCAL_STATIC_LIBRARIES := openh264encoder openh264common openh264processing openh264decoder
LOCAL_LDLIBS +=  -llog
LOCAL_CPPFLAGS := -std=c++11 -pthread

include $(BUILD_SHARED_LIBRARY)
