package com.bigwen.main;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

public class WPApplication extends Application {

    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        print("onCreate " + android.os.Process.myPid());
        application = this;

        if (getCurProcessName(this).contains(":cocos")) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String processName;
    public boolean isMainProcess() {
        if (TextUtils.isEmpty(processName)) {
            processName = getCurProcessName(this);
        }

        if (!TextUtils.isEmpty(processName) && processName.equals(getPackageName())) {
            return true;
        }
        return false;
    }

    public static Application getApplication() {
        return application;
    }

    public static void print(String msg) {
        Log.i("WPApplication", "print: " + msg);
    }

    public static String getCurProcessName(Context context) {
        try {
            int pid = android.os.Process.myPid();
            ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
                WPApplication.print("getCurProcessName: " + appProcess.processName);
                if (appProcess.pid == pid) {
                    return appProcess.processName;
                }
            }
        } catch (Exception e) {
            // ignore
        }
        return "";
    }
}
