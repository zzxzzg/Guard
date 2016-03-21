//
// Created by yxwang on 16-3-21.
//

#ifndef GUARD_YUVBUFFER_H
#define GUARD_YUVBUFFER_H
#include <jni.h>
#include<string.h>

class YUVBuffer {
public:
    YUVBuffer(unsigned char *data, int width_, int height_) ;
    ~YUVBuffer() ;

    unsigned char * byteBuf;
    jint width;
    jint height;
    int len;
};

inline int getSize(int width,int height){
    return width*height*3/2;
}


#endif //GUARD_YUVBUFFER_H
