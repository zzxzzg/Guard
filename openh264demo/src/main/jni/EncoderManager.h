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
#include "util.h"

#include "include/svc/codec_api.h"
#include "include/svc/codec_ver.h"
#include "include/svc/codec_app_def.h"

class EncoderManager {
public:
    EncoderManager();
    ~EncoderManager();
    void putDate(unsigned char* yuvData, jint width, jint height);
    void setParam(int width,int height);
    void stop();
    pthread_mutex_t _mutex;
    std::queue<YUVBuffer*> _data_queue;

    ISVCEncoder* encoder_;

    bool isStart=true;

};


#endif //GUARD_ENCODERMANAGER_H
