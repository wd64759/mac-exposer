package com.cte4.mac.machelper.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CallbackRegistry {
    private static Map<String, CallbackInterface> callbackReg = new ConcurrentHashMap<>();
    public static void apply(String reqType, CallbackInterface callbackIns) {
        callbackReg.put(reqType, callbackIns);
    }
}
