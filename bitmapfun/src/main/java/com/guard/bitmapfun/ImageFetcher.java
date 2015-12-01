/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.guard.bitmapfun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Semaphore;



/**
 * A simple subclass of {@link ImageResizer} that fetches and resizes images fetched from a URL.
 */
public class ImageFetcher extends ImageResizer {
    private static final String TAG = "ImageFetcher";
    private static final int HTTP_CACHE_SIZE = 100 * 1024 * 1024; // 10MB
    private static final String HTTP_CACHE_DIR = "http";
    private static final int IO_BUFFER_SIZE = 8 * 1024;

    private DiskLruCache mHttpDiskCache;
    private File mHttpCacheDir;
    private boolean mHttpDiskCacheStarting = true;
    private final Object mHttpDiskCacheLock = new Object();
    private static final int DISK_CACHE_INDEX = 0;

    /**
     * Initialize providing a target image width and height for the processing images.
     *
     * @param context
     * @param imageWidth
     * @param imageHeight
     */
    public ImageFetcher(Context context, int imageWidth, int imageHeight) {
        super(context, imageWidth, imageHeight);
        init(context);
    }

    /**
     * Initialize providing a single target image size (used for both width and height);
     *
     * @param context
     * @param imageSize
     */
    public ImageFetcher(Context context, int imageSize) {
        super(context, imageSize);
        init(context);
    }
    /**
     * 初始化
     * @param context
     */
    private void init(Context context) {
        //检查网络状态，如果网络未链接，进行提示
        checkConnection(context);
        //创建一个硬盘缓存文件，制定后缀名HTTP_CACHE_DIR
        mHttpCacheDir = ImageCache.getDiskCacheDir(context, HTTP_CACHE_DIR);
    }

    @Override
    protected void initDiskCacheInternal() {
        super.initDiskCacheInternal();
        initHttpDiskCache();
    }

    private void initHttpDiskCache() {
        if (mHttpCacheDir!=null&&!mHttpCacheDir.exists()) {
            mHttpCacheDir.mkdirs();
        }
        synchronized (mHttpDiskCacheLock) {
            if (ImageCache.getUsableSpace(mHttpCacheDir) > HTTP_CACHE_SIZE) {
                try {
                    mHttpDiskCache = DiskLruCache.open(mHttpCacheDir, 1, 1, HTTP_CACHE_SIZE);
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, "HTTP cache initialized");
                    }
                } catch (IOException e) {
                    mHttpDiskCache = null;
                }
            }
            mHttpDiskCacheStarting = false;
            mHttpDiskCacheLock.notifyAll();
        }
    }

    @Override
    protected void clearCacheInternal() {
        super.clearCacheInternal();
        synchronized (mHttpDiskCacheLock) {
            if (mHttpDiskCache != null && !mHttpDiskCache.isClosed()) {
                try {
                    mHttpDiskCache.delete();
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, "HTTP cache cleared");
                    }
                } catch (IOException e) {
                    Log.e(TAG, "clearCacheInternal - " + e);
                }
                mHttpDiskCache = null;
                mHttpDiskCacheStarting = true;
                initHttpDiskCache();
            }
        }
    }

    @Override
    protected void flushCacheInternal() {
        super.flushCacheInternal();
        synchronized (mHttpDiskCacheLock) {
            if (mHttpDiskCache != null) {
                try {
                    mHttpDiskCache.flush();
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, "HTTP cache flushed");
                    }
                } catch (IOException e) {
                    Log.e(TAG, "flush - " + e);
                }
            }
        }
    }

    @Override
    protected void closeCacheInternal() {
        super.closeCacheInternal();
        synchronized (mHttpDiskCacheLock) {
            if (mHttpDiskCache != null) {
                try {
                    if (!mHttpDiskCache.isClosed()) {
                        mHttpDiskCache.close();
                        mHttpDiskCache = null;
                        if (BuildConfig.DEBUG) {
                            Log.d(TAG, "HTTP cache closed");
                        }
                    }
                } catch (IOException e) {
                    Log.e(TAG, "closeCacheInternal - " + e);
                }
            }
        }
    }

    /**
    * Simple network connection check.
    *检查网络状态，如果网络未链接，进行提示
    * @param context
    */
    private void checkConnection(Context context) {
        final ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
            Toast.makeText(context, R.string.no_network_connection_toast, Toast.LENGTH_LONG).show();
            Log.e(TAG, "checkConnection - no connection found");
        }
    }


    public Bitmap getFromDiskCache(String data,int reqWidth,int reqHeight){
        final String key = ImageCache.hashKeyForDisk(data);
        FileDescriptor fileDescriptor = null;
        FileInputStream fileInputStream = null;
        DiskLruCache.Snapshot snapshot;
        synchronized (mHttpDiskCacheLock) {
            // Wait for disk cache to initialize
            while (mHttpDiskCacheStarting) {
                try {
                    mHttpDiskCacheLock.wait();
                } catch (InterruptedException e) {
                }
            }
            if (mHttpDiskCache != null) {
                try {
                    snapshot = mHttpDiskCache.get(key);
                    if (snapshot != null) {
                        Bitmap bitmap=null;
                        fileInputStream =
                                (FileInputStream) snapshot.getInputStream(DISK_CACHE_INDEX);
                        fileDescriptor = fileInputStream.getFD();
                        if (fileDescriptor != null) {
                            bitmap = decodeSampledBitmapFromDescriptor(fileDescriptor, reqWidth,
                                    reqHeight, getImageCache());
                        }
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e) {
                            }
                        }
                        return bitmap;
                    }
                }catch (IOException e){

                }finally {
                    if(fileInputStream!=null){
                        try {
                            fileInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

    public void saveInDiskCache(final String data,final byte[] bitmap){
        Thread thread=new Thread(){
            @Override
            public void run() {
                super.run();
                synchronized (mHttpDiskCacheLock) {
                    DiskLruCache.Snapshot snapshot;
                    final String key = ImageCache.hashKeyForDisk(data);
                    // Wait for disk cache to initialize
                    while (mHttpDiskCacheStarting) {
                        try {
                            mHttpDiskCacheLock.wait();
                        } catch (InterruptedException e) {
                        }
                    }
                    if (mHttpDiskCache != null) {
                        OutputStream out=null;
                        try {
                            snapshot = mHttpDiskCache.get(key);
                            if (snapshot == null) {
                                final DiskLruCache.Editor editor = mHttpDiskCache.edit(key);
                                out=editor.newOutputStream(DISK_CACHE_INDEX);
                                out.write(bitmap);
                                out.flush();
                                editor.commit();
                                out.close();
                            }else{
                                snapshot.close();
                            }
                        }catch (IOException e){
                            try {
                                if (out != null) {
                                    out.close();
                                }
                            } catch (IOException a) {}
                        }
                    }
                }
            }
        };
        thread.start();
    }


    /**
     * The main process method, which will be called by the ImageWorker in the AsyncTask background
     * thread.
     *
     * @param data The data to load the bitmap, in this case, a regular http URL
     * @return The downloaded and resized bitmap
     */
    final static Semaphore semp = new Semaphore(5);

    private Bitmap processBitmap(String data) {
        //使用改方法同样存在一个比较大的隐患,如果同时下载多张大图片,可能会导致OOM的问题.
        Bitmap bitmap=null;
        try {
            semp.acquire();
            int index=data.lastIndexOf("_");
            int height=Integer.parseInt(data.substring(index+1,data.length()));
            data=data.substring(0,index);
            index=data.lastIndexOf("_");
            int width=Integer.parseInt(data.substring(index+1,data.length()));
            data=data.substring(0,index);


            bitmap=getFromDiskCache(data,width,height);
            if(bitmap!=null){
                return bitmap;
            }else{
                ByteArrayOutputStream output=new ByteArrayOutputStream();
                if(downloadUrlToStream(data,output)){
                    Log.d("sss","download image done");
                    byte[] bytes=output.toByteArray();
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    bitmap= decodeSampledBitmapFromByte(bytes, width,
                            height, getImageCache());
                    saveInDiskCache(data,bytes);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semp.release();
        }
        return bitmap;


        //以下是源代码,它会再创建一个DiskLurCache来存储下载下来的图片,最大的问题是同步锁,只能单线程下载
//        if (BuildConfig.DEBUG) {
//            Log.d(TAG, "processBitmap - " + data);
//        }
//
//        FileDescriptor fileDescriptor = null;
//        FileInputStream fileInputStream = null;
//        DiskLruCache.Snapshot snapshot;
//        synchronized (mHttpDiskCacheLock) {
//            // Wait for disk cache to initialize
//            while (mHttpDiskCacheStarting) {
//                try {
//                    mHttpDiskCacheLock.wait();
//                } catch (InterruptedException e) {}
//            }
//            if (mHttpDiskCache != null) {
//                try {
//                    snapshot = mHttpDiskCache.get(key);
//                    if (snapshot == null) {
//                        if (BuildConfig.DEBUG) {
//                            Log.d(TAG, "processBitmap, not found in http cache, downloading...");
//                        }
//                        DiskLruCache.Editor editor = mHttpDiskCache.edit(key);
//                        if (editor != null) {
//                            if (downloadUrlToStream(data,
//                                    editor.newOutputStream(DISK_CACHE_INDEX))) {
//                                editor.commit();
//                            } else {
//                                editor.abort();
//                            }
//                        }
//                        snapshot = mHttpDiskCache.get(key);
//                    }
//                    if (snapshot != null) {
//                        fileInputStream =
//                                (FileInputStream) snapshot.getInputStream(DISK_CACHE_INDEX);
//                        fileDescriptor = fileInputStream.getFD();
//                    }
//                } catch (IOException e) {
//                    Log.e(TAG, "processBitmap - " + e);
//                } catch (IllegalStateException e) {
//                    Log.e(TAG, "processBitmap - " + e);
//                } finally {
//                    if (fileDescriptor == null && fileInputStream != null) {
//                        try {
//                            fileInputStream.close();
//                        } catch (IOException e) {}
//                    }
//                }
//            }
//        }
//
//        Bitmap bitmap = null;
//        if (fileDescriptor != null) {
//            bitmap = decodeSampledBitmapFromDescriptor(fileDescriptor, mImageWidth,
//                    mImageHeight, getImageCache());
//        }
//        if (fileInputStream != null) {
//            try {
//                fileInputStream.close();
//            } catch (IOException e) {}
//        }
//        return bitmap;
    }
/*
 * 从网络获取图片
 * @see com.example.android.bitmapfun.util.ImageResizer#processBitmap(java.lang.Object)
 */
    @Override
    protected Bitmap processBitmap(Object data) {
        return processBitmap(String.valueOf(data));
    }

    /**
     * Download a bitmap from a URL and write the content to an output stream.
     *
     * @param urlString The URL to fetch
     * @return true if successful, false otherwise
     */
    public boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        disableConnectionReuseIfNecessary();
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;

        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            //test for timeout
            urlConnection.setConnectTimeout(3000);
            urlConnection.setReadTimeout(5000);
            in = new BufferedInputStream(urlConnection.getInputStream(), IO_BUFFER_SIZE);
            out = new BufferedOutputStream(outputStream, IO_BUFFER_SIZE);

            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (final IOException e) {
            Log.e(TAG, "Error in downloadBitmap - " + e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {}
        }
        return false;
    }

    /**
     * Workaround for bug pre-Froyo, see here for more info:
     * http://android-developers.blogspot.com/2011/09/androids-http-clients.html
     */
    public static void disableConnectionReuseIfNecessary() {
        // HTTP connection reuse which was buggy pre-froyo
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        }
    }
}
