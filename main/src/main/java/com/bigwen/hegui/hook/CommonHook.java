package com.bigwen.hegui.hook;

import android.util.Log;
import de.robv.android.xposed.XC_MethodHook;

public class CommonHook extends XC_MethodHook {

    private static final String TAG = "XposedBridge";

    @Override
    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
        super.beforeHookedMethod(param);
        Log.i(TAG, "invoke method " + param.thisObject.getClass().getName() + "." + param.method.getName());
    }

    @Override
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        super.afterHookedMethod(param);
        String[] paramList = new String[param.args.length];
        if (param.args.length > 0) {
            for (int i = 0; i < param.args.length; i++) {
                paramList[i] = param.args[i].toString();
            }
        }
        InvokeInfo info = new InvokeInfo(
                param.thisObject.getClass().getName(),
                param.method.getName(),
                paramList,
                new Throwable().getStackTrace());
        HookUtil.sendBroadcast(info);
//        dump(param.thisObject.getClass().getName() + "." + param.method.getName());
    }

    public static void dump(String msg){
        Throwable ex = new Throwable();
        StackTraceElement[] stackElements = ex.getStackTrace();
        if (stackElements != null) {
            for (int i= 0; i < stackElements.length; i++) {
                StringBuilder sb=new StringBuilder("[方法栈调用]");
                sb.append(i);
                Log.e(TAG, "[Dump Stack]"+i+": "+ stackElements[i].getClassName()
                        +"----"+stackElements[i].getFileName()
                        +"----" + stackElements[i].getLineNumber()
                        +"----" +stackElements[i].getMethodName());
            }
        }
        Log.e(TAG, "\n");
    }
}
