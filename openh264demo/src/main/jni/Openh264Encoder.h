//
// Created by yxwang on 16-3-21.
//

#ifndef GUARD_ENCODERMANAGER_H
#define GUARD_ENCODERMANAGER_H
#include <jni.h>
#include <queue>
#include <YUVBuffer.h>
#include <pthread.h>
#include <assert.h>
#include <fstream>
#include "util.h"

#include "include/svc/codec_api.h"
#include "include/svc/codec_ver.h"
#include "include/svc/codec_app_def.h"
#include "include/svc/codec_def.h"

class Openh264Encoder {
public:
    Openh264Encoder();
    ~Openh264Encoder();
    void putDate(unsigned char* yuvData, jint width, jint height);
    YUVBuffer* getDate();

    void setParam(int width,int height,int frameRate,int bitRate);
    void stop();
    void start();
    pthread_mutex_t _mutex;
    std::queue<YUVBuffer*> _data_queue;

    ISVCEncoder* encoder_;

    bool isStart=false;

    pthread_t thread_encoder;
    int     g_LevelSetting = WELS_LOG_ERROR;
    std::ofstream out;

};

void* _thread_func(void *arg) ;

#endif //GUARD_ENCODERMANAGER_H
