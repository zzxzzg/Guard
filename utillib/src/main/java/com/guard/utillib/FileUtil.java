package com.guard.utillib;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by yxwang on 15-12-8.
 */
public final class FileUtil {
    public static File getDiskCacheDir(Context context, String uniqueName) {
        // Check if media is mounted or storage is built-in, if so, try and use external cache dir
        // otherwise use internal cache dir
        String cachePath =null;
        try{
            //Environment.getExternalStorageState()获取SD卡状态，Environment.MEDIA_MOUNTED表示正常挂载
            //isExternalStorageRemovable()表示sd卡是否可拆卸
            cachePath =
                    Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                            !isExternalStorageRemovable() ? getExternalCacheDir(context).getPath() :
                            context.getCacheDir().getPath();
        }catch(Exception e){
            //在模拟器上会出现错误，isExternalStorageRemovable返回true，但是getExternalCacheDir返回null
            cachePath=context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    @TargetApi(9)
    public static boolean isExternalStorageRemovable() {
        if (Utils.hasGingerbread()) {
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }

    /**
     * Get the external app cache directory.
     *
     * @param context The context to use
     * @return The external cache dir
     */
    @TargetApi(8)
    public static File getExternalCacheDir(Context context) {
        if (Utils.hasFroyo()) {
            return context.getExternalCacheDir();
        }

        // Before Froyo we need to construct the external cache dir ourselves
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }
}
