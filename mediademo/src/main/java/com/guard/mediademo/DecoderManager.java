package com.guard.mediademo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaCodec.BufferInfo;
import android.util.Log;
import android.view.Surface;

public class DecoderManager {
	private MediaCodec mMediaDecoder;
	private static final String MIME_TYPE = "video/avc"; // H.264 Advanced Video
	private static final String TAG = "sss";
	private static final int MAX_INPUT_SIZE = 1920 * 1080;
	private static final boolean VERBOSE = false;
	private Surface mSurface;
	private boolean mFlag = true;
	private BlockingQueue<byte[]> mPreviewData;
	private Thread mDecoderThread;
	private boolean hasFindSps = false;

	public void init(Surface surface) {
		if (surface == null) {
			return;
		}
		mSurface = surface;
		mPreviewData = new LinkedBlockingQueue<byte[]>();

	}

	public void putData(byte[] bytes) {
		if (mMediaDecoder == null) {
			return;
		}
		mPreviewData.add(bytes);
	}

	public void clear() {
		mPreviewData.clear();
		hasFindSps = false;
	}
	
	public void restart(){
		stopDecode();
		startDecoder();
	}

	public byte[] getData() {
		try {
			Log.d("sss","getData!!");
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
		// MediaCodecInfo codecInfo = selectCodec("video/avc");
		// int colorFormat = selectColorFormat(codecInfo, "video/avc");
		// mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, colorFormat);
		mediaFormat.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, MAX_INPUT_SIZE);
		// // mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, mBitRate);
		// mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, FRAME_RATE);
		// mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 10);

		// end
		try {
			mMediaDecoder = MediaCodec.createDecoderByType(MIME_TYPE);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (mMediaDecoder == null) {
			if (VERBOSE)
				Log.e(TAG, "createDecoderByType fail!");
			return false;
		}

		try {
			mMediaDecoder.configure(mediaFormat, surface, null, 0);
		} catch (Exception e) {
			// e.printStackTrace();
		}

		return true;
	}

	public synchronized void startDecoder() {
		mDecoderThread = new Thread() {
			@Override
			public void run() {
				super.run();
				try {
					if (mMediaDecoder == null) {
						setupDecoder(mSurface);
					}
					mFlag = true;
					try {
						mMediaDecoder.start();
					} catch (Exception e) {
						e.printStackTrace();
						mMediaDecoder = null;
					}
					//looper for the bug 57845
					while (!offerDecoder() && mFlag) {
						stopDecodeInner();
						setupDecoder(mSurface);
						try {
							mMediaDecoder.start();
						} catch (Exception e) {
							e.printStackTrace();
							mMediaDecoder = null;
						}
						try {
							sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				} finally {
					stopDecodeInner();
				}
			}
		};
		mDecoderThread.start();
	}

	private boolean offerDecoder() {
		Log.d(TAG, "offerDecoder ");
		if (mMediaDecoder == null) {
			Log.d(TAG, "mMediaDecoder == null ");
			return false;
		}
		try {
			ByteBuffer[] inputBuffers = mMediaDecoder.getInputBuffers();
			if (inputBuffers == null) {
				Log.d("sss", "the input buffer is null");
			}
			byte[] spsDate = null;
			while (mFlag) {

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
				// Log.d(TAG, "outputBufferIndex= "+outputBufferIndex);
				switch (outputBufferIndex) {
				case MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED:
					mMediaDecoder.getOutputBuffers();
					break;
				case MediaCodec.INFO_OUTPUT_FORMAT_CHANGED:
					break;
				case MediaCodec.INFO_TRY_AGAIN_LATER:
					break;
				default:
					try {
						mMediaDecoder.releaseOutputBuffer(outputBufferIndex,
								true);
					} catch (Exception IllegalStateException) {
						return false;
					}

					break;
				}
			}
		} catch (Exception IllegalStateException) {
			Log.d("sss","what crash?!!!!!!!");
			return false;
		}
		return true;

	}

	public synchronized void stopDecode() {
		mFlag = false;
		mDecoderThread.interrupt();
		mDecoderThread = null;
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
