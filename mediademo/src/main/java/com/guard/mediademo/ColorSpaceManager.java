/****************************************************************************
 *
 * FILENAME:        com.encode.soft.ColorSpaceManager.java
 *
 * LAST REVISION:   $Revision: 1.0
 * LAST MODIFIED:   $Date: 2013/01/22 02:14:08 2015-7-20
 *
 *
 * vi: set ts=4:
 *
 * Copyright (c) 2009-2013 by Grandstream Networks, Inc.
 * All rights reserved.
 *
 * This material is proprietary to Grandstream Networks, Inc. and,
 * in addition to the above mentioned Copyright, may be
 * subject to protection under other intellectual property
 * regimes, including patents, trade secrets, designs and/or
 * trademarks.
 *
 * Any use of this material for any purpose, except with an
 * express license from Grandstream Networks, Inc. is strictly
 * prohibited.
 *
 ***************************************************************************/
package com.guard.mediademo;

import android.util.Log;

/**
YV12
Y01  Y02  Y03  Y04  Y05  Y06
Y07  Y08  Y09  Y10  Y11  Y12
Y13  Y14  Y15  Y16  Y17  Y18
Y19  Y20  Y21  Y22  Y23  Y24
U01 U02 U03  U04 U05 U06 
V01  V02 V03   V04 V05  V06


YUV420Planar
Y01  Y02  Y03  Y04  Y05  Y06
Y07  Y08  Y09  Y10  Y11  Y12
Y13  Y14  Y15  Y16  Y17  Y18
Y19  Y20  Y21  Y22  Y23  Y24
V01  V02 V03   V04 V05  V06
U01 U02 U03  U04 U05 U06 
  
NV21	 
Y01  Y02  Y03  Y04  Y05  Y06
Y07  Y08  Y09  Y10  Y11  Y12
Y13  Y14  Y15  Y16  Y17  Y18
Y19  Y20  Y21  Y22  Y23  Y24
U01  V01 U02 V02  U03  V03
U04  V04 U05 V05  U06  V06 


* */
public class ColorSpaceManager {
	private static final String TAG = "ColorSpaceManager";
	
	public static byte[] YV12toNV21(final byte[] input,
			final byte[] output, final int width, final int height){
		final int frameSize = width * height;
		System.arraycopy(input, 0, output, 0, frameSize); // Y
		final int qFrameSize = frameSize / 4;
		for(int i=0;i<qFrameSize;i++){
			output[frameSize+i*2]=input[frameSize+i];
			output[frameSize+i*2+1]=input[frameSize+qFrameSize+i];
		}
		
		return output;
	}

	public static byte[] YV12toYUV420PackedSemiPlanar(final byte[] input,
			final byte[] output, final int width, final int height) {
		/*
		 * COLOR_TI_FormatYUV420PackedSemiPlanar is NV12 We convert by putting
		 * the corresponding U and V bytes together (interleaved).
		 */
		final int frameSize = width * height;
		final int qFrameSize = frameSize / 4;

		System.arraycopy(input, 0, output, 0, frameSize); // Y

		for (int i = 0; i < qFrameSize; i++) {
			output[frameSize + i * 2] = input[frameSize + i + qFrameSize]; // Cb
																			// (U)
			output[frameSize + i * 2 + 1] = input[frameSize + i]; // Cr (V)
		}
		return output;
	}

	public static byte[] YV12toYUV420Planar(byte[] input, byte[] output,
			int width, int height) {
		/*
		 * COLOR_FormatYUV420Planar is I420 which is like YV12, but with U and V
		 * reversed. So we just have to reverse U and V.
		 */
		final int frameSize = width * height;
		final int qFrameSize = frameSize / 4;

		System.arraycopy(input, 0, output, 0, frameSize); // Y
		System.arraycopy(input, frameSize, output, frameSize + qFrameSize,
				qFrameSize); // Cr (V)
		System.arraycopy(input, frameSize + qFrameSize, output, frameSize,
				qFrameSize); // Cb (U)
		return output;
	}

	public static byte[] NV21toYUV420Planar(byte[] input, byte[] output,
			int width, int height) {
		final int frameSize = width * height;
		final int qFrameSize = frameSize / 4;

		System.arraycopy(input, 0, output, 0, frameSize); // Y

		byte v, u;

		for (int i = 0; i < qFrameSize; i++) {
			v = input[frameSize + i * 2];
			u = input[frameSize + i * 2 + 1];

			output[frameSize + i + qFrameSize] = v;
			output[frameSize + i] = u;
		}

		return output;
	}

	public static byte[] NV21toYUV420SemiPlanar(byte[] input, byte[] output,
			int width, int height) {
		final int frameSize = width * height;
		final int qFrameSize = frameSize / 4;

		System.arraycopy(input, 0, output, 0, frameSize); // Y

		byte v, u;

		for (int i = 0; i < qFrameSize; i++) {
			v = input[frameSize + i * 2];
			u = input[frameSize + i * 2 + 1];

			output[frameSize + i * 2] = u;
			output[frameSize + i * 2 + 1] = v;
		}

		return output;
	}
	
	public static byte[] rotateYUV420Degree180(byte[] data, int imageWidth,
			int imageHeight) {
		byte[] yuv = new byte[imageWidth * imageHeight * 3 / 2];
		int i = 0;
		int count = 0;
		for (i = imageWidth * imageHeight - 1; i >= 0; i--) {
			yuv[count] = data[i];
			count++;
		}
		i = imageWidth * imageHeight * 3 / 2 - 1;
		for (i = imageWidth * imageHeight * 3 / 2 - 1; i >= imageWidth
				* imageHeight; i -= 2) {
			yuv[count++] = data[i - 1];
			yuv[count++] = data[i];
		}
		return yuv;
	}
	
	public static void NV21rotate180toYUV420SemiPlanar(byte[] input,
			byte[] output, int imageWidth, int imageHeight) {
		// Rotate the Y luma
		int i = 0;
		int count = 0;
		for (i = imageWidth * imageHeight - 1; i >= 0; i--) {
			output[count] = input[i];
			count++;
		}

		i = imageWidth * imageHeight * 3 / 2 - 1;
		for (i = imageWidth * imageHeight * 3 / 2 - 1; i >= imageWidth
				* imageHeight; i -= 2) {
			output[count++] = input[i];
			output[count++] = input[i-1];
		}
	}
	
	public static void NV21rotate180toYUV420Planar(byte[] input,
			byte[] output, int imageWidth, int imageHeight) {
		// Rotate the Y luma
		int i = 0;
		int count = 0;
		for (i = imageWidth * imageHeight - 1; i >= 0; i--) {
			output[count] = input[i];
			count++;
		}

		i = imageWidth * imageHeight * 3 / 2 - 1;
		int qframe=imageWidth * imageHeight /4;
		for (i = imageWidth * imageHeight * 3 / 2 - 1; i >= imageWidth
				* imageHeight; i -= 2) {
			output[count+qframe] = input[i-1];
			output[count] = input[i];
			count++;
		}
	}

	public static void NV21rotate270toYUV420SemiPlanar(byte[] input,
			byte[] output, int width, int height) {
		int tempHeight = height;
		int tempWidht = width;
		// Rotate the Y luma
		int i = 0;
		for (int x = tempWidht - 1; x >= 0; x--) {
			for (int y = 0; y < tempHeight; y++) {
				output[i] = input[y * tempWidht + x];
				i++;
			}
		}

		i = tempWidht * tempHeight - 1;
		int basePos = tempWidht * tempHeight;
		int yMax = tempHeight >> 1;

		for (int x = tempWidht - 1; x > 0; x = x - 2) {
			for (int y = 0; y < yMax; y++) {
				output[i] = input[basePos + (y * tempWidht) + (x - 1)];
				i++;
				output[i] = input[basePos + (y * tempWidht) + x];
				i++;
			}
		}
	}

	public static void NV21rotate90toYUV420SemiPlanar(byte[] input,
			byte[] output, int width, int height) {
		int tempHeight = height;
		int tempWidht = width;
		// Rotate the Y luma
		int i = 0;
		for (int x = 0; x < tempWidht; x++) {
			for (int y = tempHeight - 1; y >= 0; y--) {
				output[i] = input[y * tempWidht + x];
				i++;
			}
		}
		i = tempWidht * tempHeight * 3 / 2 - 1;

		int basePos = tempWidht * tempHeight;
		int yMax = tempHeight >> 1;
		for (int x = tempWidht - 1; x > 0; x = x - 2) {
			for (int y = 0; y < yMax; y++) {
				output[i] = input[basePos + (y * tempWidht) + (x - 1)];
				i--;
				output[i] = input[basePos + (y * tempWidht) + x];
				i--;
			}
		}
	}

	public static void NV21rotate90toYUV420Planar(byte[] input, byte[] output,
			int width, int height) {
		int tempHeight = height;
		int tempWidht = width;
		// Rotate the Y luma
		int i = 0;
		for (int x = 0; x < tempWidht; x++) {
			for (int y = tempHeight - 1; y >= 0; y--) {
				output[i] = input[y * tempWidht + x];
				i++;
			}
		}
		i = tempHeight * tempWidht / 2 - 1;
		int basePos = tempWidht * tempHeight;
		int t = 0;
		int yMax = tempHeight >> 1;
		for (int x = tempWidht - 1; x > 0; x = x - 2) {
			for (int y = 0; y < yMax; y++) {
				output[basePos + i / 2 - t] = input[basePos + (y * tempWidht)
						+ x];

				output[basePos + i - t] = input[basePos + (y * tempWidht)
						+ (x - 1)];
				t++;
			}
		}
	}

	public static void NV21rotate270toYUV420Planar(byte[] input, byte[] output,
			int width, int height) {
		int tempHeight = height;
		int tempWidht = width;
		// Rotate the Y luma

		int i = 0;
		for (int x = tempWidht - 1; x >= 0; x--) {
			for (int y = 0; y < tempHeight; y++) {
				output[i] = input[y * tempWidht + x];
				i++;
			}
		}

		i = tempHeight * tempWidht / 2 - 1;
		int basePos = tempWidht * tempHeight;
		int t = 0;
		int yMax = tempHeight >> 1;
		for (int x = tempWidht - 1; x > 0; x = x - 2) {
			for (int y = 0; y < yMax; y++) {
				output[basePos + t] = input[basePos + (y * tempWidht) + x];

				output[basePos + i / 2 + t] = input[basePos + (y * tempWidht)
						+ (x - 1)];
				t++;
			}
		}
	}
	


	public static boolean iSMCSupport() {
		Log.i(TAG, "sdk----" + android.os.Build.VERSION.SDK_INT);
		return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN;
	}
}
