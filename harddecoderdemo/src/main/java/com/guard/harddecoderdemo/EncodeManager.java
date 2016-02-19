package com.guard.harddecoderdemo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import android.annotation.TargetApi;
import android.graphics.ImageFormat;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Surface;

@TargetApi(16)
public class EncodeManager {
	private static final String TAG = "sss";
	private static final boolean VERBOSE = false;
	private static final String MIME_TYPE = "video/avc"; // H.264 Advanced Video
															// Coding
	private static final boolean DEBUG_SAVE_FILE = false; // save copy of
															// encoded
															// movie
	private static final String DEBUG_FILE_NAME_BASE = "/sdcard/encode_test";	
	private static final int TEST_Y = 120; // YUV values for colored rect
	private static final int TEST_U = 160;
	private static final int TEST_V = 200;
	private static final int NUM_FRAMES = 300; // two seconds of video

	private int mWidth = -1;
	private int mHeight = -1;
	
	private int mCameraWidth;
	private int mCameraHeight;
	
	// bit rate, in bits per second
	private int mBitRate = -1;
	private int mFrameRate;

	private boolean mFlag = false;

	public DateCallBack mDateCallBack;
	// parameters for the encoder
	private static final int FRAME_RATE = 30; // 15fps
	private static final int IFRAME_INTERVAL = 10; // 10 seconds between
													// I-frames
	private BlockingQueue<byte[]> mPreviewData;

	Thread mEncodeThread;

	private int mColorFormat = -1;

	// private byte[] m_info = null;

	private byte[] sps;
	private byte[] pps;
	private Surface mSurface;
	private int mPreviewFormat;
	
	private String mCodecName;
	
	private int mOrientation;

	public void setSurface(Surface surface) {
		this.mSurface = surface;
	}

	public void setCallBack(DateCallBack callback) {
		mDateCallBack = callback;
	}

	protected void init(int width, int height, int previewFormat,int orientation) {
		mCameraWidth=width;
		mCameraHeight=height;
		mPreviewData = new LinkedBlockingQueue<byte[]>();
		mPreviewFormat = previewFormat;
		mOrientation=orientation;

		
		MediaCodecInfo codecInfo =ColorSpaceManager. selectCodec(MIME_TYPE);
		if (codecInfo == null) {
			// Don't fail CTS if they don't have an AVC codec (not here,
			// anyway).
			Log.e(TAG, "Unable to find an appropriate codec for " + MIME_TYPE);
			return;
		}
		if (VERBOSE)
			Log.d(TAG, "found codec: " + codecInfo.getName());
		int colorFormat = ColorSpaceManager.selectColorFormat(codecInfo, MIME_TYPE);
		if (VERBOSE)
			Log.d(TAG, "found colorFormat: " + colorFormat);
		mColorFormat = colorFormat;
		mCodecName=codecInfo.getName();
	}

	public void putData(byte[] date) {
		 byte[] dst =null;
		 if(date.length<mCameraHeight*mCameraWidth*3/2){
			 return;
		 }
		try{
		     dst = new byte[date.length];
		}catch(OutOfMemoryError e){
			mPreviewData.clear();
			 return;
		}
		if(VERBOSE)
			Log.d(TAG,"put data   "+mPreviewFormat+"   "+mColorFormat+"    "+mOrientation);
		if (mPreviewFormat == ImageFormat.NV21) {
			if (mColorFormat == MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Planar) {
//				ColorSpaceManager.NV21toYUV420Planar(date, dst, mCameraWidth,
//						mCameraHeight);
				
				if(mOrientation%360==0){
					ColorSpaceManager.NV21toYUV420Planar(date, dst, mCameraWidth,
					mCameraHeight);
				}else if(mOrientation%360==90){
				ColorSpaceManager
						.NV21rotate90toYUV420Planar(date, dst, mCameraWidth, mCameraHeight);
				}else if(mOrientation%360==180){
					ColorSpaceManager
					.NV21rotate180toYUV420Planar(date, dst, mCameraWidth, mCameraHeight);
			     }else if(mOrientation%360==270){
					ColorSpaceManager
					.NV21rotate270toYUV420Planar(date, dst, mCameraWidth, mCameraHeight);
				}else{
					ColorSpaceManager.NV21toYUV420Planar(date, dst, mCameraWidth,
							mCameraHeight);
				}
			} else if (mColorFormat == MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420SemiPlanar) {				
//				ColorSpaceManager.NV21toYUV420SemiPlanar(date, dst, mCameraWidth,
//				mCameraHeight);
				if(mOrientation%360==0){
					ColorSpaceManager.NV21toYUV420SemiPlanar(date, dst, mCameraWidth,
					mCameraHeight);
				}else if(mOrientation%360==90){
				ColorSpaceManager
						.NV21rotate90toYUV420SemiPlanar(date, dst, mCameraWidth, mCameraHeight);
				}else if(mOrientation%360==180){
					ColorSpaceManager
					.NV21rotate180toYUV420SemiPlanar(date, dst, mCameraWidth, mCameraHeight);
			     }else if(mOrientation%360==270){
					ColorSpaceManager
					.NV21rotate270toYUV420SemiPlanar(date, dst, mCameraWidth, mCameraHeight);
				}else{
					ColorSpaceManager.NV21toYUV420SemiPlanar(date, dst, mCameraWidth,
							mCameraHeight);
				}
			} else {
				System.arraycopy(date, 0, dst, 0, date.length);
			}
		} 
		//use NV21 first
		else if (mPreviewFormat == ImageFormat.YV12) {
			if (mColorFormat == MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Planar) {
				ColorSpaceManager
						.YV12toYUV420Planar(date, dst, mCameraWidth, mCameraHeight);
			} else if (mColorFormat == MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420SemiPlanar) {
				ColorSpaceManager.YV12toYUV420PackedSemiPlanar(date, dst,
						mCameraWidth, mCameraHeight);
			} else {
				System.arraycopy(date, 0, dst, 0, date.length);
			}
		} else {
			System.arraycopy(date, 0, dst, 0, date.length);
		}
		
		mPreviewData.add(dst);
	}

	public static byte[] NV21toYUV420Planar(final byte[] input,
			final byte[] output, final int width, final int height) {
		final int frameSize = width * height;
		final int qFrameSize = frameSize / 4;
		System.arraycopy(input, 0, output, 0, frameSize); // Y
		for (int i = 0; i < qFrameSize; i++) {
			output[frameSize + i + qFrameSize] = input[frameSize + i * 2]; // Cb
																			// (U)
			output[frameSize + i] = input[frameSize + i * 2 + 1]; // Cr (V)
		}
		return output;
	}

	private byte[] popData() {
		byte[] frameData = null;
		try {
			frameData = mPreviewData.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return frameData;
	}

	public void startEncoder(final int framerate, final int bitrate) {
		if (mEncodeThread != null) {
			return;
		}
		mEncodeThread = new Thread() {
			public void run() {
				try {
					mFlag = true;
					setParameters(mCameraWidth, mCameraHeight, bitrate);
					mFrameRate=framerate;
					encodeVideoFromBuffer(false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		mEncodeThread.start();
	}

	public void stopEncoder() {
		if (VERBOSE)
			Log.d(TAG, "stopEncoder");
		mFlag = false;
		mEncodeThread.interrupt();
		mEncodeThread = null;
	}

	public void testEncodeDecodeVideoFromBufferToBufferQCIF() throws Exception {
		setParameters(176, 144, 1000000);
		encodeVideoFromBuffer(false);
	}

	public void testEncodeDecodeVideoFromBufferToBufferQVGA() throws Exception {
		setParameters(320, 240, 2000000);
		encodeVideoFromBuffer(false);
	}

	public void testEncodeDecodeVideoFromBufferToBuffer720p() throws Exception {
		setParameters(1280, 720, 6000000);
		encodeVideoFromBuffer(false);
	}

	/**
	 * Sets the desired frame size and bit rate.
	 */
	private void setParameters(int width, int height, int bitRate) {
		if ((width % 16) != 0 || (height % 16) != 0) {
			Log.w(TAG, "WARNING: width or height not multiple of 16");
		}
		//the camera in phone should rotate the
		
		if(mOrientation==90|| mOrientation==270){
			mWidth = height;
			mHeight = width;
		}else{
			mWidth = width;
			mHeight = height ;
		}
		
		mBitRate = bitRate;
	}

	private MediaCodec formatEncode() {
        Log.d("sss","formatEncode   "+mWidth+"    "+mHeight+"    "+mBitRate+"     "+mFrameRate);
		// We avoid the device-specific limitations on width and height by
		// using values that
		// are multiples of 16, which all tested devices seem to be able to
		// handle.
		MediaFormat format = MediaFormat.createVideoFormat(MIME_TYPE, mWidth,
				mHeight);
		// Set some properties. Failing to specify some of these can cause
		// the MediaCodec
		// configure() call to throw an unhelpful exception.
		format.setInteger(MediaFormat.KEY_COLOR_FORMAT, mColorFormat);
		format.setInteger(MediaFormat.KEY_BIT_RATE, mBitRate*2);
		format.setInteger(MediaFormat.KEY_FRAME_RATE, mFrameRate);
		format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 2);
		
		if (VERBOSE)
			Log.d(TAG, "format: " + format);
		// Create a MediaCodec for the desired codec, then configure it as
		// an encoder with
		// our desired properties.
		MediaCodec encoder = null;
		try {
			encoder = MediaCodec.createByCodecName(mCodecName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		encoder.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
		return encoder;
	}

	private void encodeVideoFromBuffer(boolean toSurface) throws Exception {
		MediaCodec encoder = null;
		try {
			encoder = formatEncode();
			if (encoder == null) {
				return;
			}
			encoder.start();
			// Create a MediaCodec for the decoder, just based on the MIME type.
			// The various
			// format details will be passed through the csd-0 meta-data later
			// on.
			// decoder = MediaCodec.createDecoderByType(MIME_TYPE);
			doEncodeVideoFromBuffer(encoder);

			// EncodeThread thread = new EncodeThread(encoder, colorFormat);
			// thread.start();
		} finally {
			if (VERBOSE)
				Log.d(TAG, "finally");
			if (encoder != null) {
				encoder.stop();
				encoder.release();
			}
		}
	}

	/**
	 * Does the actual work for encoding frames from buffers of byte[].
	 */
	private void doEncodeVideoFromBuffer(MediaCodec encoder) {
		final int TIMEOUT_USEC = 10000;
		ByteBuffer[] encoderInputBuffers = encoder.getInputBuffers();
		ByteBuffer[] encoderOutputBuffers = encoder.getOutputBuffers();
		MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
		int generateIndex = 0;
		int checkIndex = 0;
		int badFrames = 0;
		// The size of a frame of video data, in the formats we handle, is
		// stride*sliceHeight
		// for Y, and (stride/2)*(sliceHeight/2) for each of the Cb and Cr
		// channels. Application
		// of algebra and assuming that stride==width and sliceHeight==height
		// yields:
		byte[] frameData = null;
		// Just out of curiosity.
		long rawSize = 0;
		long encodedSize = 0;
		
		
		// Save a copy to disk. Useful for debugging the test. Note this is a
		// raw elementary
		// stream, not a .mp4 file, so not all players will know what to do with
		// it.
		FileOutputStream outputStream = null;
		if (DEBUG_SAVE_FILE) {
			String fileName = DEBUG_FILE_NAME_BASE + ".h264";
			try {
				outputStream = new FileOutputStream(fileName);
				Log.d(TAG, "encoded output will be saved as " + fileName);
			} catch (IOException ioe) {
				Log.w(TAG, "Unable to create debug output file " + fileName);
				throw new RuntimeException(ioe);
			}
		}

		// Loop until the output side is done.
		boolean inputDone = false;
		boolean encoderDone = false;
		int errorNum = 0;
		try {
			while (mFlag) {
				if (VERBOSE)
					Log.d(TAG, "loop");

				// If we're not done submitting frames, generate a new one and
				// submit it. By
				// doing this on every loop we're working to ensure that the
				// encoder
				// always has
				// work to do.
				//
				// We don't really want a timeout here, but sometimes there's a
				// delay opening
				// the encoder device, so a short timeout can keep us from
				// spinning
				// hard.
				int inputBufIndex = encoder.dequeueInputBuffer(TIMEOUT_USEC);
				if (VERBOSE)
					Log.d(TAG, "inputBufIndex=" + inputBufIndex);
				if (inputBufIndex >= 0) {
					long ptsUsec = computePresentationTime(generateIndex);
					if (inputDone) {
						// Send an empty frame with the end-of-stream flag set.
						// If we set EOS
						// on a frame with data, that frame data will be
						// ignored, and the
						// output will be short one frame.
						encoder.queueInputBuffer(inputBufIndex, 0, 0, ptsUsec,
								MediaCodec.BUFFER_FLAG_END_OF_STREAM);
						if (VERBOSE)
							Log.d(TAG,
									"sent input EOS (with zero-length frame)");
					} else {
						frameData = popData();
						// generateFrame(generateIndex, encoderColorFormat,
						// frameData);
						ByteBuffer inputBuf = encoderInputBuffers[inputBufIndex];
						// the buffer should be sized to hold one full frame
						if(inputBuf==null ||frameData==null){
							continue;
						}
						if (!(inputBuf.capacity() >= frameData.length)) {
							fail("what happend?!	`");
						}
						inputBuf.clear();
						inputBuf.put(frameData);
						encoder.queueInputBuffer(inputBufIndex, 0,
								frameData.length, ptsUsec, 0);
						if (VERBOSE)
							Log.d(TAG, "submitted frame " + generateIndex
									+ " to enc");
					}
					generateIndex++;
				} else {
					// either all in use, or we timed out during initial setup
					if (VERBOSE)
						Log.d(TAG, "input buffer not available");
				}
				// Check for output from the encoder. If there's no output yet,
				// we
				// either need to
				// provide more input, or we need to wait for the encoder to
				// work
				// its magic. We
				// can't actually tell which is the case, so if we can't get an
				// output buffer right
				// away we loop around and see if it wants more input.
				//
				// Once we get EOS from the encoder, we don't need to do this
				// anymore.
				int encoderStatus = 0;
				while (encoderStatus >= 0 && !encoderDone) {
					encoderStatus = encoder.dequeueOutputBuffer(info,
							TIMEOUT_USEC);
					if (VERBOSE)
						Log.d(TAG, "encoderStatus=" + encoderStatus);
					if (encoderStatus == MediaCodec.INFO_TRY_AGAIN_LATER) {
						errorNum++;
						// no output available yet
						// encoder.flush();
						// encoder.start();
						if (errorNum > 5) {
							if (VERBOSE)
								Log.d(TAG,
										"errorNum   >5 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
							// inputDone = true;
							encoder.release();
							encoder = formatEncode();
							encoder.start();
							encoderInputBuffers = encoder.getInputBuffers();
							encoderOutputBuffers = encoder.getOutputBuffers();
						}
						if (VERBOSE)
							Log.d(TAG, "no output from encoder available");
					} else if (encoderStatus == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
						// not expected for an encoder
						encoderOutputBuffers = encoder.getOutputBuffers();
						if (VERBOSE)
							Log.d(TAG, "encoder output buffers changed");
					} else if (encoderStatus == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
						// not expected for an encoder
						MediaFormat newFormat = encoder.getOutputFormat();
						if (VERBOSE)
							Log.d(TAG, "encoder output format changed: "
									+ newFormat);
					} else if (encoderStatus < 0) {
						fail("unexpected result from encoder.dequeueOutputBuffer: "
								+ encoderStatus);
					} else { // encoderStatus >= 0
						errorNum = 0;
						ByteBuffer encodedData = encoderOutputBuffers[encoderStatus];
						if (encodedData == null) {
							fail("encoderOutputBuffer " + encoderStatus
									+ " was null");
						}

						// // It's usually necessary to adjust the ByteBuffer
						// values
						// to match BufferInfo.
						encodedData.position(info.offset);
						encodedData.limit(info.offset + info.size);
						encodedSize += info.size;

						if (mDateCallBack != null) {
							boolean isPPSSPS = false;
							byte[] data = null;
							data = new byte[info.size];
							encodedData.get(data);
							encodedData.position(info.offset);

							if (sps == null && pps == null) {
								ByteBuffer spsPpsBuffer = ByteBuffer.wrap(data);
								// if (spsPpsBuffer.getInt() == 0x00000001) {
								// m_info = new byte[data.length];
								// System.arraycopy(data, 0, m_info, 0,
								// data.length);
								// }

								// 注 media pps 和 sps是在同一个buffer中给的，使用
								// 00000001分隔。
								// 在使用rtp打包发送时，需要先分别获取sps 和
								// pps，然后分开打包发送，否则部分解码器无法解码。
								if (spsPpsBuffer.getInt() == 0x00000001) {
									int ppsIndex = 0;
									while (!(spsPpsBuffer.get() == 0x00
											&& spsPpsBuffer.get() == 0x00
											&& spsPpsBuffer.get() == 0x00 && spsPpsBuffer
												.get() == 0x01)) {
									}
									ppsIndex = spsPpsBuffer.position();
									sps = new byte[ppsIndex - 4];
									sps[0]=0x00;
									sps[1]=0x00;
									sps[2]=0x00;
									sps[3]=0x01;
									System.arraycopy(data, 4, sps, 4,
											sps.length-4);
									pps = new byte[data.length - ppsIndex+4];
									pps[0]=0x00;
									pps[1]=0x00;
									pps[2]=0x00;
									pps[3]=0x01;
									System.arraycopy(data, ppsIndex, pps, 4,
											pps.length-4);

									isPPSSPS = true;
								}
							}

							if (outputStream != null) {
								try {
									outputStream.write(data);
									Log.d(TAG, "write data--------------");
								} catch (IOException ioe) {
									Log.w(TAG,
											"failed writing debug data to file");
									throw new RuntimeException(ioe);
								}
							}

							if (encodedData.get(4) == 0x65) {
								mDateCallBack.callback(sps, info);
								mDateCallBack.callback(pps, info);
								mDateCallBack.callback(sps, info);
								mDateCallBack.callback(pps, info);
								mDateCallBack.callback(sps, info);
								mDateCallBack.callback(pps, info);
							}

							if (!isPPSSPS) {
								// 实际上这里的处理应该参考分割sps 和 pps。原理是
								// 从mediacodec中返回的是一帧数据，而每一阵数据都是由若干个片组成的
								// 也是使用00000001分割。
								// 部分解码器也会要求分开打包，否则不能识别。(此处没有进行分割。)

								mDateCallBack.callback(data, info);
							}
						}

						encoder.releaseOutputBuffer(encoderStatus, false);

						// encoderDone = (info.flags &
						// MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0;
						// if (encoderDone) {
						// generateIndex = 0;
						// }
					}
				}
				// Check for output from the decoder. We want to do this on
				// every
				// loop to avoid
				// the possibility of stalling the pipeline. We use a short
				// timeout
				// to avoid
				// burning CPU if the decoder is hard at work but the next frame
				// isn't quite ready.
				//
				// If we're decoding to a Surface, we'll get notified here as
				// usual
				// but the
				// ByteBuffer references will be null. The data is sent to
				// Surface
				// instead.
			}
		} finally {
			if (VERBOSE)
				Log.d(TAG, "decoded " + checkIndex + " frames at " + mWidth
						+ "x" + mHeight + ": raw=" + rawSize + ", enc="
						+ encodedSize);
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException ioe) {
					Log.w(TAG, "failed closing debug file");
					throw new RuntimeException(ioe);
				}
			}
			if (checkIndex != NUM_FRAMES) {
				fail("expected " + NUM_FRAMES + " frames, only decoded "
						+ checkIndex);
			}
			if (badFrames != 0) {
				fail("Found " + badFrames + " bad frames");
			}
		}

	}
	
	private static void fail(String s) {
		Log.d(TAG, s);
	}

	private static long computePresentationTime(int frameIndex) {
		return 132 + frameIndex * 1000000 / FRAME_RATE;
	}

	/**
	 * Generates data for frame N into the supplied buffer. We have an 8-frame
	 * animation sequence that wraps around. It looks like this:
	 * 
	 * <pre>
	 *   0 1 2 3
	 *   7 6 5 4
	 * </pre>
	 * 
	 * We draw one of the eight rectangles and leave the rest set to the
	 * zero-fill color.
	 */
	private static boolean isSemiPlanarYUV(int colorFormat) {
		switch (colorFormat) {
		case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Planar:
		case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420PackedPlanar:
			return false;
		case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420SemiPlanar:
		case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420PackedSemiPlanar:
		case MediaCodecInfo.CodecCapabilities.COLOR_TI_FormatYUV420PackedSemiPlanar:
			return true;
		default:
			throw new RuntimeException("unknown format " + colorFormat);
		}
	}

	private void generateFrame(int frameIndex, int colorFormat, byte[] frameData) {
		final int HALF_WIDTH = mWidth / 2;
		boolean semiPlanar = isSemiPlanarYUV(colorFormat);
		// Set to zero. In YUV this is a dull green.
		Arrays.fill(frameData, (byte) 0);
		int startX, startY, countX, countY;
		frameIndex %= 8;
		// frameIndex = (frameIndex / 8) % 8; // use this instead for debug --
		// easier to see
		if (frameIndex < 4) {
			startX = frameIndex * (mWidth / 4);
			startY = 0;
		} else {
			startX = (7 - frameIndex) * (mWidth / 4);
			startY = mHeight / 2;
		}
		for (int y = startY + (mHeight / 2) - 1; y >= startY; --y) {
			for (int x = startX + (mWidth / 4) - 1; x >= startX; --x) {
				if (semiPlanar) {
					// full-size Y, followed by UV pairs at half resolution
					// e.g. Nexus 4 OMX.qcom.video.encoder.avc
					// COLOR_FormatYUV420SemiPlanar
					// e.g. Galaxy Nexus OMX.TI.DUCATI1.VIDEO.H264E
					// OMX_TI_COLOR_FormatYUV420PackedSemiPlanar
					frameData[y * mWidth + x] = (byte) TEST_Y;
					if ((x & 0x01) == 0 && (y & 0x01) == 0) {
						frameData[mWidth * mHeight + y * HALF_WIDTH + x] = (byte) TEST_U;
						frameData[mWidth * mHeight + y * HALF_WIDTH + x + 1] = (byte) TEST_V;
					}
				} else {
					// full-size Y, followed by quarter-size U and quarter-size
					// V
					// e.g. Nexus 10 OMX.Exynos.AVC.Encoder
					// COLOR_FormatYUV420Planar
					// e.g. Nexus 7 OMX.Nvidia.h264.encoder
					// COLOR_FormatYUV420Planar
					frameData[y * mWidth + x] = (byte) TEST_Y;
					if ((x & 0x01) == 0 && (y & 0x01) == 0) {
						frameData[mWidth * mHeight + (y / 2) * HALF_WIDTH
								+ (x / 2)] = (byte) TEST_U;
						frameData[mWidth * mHeight + HALF_WIDTH * (mHeight / 2)
								+ (y / 2) * HALF_WIDTH + (x / 2)] = (byte) TEST_V;
					}
				}
			}
		}
	}

	public static interface DateCallBack {
		public void callback(byte[] data, MediaCodec.BufferInfo info);
	}

	// public class EncodeThread extends Thread {
	// private MediaCodec encoder;
	// private int encoderColorFormat;
	// final int TIMEOUT_USEC = 10000;
	// byte[] m_info = null;
	//
	// int generateIndex = 0;
	// ByteBuffer[] encoderInputBuffers;
	// ByteBuffer[] encoderOutputBuffers;
	// MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
	//
	// public EncodeThread(MediaCodec encoder, int encoderColorFormat) {
	// this.encoder = encoder;
	// this.encoderColorFormat = encoderColorFormat;
	// encoderInputBuffers = encoder.getInputBuffers();
	// encoderOutputBuffers = encoder.getOutputBuffers();
	// flag=true;
	// }
	//
	// @Override
	// public void run() {
	// super.run();
	// int checkIndex = 0;
	// int badFrames = 0;
	// // The size of a frame of video data, in the formats we handle, is
	// // stride*sliceHeight
	// // for Y, and (stride/2)*(sliceHeight/2) for each of the Cb and Cr
	// // channels. Application
	// // of algebra and assuming that stride==width and
	// // sliceHeight==height yields:
	// byte[] frameData = null;
	// // Just out of curiosity.
	// long rawSize = 0;
	// long encodedSize = 0;
	// // Save a copy to disk. Useful for debugging the test. Note this is
	// // a raw elementary
	// // stream, not a .mp4 file, so not all players will know what to do
	// // with it.
	// FileOutputStream outputStream = null;
	// if (DEBUG_SAVE_FILE) {
	// String fileName = DEBUG_FILE_NAME_BASE + ".h264";
	// try {
	// outputStream = new FileOutputStream(fileName);
	// Log.d(TAG, "encoded output will be saved as " + fileName);
	// } catch (IOException ioe) {
	// Log.w(TAG, "Unable to create debug output file " + fileName);
	// throw new RuntimeException(ioe);
	// }
	// }
	//
	// while (flag) {
	// int ret = doEncode(encoder, popData(), h264);
	// Log.d("sss", "ret=" + ret);
	// if (ret > 0) {
	// if (outputStream != null) {
	// try {
	// Log.d(TAG, "write data");
	// outputStream.write(h264);
	// } catch (IOException ioe) {
	// Log.w(TAG, "failed writing debug data to file");
	// throw new RuntimeException(ioe);
	// }
	// }
	// }
	// }
	// if (encoder != null) {
	// encoder.stop();
	// encoder.release();
	// }
	// if (outputStream != null) {
	// try {
	// outputStream.close();
	// } catch (IOException ioe) {
	// Log.w(TAG, "failed closing debug file");
	// throw new RuntimeException(ioe);
	// }
	// }
	// }
	//
	// private int doEncode(MediaCodec encodec, byte[] input, byte[] output) {
	// int pos = 0;
	//
	// int inputBufIndex = encoder.dequeueInputBuffer(TIMEOUT_USEC);
	// if (VERBOSE)
	// Log.d(TAG, "inputBufIndex=" + inputBufIndex);
	// if (inputBufIndex >= 0) {
	// long ptsUsec = computePresentationTime(generateIndex);
	// if (generateIndex == NUM_FRAMES) {
	// // Send an empty frame with the end-of-stream flag set.
	// // If we set EOS
	// // on a frame with data, that frame data will be
	// // ignored, and the
	// // output will be short one frame.
	// encoder.queueInputBuffer(inputBufIndex, 0, 0, ptsUsec,
	// MediaCodec.BUFFER_FLAG_END_OF_STREAM);
	// if (VERBOSE)
	// Log.d(TAG, "sent input EOS (with zero-length frame)");
	// } else {
	// // generateFrame(generateIndex, encoderColorFormat,
	// // frameData);
	// ByteBuffer inputBuf = encoderInputBuffers[inputBufIndex];
	// // the buffer should be sized to hold one full frame
	// Log.d("sss",
	// "     read frameData    " + inputBuf.capacity()
	// + "   " + input.length);
	// if (!(inputBuf.capacity() >= input.length)) {
	// fail("what happend?!	`");
	// }
	// inputBuf.clear();
	// inputBuf.put(input);
	// encoder.queueInputBuffer(inputBufIndex, 0, input.length,
	// ptsUsec, 0);
	// if (VERBOSE)
	// Log.d(TAG, "submitted frame " + generateIndex
	// + " to enc");
	// }
	// generateIndex++;
	// } else {
	// // either all in use, or we timed out during initial setup
	// if (VERBOSE)
	// Log.d(TAG, "input buffer not available");
	// }
	//
	// while (true) {
	// int encoderStatus = encoder.dequeueOutputBuffer(info,
	// TIMEOUT_USEC);
	// if (VERBOSE)
	// Log.d(TAG, "encoderStatus=" + encoderStatus);
	// if (encoderStatus == MediaCodec.INFO_TRY_AGAIN_LATER) {
	// // no output available yet
	// if (VERBOSE)
	// Log.d(TAG, "no output from encoder available");
	// break;
	// } else if (encoderStatus == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
	// // not expected for an encoder
	// encoderOutputBuffers = encoder.getOutputBuffers();
	// if (VERBOSE)
	// Log.d(TAG, "encoder output buffers changed");
	// break;
	// } else if (encoderStatus == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
	// // not expected for an encoder
	// MediaFormat newFormat = encoder.getOutputFormat();
	// if (VERBOSE)
	// Log.d(TAG, "encoder output format changed: "
	// + newFormat);
	// break;
	// } else if (encoderStatus < 0) {
	// fail("unexpected result from encoder.dequeueOutputBuffer: "
	// + encoderStatus);
	// break;
	// } else { // encoderStatus >= 0
	// ByteBuffer outputBuffer = encoderOutputBuffers[encoderStatus];
	// if (outputBuffer == null) {
	// fail("encoderOutputBuffer " + encoderStatus
	// + " was null");
	// }
	// byte[] outData = new byte[info.size];
	// outputBuffer.get(outData);
	//
	// if (m_info != null) {
	// System.arraycopy(outData, 0, output, pos,
	// outData.length);
	// pos += outData.length;
	//
	// }
	//
	// else // 保存pps sps 只有开始时 第一个帧里有， 保存起来后面用
	// {
	// ByteBuffer spsPpsBuffer = ByteBuffer.wrap(outData);
	// if (spsPpsBuffer.getInt() == 0x00000001) {
	// m_info = new byte[outData.length];
	// System.arraycopy(outData, 0, m_info, 0,
	// outData.length);
	// } else {
	// return -1;
	// }
	// }
	//
	// encodec.releaseOutputBuffer(encoderStatus, false);
	// }
	// }
	// if (output[4] == 0x65) // key frame 编码器生成关键帧时只有 00 00 00 01 65
	// // 没有pps sps， 要加上
	// {
	// System.arraycopy(output, 0, input, 0, pos);
	// System.arraycopy(m_info, 0, output, 0, m_info.length);
	// System.arraycopy(input, 0, output, m_info.length, pos);
	// pos += m_info.length;
	// }
	// return pos;
	// }
	//
	// }

}
