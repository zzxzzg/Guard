//
// Created by yxwang on 16-3-21.
//

#include "YUVBuffer.h"

YUVBuffer::YUVBuffer(unsigned char *data, int width_, int height_) {
    width= width_;
    height = height_;
    len=getSize(width_,height_);
    byteBuf = new unsigned char[len];
    memcpy(byteBuf, data, len);
}

YUVBuffer::~YUVBuffer() {
    delete[] byteBuf;
}