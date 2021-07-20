package com.bigwen.main;

import android.util.Log;
import de.robv.android.xposed.XC_MethodHook;

public class CommonHook extends XC_MethodHook {

    private String TAG = "XposedBridge";

    @Override
    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
        super.beforeHookedMethod(param);
        Log.i(TAG, "beforeHookedMethod: " + param.method.getName());
        for (Object obj: param.args){
            Log.i(TAG, "beforeHookedMethod: param " + obj);
        }
    }

    @Override
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        super.afterHookedMethod(param);
//        DumpMethodHook.dump2();
        Log.i(TAG, "afterHookedMethod: " + param.method.getName());
    }
}
