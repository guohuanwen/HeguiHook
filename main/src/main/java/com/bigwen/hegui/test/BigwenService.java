package com.bigwen.hegui.test;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;

import java.util.List;

public class BigwenService extends Service {

    String TAG = "BigwenService";

    @Override
    public void onCreate() {
        super.onCreate();
        PackageManager pm = getApplication().getPackageManager();
        List<PackageInfo> installedPkgs = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (PackageInfo packageInfo: installedPkgs) {
            Log.i(TAG, "onCreate: "+ packageInfo.packageName);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
