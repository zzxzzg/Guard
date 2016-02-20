package com.guard.harddecoderdemo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.media.MediaCodec.BufferInfo;
import android.util.Log;
import android.view.Surface;

public class DecoderManager {
	private MediaCodec mMediaDecoder;
	private static final String MIME_TYPE = "video/avc"; // H.264 Advanced Video
	private static final String TAG = "sss";
	private static final int MAX_INPUT_SIZE = 1920 * 1080;
	private static final boolean VERBOSE = true;
	private Surface mSurface;
	private boolean mFlag = true;
	private BlockingQueue<byte[]> mPreviewData;
	private Thread mDecoderThread;
	private boolean hasFindSps = false;
	private boolean isGetYuvBuffer = false;// 从解码器获取yuv数据还是直接刷到surface中
	private long mInstance;
	private Object mObject=new Object();

	private static final boolean DEBUG_SAVE_FILE = false; // save copy of
	// encoded
	// movie
	private static final String DEBUG_FILE_NAME_BASE = "/sdcard/decoder_test";

//	static {
//		System.loadLibrary("VideoController");
//	}
//
//	private native long init(int arg);
//
//	private native boolean setVideoSurface(long instance, Surface surface);
//
//	private native void nativeStop(long instance);
//
//	private native void nativeSetYUVDate(long instance, byte[] yuvArray,
//			int width, int height);

	public void init(Surface surface) {
		if (surface == null) {
			return;
		}
		mSurface = surface;
		if (isGetYuvBuffer) {
//			mInstance = init(0);
//			setVideoSurface(mInstance, surface);
		}
		mPreviewData = new LinkedBlockingQueue<byte[]>();

	}

	public void putData(byte[] bytes) {
		mPreviewData.add(bytes);
	}

	public void clear() {
		mPreviewData.clear();
		hasFindSps = false;
	}

	public byte[] getData() {

		try {
			return mPreviewData.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private boolean setupDecoder(Surface surface) {
		Log.d("sss", "setupDecoder");
		// MediaFormat mediaFormat = MediaFormat.createVideoFormat(MIME_TYPE,
		// width, height);
		MediaFormat mediaFormat = MediaFormat.createVideoFormat(MIME_TYPE, 640,
				480);
		// modify hphuang
		if (isGetYuvBuffer) {
			MediaCodecInfo codecInfo = ColorSpaceManager
					.selectCodec("video/avc");
			int colorFormat = ColorSpaceManager.selectColorFormat(codecInfo,
					"video/avc");
			mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, colorFormat);
			Log.d("sss", "colorFormat=" + colorFormat);
		}
		mediaFormat.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, MAX_INPUT_SIZE);
		// // mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, mBitRate);
		// mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, FRAME_RATE);
		// mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 10);

		// end
		try {
			mMediaDecoder = MediaCodec.createDecoderByType(MIME_TYPE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (mMediaDecoder == null) {
			if (VERBOSE)
				Log.e(TAG, "createDecoderByType fail!");
			return false;
		}

		try {
			if (isGetYuvBuffer) {
				mMediaDecoder.configure(mediaFormat, null, null, 0);
			} else {
				mMediaDecoder.configure(mediaFormat, mSurface, null, 0);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}

		return true;
	}

	public void startDecoder() {
		mDecoderThread = new Thread() {
			@Override
			public void run() {
				super.run();
				try {
					if (mMediaDecoder == null) {
						setupDecoder(mSurface);
					}
					mFlag = true;
					mMediaDecoder.start();
					offerDecoder();
				} finally {
					stopDecodeInner();
				}
			}
		};
		mDecoderThread.start();
	}

	private void offerDecoder() {
		Log.d(TAG, "offerDecoder ");
		FileOutputStream outputStream = null;
		try {
			ByteBuffer[] inputBuffers = mMediaDecoder.getInputBuffers();
			byte[] spsDate = null;

			if (DEBUG_SAVE_FILE) {
				String fileName = DEBUG_FILE_NAME_BASE + ".yuv";
				try {
					outputStream = new FileOutputStream(fileName);
					Log.d(TAG, "encoded output will be saved as " + fileName);
				} catch (IOException ioe) {
					Log.w(TAG, "Unable to create debug output file " + fileName);
					throw new RuntimeException(ioe);
				}
			}

			while (mFlag) {
				// Log.d("sss","offerDecoder");
				if (!hasFindSps) {
					byte[] input = getData();
					if ((input[4] & 0x1f) == 7 || (input[4] & 0x1f) == 8) {
						spsDate = input;
						hasFindSps = true;
					} else {
						continue;
					}
				}

				int inputBufferIndex = mMediaDecoder.dequeueInputBuffer(1000);
				// Log.d(TAG, "inputBufferIndex= "+inputBufferIndex);
				if (inputBufferIndex >= 0) {
					ByteBuffer inputBuffer = inputBuffers[inputBufferIndex];
					inputBuffer.clear();
					byte[] input = null;
					if (spsDate != null) {
						input = spsDate;
						spsDate = null;
					} else {
						input = getData();
					}
					if (input == null) {
						continue;
					}
					int length = input.length;
					inputBuffer.put(input, 0, length);
					mMediaDecoder.queueInputBuffer(inputBufferIndex, 0, length,
							0, 0);
				}

				BufferInfo bufferInfo = new BufferInfo();

				int outputBufferIndex = mMediaDecoder.dequeueOutputBuffer(
						(BufferInfo) bufferInfo, 1000);
				//Log.d(TAG, "outputBufferIndex= " + outputBufferIndex);
				switch (outputBufferIndex) {
				case MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED:
					mMediaDecoder.getOutputBuffers();
					break;
				case MediaCodec.INFO_OUTPUT_FORMAT_CHANGED:
					break;
				case MediaCodec.INFO_TRY_AGAIN_LATER:
					break;
				default:
					if (isGetYuvBuffer) {
//						ByteBuffer encodedData = mMediaDecoder
//								.getOutputBuffers()[outputBufferIndex];
//						encodedData.position(bufferInfo.offset);
//						encodedData.limit(bufferInfo.offset + bufferInfo.size);
//						byte[] data = new byte[bufferInfo.size];
//						encodedData.get(data);
//						MediaFormat format = mMediaDecoder.getOutputFormat();
//						int width = format.getInteger(MediaFormat.KEY_WIDTH);
//						int height = format.getInteger(MediaFormat.KEY_HEIGHT);
//						byte[] dst = ColorSpaceManager.SemiPlanartoNV21(data,
//								width, height);
//						Log.d("sss","width="+width+"   height="+height+"     dst.length="+dst.length);
//						// the data is the buffer
//						nativeSetYUVDate(mInstance, dst, width, height);
//						if (outputStream != null) {
//							try {
//								outputStream.write(dst);
//								Log.d(TAG, "write data--------------");
//							} catch (IOException ioe) {
//								Log.w(TAG, "failed writing debug data to file");
//								throw new RuntimeException(ioe);
//							}
//						}
					}
					try {
						mMediaDecoder.releaseOutputBuffer(outputBufferIndex,
								true);
					} catch (Exception IllegalStateException) {
						return;
					}

					break;
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
			Log.d("sss", "what crash?!!!!!!!");
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException ioe) {
					Log.w(TAG, "failed closing debug file");
					throw new RuntimeException(ioe);
				}
			}
		}

	}

	public void stopDecode() {
		mFlag = false;
		mDecoderThread.interrupt();
		try {
			mDecoderThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mDecoderThread = null;
		if (isGetYuvBuffer) {
//			nativeStop(mInstance);
//			mInstance = -1;
		}
	}

	public void stopDecodeInner() {
		try {
			if (mMediaDecoder != null) {
				mMediaDecoder.stop();
				mMediaDecoder.release();
				mMediaDecoder = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
