package com.zzxzzg.alihotfixtest;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.taobao.hotfix.HotFixManager;
import com.taobao.hotfix.NewPatchListener;

/**
 * Created by yxwang on 16/10/10.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        HotFixManager.getInstance().initialize(this, getVersion(), "74169-1", new NewPatchListener() {
            @Override
            public void handlePatch(int i) {

            }
        });
        HotFixManager.getInstance().queryNewHotPatch();

    }

    public String getVersion() {
             try {
                     PackageManager manager = this.getPackageManager();
                     PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
                     String version = info.versionName;
                     return version;
                 } catch (Exception e) {
                     e.printStackTrace();
                     return "null";
                 }
         }
}
