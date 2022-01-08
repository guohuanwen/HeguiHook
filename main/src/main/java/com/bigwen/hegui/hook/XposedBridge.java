package com.bigwen.hegui.hook;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;
import static de.robv.android.xposed.XposedHelpers.findClassIfExists;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import java.net.NetworkInterface;

public class XposedBridge implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        try {
            findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Context context = (Context) param.args[0];
                    HookUtil.setApplication(context);
                    boolean trtc = findClassIfExists("com.tencent.txcopyrightedmedia.TXCopyrightedMedia", lpparam.classLoader) != null;
                    boolean zego = findClassIfExists("im.zego.zegoexpress.ZegoCopyrightedMusic", lpparam.classLoader) != null;
                    String msg = "zego " + zego + "\ntrtc " + trtc;
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                }
            });
        } catch(Exception e) {
            e.printStackTrace();
        }

        if (true) {
            return;
        }

        findAndHookMethod(WifiInfo.class, "getMacAddress", new CommonHook());
        findAndHookMethod(NetworkInterface.class, "getHardwareAddress", new CommonHook());
        findAndHookMethod(TelephonyManager.class, "getDeviceId", new CommonHook());
        findAndHookMethod(TelephonyManager.class, "getImei", new CommonHook());
        findAndHookMethod(TelephonyManager.class, "getSubscriberId", int.class, new CommonHook());

        findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader, "getInstalledApplications", int.class, new CommonHook());
        findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader, "getInstalledApplicationsAsUser", int.class, int.class, new CommonHook());
        findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader, "getInstalledPackages", int.class, new CommonHook());
        findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader, "getInstalledPackagesAsUser", int.class, int.class, new CommonHook());
        findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader, "isInstantApp", new CommonHook());
        findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader, "getInstantApps", new CommonHook());

        findAndHookMethod("android.app.ActivityManager", lpparam.classLoader, "getRunningAppProcesses", new CommonHook());
        findAndHookMethod(Settings.Secure.class, "getString", ContentResolver.class, String.class, new CommonHook());
        findAndHookMethod(Settings.Secure.class, "getStringForUser", ContentResolver.class, String.class, int.class, new CommonHook());
        findAndHookMethod(LocationManager.class, "getLastKnownLocation", String.class, new CommonHook());

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
