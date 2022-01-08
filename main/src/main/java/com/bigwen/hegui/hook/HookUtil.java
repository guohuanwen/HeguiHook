package com.bigwen.hegui.hook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

public class HookUtil {

    private static String TAG = "XposedBridge";
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    public static final String BROADCAST_ACTION = "com.bigwen.hegui";
    public static final String EXTRA_KEY = "extra";
    public static final String STRING_KEY = "string";
    private static Gson gson = new Gson();

    public static void setApplication(Context context) {
        print("setup context " + context.getPackageName());
        HookUtil.context = context;
    }

    public static void sendBroadcast(InvokeInfo info) {
        if (context == null) return;
        Intent intent = new Intent();
        intent.setAction(BROADCAST_ACTION);
        intent.putExtra(EXTRA_KEY, info);
        context.sendBroadcast(intent);
    }

    public static void sendBroadcast(String info) {
        if (context == null) return;
        Intent intent = new Intent();
        intent.setAction(BROADCAST_ACTION);
        intent.putExtra(STRING_KEY, info);
        context.sendBroadcast(intent);
    }

    public static String toJson(Object o) {
        return gson.toJson(o);
    }

    public static void print(String msg) {
        Log.i(TAG, msg);
    }

    public static void showToast(String msg) {
        if (context != null) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
