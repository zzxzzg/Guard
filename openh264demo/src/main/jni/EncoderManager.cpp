//
// Created by yxwang on 16-3-21.
//

#include "EncoderManager.h"


EncoderManager::EncoderManager() {
    pthread_mutex_init(&_mutex, NULL);

    //step 1
    int rv = WelsCreateSVCEncoder (&encoder_);
    assert (rv==0);
    assert (encoder_ != NULL);


}

void EncoderManager::stop() {
    isStart=false;
    if (encoder_) {
        encoder_->Uninitialize();
        WelsDestroySVCEncoder (encoder_);
    }
}

void EncoderManager::setParam(int width, int height) {
    //step2
    SEncParamBase param;
    memset (&param, 0, sizeof (SEncParamBase));
    param.iUsageType = CAMERA_VIDEO_REAL_TIME;
    param.fMaxFrameRate = 30;
    param.iPicWidth = width;
    param.iPicHeight = height;
    param.iTargetBitrate = 5000000;
    encoder_->Initialize (&param);
}

void EncoderManager::putDate(unsigned char *byteBuf, jint width,
                             jint height) {
    if(!isStart){
        return;
    }
    YUVBuffer *buffer=new YUVBuffer(byteBuf,width,height);
    pthread_mutex_lock(&_mutex);
    LOGI("put date   %n",_data_queue.size());
    if( _data_queue.size()>10){
        YUVBuffer *data = NULL;
        data = _data_queue.front();
        _data_queue.pop();
        delete data;
    }
    _data_queue.push(buffer);

    pthread_mutex_unlock(&_mutex);
}

EncoderManager::~EncoderManager() {
    pthread_mutex_lock(&_mutex);
    int size=_data_queue.size();
    for(int i=0;i<size;i++){
        YUVBuffer *data = _data_queue.front();
        _data_queue.pop();
        delete data;
    }
    pthread_mutex_unlock(&_mutex);
    pthread_mutex_destroy(&_mutex);

}
