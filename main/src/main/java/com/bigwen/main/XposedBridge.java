package com.bigwen.main;

import android.content.ContentResolver;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import java.net.NetworkInterface;

public class XposedBridge implements IXposedHookLoadPackage {

    private static final String TAG = "XposedBridge";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        XposedHelpers.findAndHookMethod(WifiInfo.class, "getMacAddress", new CommonHook());
        XposedHelpers.findAndHookMethod(NetworkInterface.class, "getHardwareAddress", new CommonHook());
        XposedHelpers.findAndHookMethod(TelephonyManager.class, "getDeviceId", new CommonHook());
        XposedHelpers.findAndHookMethod(TelephonyManager.class, "getImei", new CommonHook());
        XposedHelpers.findAndHookMethod(TelephonyManager.class, "getSubscriberId", int.class, new CommonHook());

        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader, "getInstalledApplications", int.class, new CommonHook());
        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader, "getInstalledApplicationsAsUser", int.class, int.class, new CommonHook());
        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader, "getInstalledPackages", int.class, new CommonHook());
        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader, "getInstalledPackagesAsUser", int.class, int.class, new CommonHook());
        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader, "isInstantApp", new CommonHook());
        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader, "getInstantApps", new CommonHook());

        XposedHelpers.findAndHookMethod("android.app.ActivityManager", lpparam.classLoader, "getRunningAppProcesses", new CommonHook());
        XposedHelpers.findAndHookMethod(Settings.Secure.class, "getString", ContentResolver.class, String.class, new CommonHook());
        XposedHelpers.findAndHookMethod(Settings.Secure.class, "getStringForUser", ContentResolver.class, String.class, int.class, new CommonHook());
        XposedHelpers.findAndHookMethod(LocationManager.class, "getLastKnownLocation", String.class, new CommonHook());

//        Class intent = XposedHelpers.findClass("android.content.Intent", lpparam.classLoader);
//        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader,"resolveActivity", intent, int.class, new CommonHook());
//        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader,"queryIntentActivities", intent, int.class, new CommonHook());

//        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader,"getPackageInfo", String.class, int.class, new CommonHook());
//        Class fclass1 = XposedHelpers.findClass("android.content.pm.VersionedPackage", lpparam.classLoader);
//        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader,"getPackageInfo", fclass1, int.class, new CommonHook());
//        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader,"getPackageInfoAsUser", String.class, int.class, int.class, new CommonHook());
//        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader,"getApplicationInfo", String.class, int.class, new CommonHook());
//        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader,"getApplicationInfoAsUser", String.class, int.class, int.class, new CommonHook());
//
//        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader, "getInstalledModules", int.class, new CommonHook());
//        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader, "getModuleInfo", String.class, int.class, new CommonHook());
//        XposedHelpers.findAndHookMethod("android.app.ActivityManager", lpparam.classLoader, "getRunningTasks", int.class, new CommonHook());
//        XposedHelpers.findAndHookMethod("android.app.ActivityManager", lpparam.classLoader, "getRecentTasks", int.class, int.class, new CommonHook());

    }
}
