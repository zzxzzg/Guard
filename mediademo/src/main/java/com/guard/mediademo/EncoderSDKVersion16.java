package com.guard.mediademo;


import android.annotation.TargetApi;

@TargetApi(16)
public class EncoderSDKVersion16 extends AbstractEncoder{
	
	EncodeManager mEncoderManager;
	
	@Override
	public boolean canEncoder() {
		return true;
	}

	@Override
	public void initEncoder(int width, int height, int framerate, int bitrate,int previewFormat,int orientation) {
		mEncoderManager=new EncodeManager();
		mEncoderManager.init(width, height, previewFormat, orientation);
		mEncoderManager.startEncoder(framerate, bitrate);
	}
	

	@Override
	public void closeEncoder() {
		if(mEncoderManager!=null){
			mEncoderManager.stopEncoder();
		}
	}


	@Override
	public void offerEncoder(byte[] input) {
		// TODO Auto-generated method stub
		 mEncoderManager.putData(input);
	}

	public void setCallBack(EncodeManager.DateCallBack callback) {
		mEncoderManager.setCallBack(callback);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	private MediaCodec mediaCodec;  
//    int m_width;  
//    int m_height;  
//    byte[] m_info = null;  
//    private byte[] yuv420 = null;   
//	@Override
//	public boolean canEncoder() {
//		return true;
//	}
//
//	@Override
//	public void initEncoder(int width, int height, int framerate, int bitrate) {
//		
//		int colorFormat = 0;
//	    MediaCodecInfo.CodecCapabilities capabilities = selectCodec("video/avc").getCapabilitiesForType("video/avc");
//	    for (int i = 0; i < capabilities.colorFormats.length && colorFormat == 0; i++) {
//	        int format = capabilities.colorFormats[i];
//	        Log.e("sss", "Using color format " + format);           
//	    }
//	    
//		 m_width  = width;  
//	        m_height = height;  
//	        yuv420 = new byte[width*height*3/2];  
//	      
//	        try {
//				mediaCodec = MediaCodec.createEncoderByType("video/avc");
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}  
//	        MediaFormat mediaFormat = MediaFormat.createVideoFormat("video/avc", width, height);  
//	        mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, bitrate);  
//	        mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, framerate);
//	        mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Planar);      
//	        mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 1); //关键帧间隔时间 单位s  
//	          
//	        mediaCodec.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);  
//	        mediaCodec.start();
//	}
//
//	@Override
//	public void closeEncoder() {
//		 try {  
//	            mediaCodec.stop();  
//	            mediaCodec.release();  
//	        } catch (Exception e){   
//	            e.printStackTrace();  
//	        }  
//	}
//
//	@Override
//	public int offerEncoder(byte[] input, byte[] output) {
//		 int pos = 0;  
//	        swapYV12toI420(input, yuv420, m_width, m_height);  
//	        try {  
//	            ByteBuffer[] inputBuffers = mediaCodec.getInputBuffers();  
//	            ByteBuffer[] outputBuffers = mediaCodec.getOutputBuffers();  
//	            int inputBufferIndex = mediaCodec.dequeueInputBuffer(-1);  
//	            if (inputBufferIndex >= 0)   
//	            {  
//	                ByteBuffer inputBuffer = inputBuffers[inputBufferIndex];  
//	                inputBuffer.clear();  
//	                inputBuffer.put(yuv420);  
//	                mediaCodec.queueInputBuffer(inputBufferIndex, 0, yuv420.length, 0, 0);  
//	            }  
//	  
//	            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();  
//	            int outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo,0);  
//	             
//	            while (outputBufferIndex >= 0)   
//	            {  
//	                ByteBuffer outputBuffer = outputBuffers[outputBufferIndex];  
//	                byte[] outData = new byte[bufferInfo.size];  
//	                outputBuffer.get(outData);  
//	                  
//	                if(m_info != null)  
//	                {                 
//	                    System.arraycopy(outData, 0,  output, pos, outData.length);  
//	                    pos += outData.length;  
//	                      
//	                }  
//	                  
//	                else //保存pps sps 只有开始时 第一个帧里有， 保存起来后面用  
//	                {  
//	                     ByteBuffer spsPpsBuffer = ByteBuffer.wrap(outData);    
//	                     if (spsPpsBuffer.getInt() == 0x00000001)   
//	                     {    
//	                         m_info = new byte[outData.length];  
//	                         System.arraycopy(outData, 0, m_info, 0, outData.length);  
//	                     }   
//	                     else   
//	                     {    
//	                            return -1;  
//	                     }        
//	                }  
//	                  
//	                mediaCodec.releaseOutputBuffer(outputBufferIndex, false);  
//	                outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, 0);  
//	            }  
//	  
//	            if(output[4] == 0x65) //key frame   编码器生成关键帧时只有 00 00 00 01 65 没有pps sps， 要加上  
//	            {  
//	                System.arraycopy(output, 0,  yuv420, 0, pos);  
//	                System.arraycopy(m_info, 0,  output, 0, m_info.length);  
//	                System.arraycopy(yuv420, 0,  output, m_info.length, pos);  
//	                pos += m_info.length;  
//	            }  
//	              
//	        } catch (Throwable t) {  
//	            t.printStackTrace();  
//	        }  
//	  
//	        return pos;  
//	}
//	
//    //yv12 转 yuv420p  yvu -> yuv  
//   private void swapYV12toI420(byte[] yv12bytes, byte[] i420bytes, int width, int height)   
//   {        
//       System.arraycopy(yv12bytes, 0, i420bytes, 0,width*height);  
//       System.arraycopy(yv12bytes, width*height+width*height/4, i420bytes, width*height,width*height/4);  
//       System.arraycopy(yv12bytes, width*height, i420bytes, width*height+width*height/4,width*height/4);    
//   }    

}
