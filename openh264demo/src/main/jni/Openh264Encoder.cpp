//
// Created by yxwang on 16-3-21.
//

#include "Openh264Encoder.h"
#include "include/svc/codec_app_def.h"
#include <unistd.h>

Openh264Encoder::Openh264Encoder() {
    pthread_mutex_init(&_mutex, NULL);

    //step 1
    int rv = WelsCreateSVCEncoder (&encoder_);
    assert (rv==0);
    assert (encoder_ != NULL);
    out.open("/sdcard/openh264/test.264");

}

YUVBuffer *Openh264Encoder::getDate() {
    YUVBuffer* data=NULL;
    pthread_mutex_lock (&_mutex);
    if (!_data_queue.empty()) {
        data =_data_queue.front();
        _data_queue.pop();
    }
    pthread_mutex_unlock(&_mutex);
    return data;
}
void* _thread_func(void *arg) {
    Openh264Encoder *manage = (Openh264Encoder *) arg;

    int width;
    int height;
    int frameSize;
    SFrameBSInfo info;
    memset (&info, 0, sizeof (SFrameBSInfo));
    SSourcePicture pic;
    memset (&pic, 0, sizeof (SSourcePicture));




    while(manage->isStart){
        YUVBuffer* data=manage->getDate();
        if(data!=NULL){
            width=data->width;
            height=data->height;
            frameSize = data->len;
            pic.iPicWidth = width;
            pic.iPicHeight = height;
            pic.iColorFormat = videoFormatI420;
            pic.iStride[0] = pic.iPicWidth;
            pic.iStride[1] = pic.iStride[2] = pic.iPicWidth >> 1;
            pic.pData[0] = data->byteBuf;
            pic.pData[1] = pic.pData[0] + width * height;
            pic.pData[2] = pic.pData[1] + (width * height >> 2);

            int rv = manage->encoder_->EncodeFrame (&pic, &info);
            if(rv == cmResultSuccess && info.eFrameType != videoFrameTypeSkip){
                //LOGI("encoder  info.eFrameType= %d  and iLayerNum = %d",info.eFrameType,info.iLayerNum);
                for(int i=0;i<info.iLayerNum;i++){

//                    LOGI("layer %d layer frame type=%d,uiLayerType=%d,nalcount=%d",i,info.sLayerInfo[i].eFrameType,
//                         info.sLayerInfo[i].uiLayerType,
//                         info.sLayerInfo[i].iNalCount
//                    );
                    int size=0;
                    for(int j=0;j<info.sLayerInfo[i].iNalCount;j++){
//                        LOGI("nal %d length is %d",j,
//                             info.sLayerInfo[i].pNalLengthInByte[j]
//                        );
                        size+=info.sLayerInfo[i].pNalLengthInByte[j];
                    }
                    if(manage->out){
                        manage->out.write( (char*)info.sLayerInfo[i].pBsBuf,size);
                    }
                }
//                if(info.eFrameType==videoFrameTypeIDR && info.sLayerInfo[i].eFrameType==NON_VIDEO_CODING_LAYER){
//                    //SPS
//                }
            }

            delete data;
        }
        usleep(5000);
    }

}


void Openh264Encoder::start() {
    if(isStart){
        return;
    }
    isStart=true;
    pthread_create(&thread_encoder, NULL, _thread_func, this);
}

void Openh264Encoder::stop() {
    isStart=false;
    pthread_join(thread_encoder, NULL);
    out.close();
    if (encoder_) {
        encoder_->Uninitialize();
        WelsDestroySVCEncoder (encoder_);
    }
}

void Openh264Encoder::setParam(int width,int height,int frameRate,int bitRate) {
    //step2
    SEncParamBase param;
    memset (&param, 0, sizeof (SEncParamBase));
    param.iUsageType = CAMERA_VIDEO_REAL_TIME;
    param.fMaxFrameRate = frameRate;
    param.iPicWidth = width;
    param.iPicHeight = height;
    param.iTargetBitrate = bitRate;
    encoder_->Initialize (&param);


    //step 3

    encoder_->SetOption (ENCODER_OPTION_TRACE_LEVEL, &g_LevelSetting);
    int videoFormat = videoFormatI420;
    encoder_->SetOption (ENCODER_OPTION_DATAFORMAT, &videoFormat);
}

void Openh264Encoder::putDate(unsigned char *byteBuf, jint width,
                             jint height) {
    if(!isStart){
        return;
    }
    YUVBuffer *buffer=new YUVBuffer(byteBuf,width,height);
    LOGI("put date   %d",_data_queue.size());
    //pthread_mutex_lock(&_mutex);

    if( _data_queue.size()>10){
        YUVBuffer *data = NULL;
        data = _data_queue.front();
        _data_queue.pop();
        delete data;
    }
    _data_queue.push(buffer);

    //pthread_mutex_unlock(&_mutex);
}

Openh264Encoder::~Openh264Encoder() {
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
