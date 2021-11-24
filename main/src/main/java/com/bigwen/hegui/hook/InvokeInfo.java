package com.bigwen.hegui.hook;

import java.io.Serializable;

public class InvokeInfo implements Serializable {

    private String className;
    private String methodName;
    private String[] param;
    private StackTraceElement[] stackTrace;

    public InvokeInfo(String className, String methodName, String[] param, StackTraceElement[] stackTrace) {
        this.className = className;
        this.methodName = methodName;
        this.param = param;
        this.stackTrace = stackTrace;
    }
}
