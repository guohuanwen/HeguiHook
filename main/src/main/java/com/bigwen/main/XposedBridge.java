package com.bigwen.main;

import android.content.ContentResolver;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
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
//        XposedHelpers.findAndHookMethod(ContextWrapper.class, "getPackageManager", new CommonHook());
        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader,"getInstalledApplications", int.class, new CommonHook());
        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader,"getInstalledApplicationsAsUser", int.class, int.class, new CommonHook());
        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader,"getInstalledPackages", int.class, new CommonHook());
        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader,"getInstalledPackagesAsUser", int.class, int.class, new CommonHook());
        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader,"isInstantApp", new CommonHook());
        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader,"getPackageInfo", String.class, int.class, new CommonHook());

        Class fclass1 = XposedHelpers.findClass("android.content.pm.VersionedPackage", lpparam.classLoader);
        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader,"getPackageInfo", fclass1, int.class, new CommonHook());

        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader,"getPackageInfoAsUser", String.class, int.class, int.class, new CommonHook());
        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader,"getApplicationInfo", String.class, int.class, new CommonHook());
        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader,"getApplicationInfoAsUser", String.class, int.class, int.class, new CommonHook());

        //固定格式
//        XposedHelpers.findAndHookMethod(
//                "android.app.ContextImpl", // 需要hook的方法所在类的完整类名
//                lpparam.classLoader,                            // 类加载器，固定这么写就行了
//                "getPackageManager",                     // 需要hook的方法名
//                new DumpMethodHook() {
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param) {
//                        Log.i(TAG, "beforeHookedMethod: getPackageManager");
//                    }
//                }
//        );

//
//        XposedHelpers.findAndHookMethod("android.app.ContextImpl", lpparam.classLoader,"getPackageManager", new CommonHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.beforeHookedMethod(param);
//                Log.i(TAG, "beforeHookedMethod: getPackageManager");
//            }
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//                Log.i(TAG, "afterHookedMethod: getPackageManager");
//            }
//        });



        //固定格式
        XposedHelpers.findAndHookMethod(
                android.telephony.TelephonyManager.class.getName(), // 需要hook的方法所在类的完整类名
                lpparam.classLoader,                            // 类加载器，固定这么写就行了
                "getDeviceId",                     // 需要hook的方法名
                new DumpMethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        Log.i(TAG, "beforeHookedMethod: getDeviceId");
                    }
                }
        );
        XposedHelpers.findAndHookMethod(
                android.telephony.TelephonyManager.class.getName(),
                lpparam.classLoader,
                "getDeviceId",
                int.class,
                new DumpMethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        Log.i(TAG, "beforeHookedMethod: getDeviceId");
                    }
                }
        );

        XposedHelpers.findAndHookMethod(
                android.telephony.TelephonyManager.class.getName(),
                lpparam.classLoader,
                "getSubscriberId",
                int.class,
                new DumpMethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        Log.i(TAG, "beforeHookedMethod: getSubscriberId");
                    }
                }
        );

        XposedHelpers.findAndHookMethod(
                android.telephony.TelephonyManager.class.getName(),
                lpparam.classLoader,
                "getImei",
                new DumpMethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        Log.i(TAG, "beforeHookedMethod: getImei");
                    }
                }
        );

        XposedHelpers.findAndHookMethod(
                android.telephony.TelephonyManager.class.getName(),
                lpparam.classLoader,
                "getImei",
                int.class,
                new DumpMethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        Log.i(TAG, "beforeHookedMethod: getImei");
                    }
                }
        );

        XposedHelpers.findAndHookMethod(
                android.net.wifi.WifiInfo.class.getName(),
                lpparam.classLoader,
                "getMacAddress",
                new DumpMethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        Log.i(TAG, "beforeHookedMethod: getMacAddress");
                    }
                }
        );

        XposedHelpers.findAndHookMethod(
                java.net.NetworkInterface.class.getName(),
                lpparam.classLoader,
                "getHardwareAddress",
                new DumpMethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        Log.i(TAG, "beforeHookedMethod: getHardwareAddress");
                    }
                }
        );

        XposedHelpers.findAndHookMethod(
                android.provider.Settings.Secure.class.getName(),
                lpparam.classLoader,
                "getString",
                ContentResolver.class,
                String.class,
                new DumpMethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        Log.i(TAG, "beforeHookedMethod: getString");
                    }
                }
        );

        XposedHelpers.findAndHookMethod(
                LocationManager.class.getName(),
                lpparam.classLoader,
                "getLastKnownLocation",
                String.class,
                new DumpMethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        Log.i(TAG, lpparam.packageName + "调用getLastKnownLocation获取了GPS地址");
                    }
                }
        );

//        XposedHelpers.findAndHookMethod(
//                LocationManager.class.getName(),
//                lpparam.classLoader,
//                "requestLocationUpdates",
//                String.class,
//                new DumpMethodHook() {
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param) {
//                        Log.i(TAG, lpparam.packageName + "调用requestLocationUpdates获取了GPS地址");
//                    }
//                }
//        );
    }
}
