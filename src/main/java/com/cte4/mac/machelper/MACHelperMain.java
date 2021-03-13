package com.cte4.mac.machelper;

import java.lang.instrument.Instrumentation;

import com.cte4.mac.machelper.utils.MacCargo;

public class MACHelperMain {
    public static boolean firstTime = true;

    public static void main(String[] args) {
        System.out.println("prompt:TODO");
    }

    public static void premain(String args, Instrumentation inst) throws Exception {
        System.out.println("::install helpers start::");
        synchronized (MACHelperMain.class) {
            if (firstTime) {
                firstTime = false;
            } else {
                System.out.println("::skip helpers installation::");
                return;
            }
        }
        MacCargo.build();
        System.out.println("::install helpers complete::");
    }

    public static void agentmain(String args, Instrumentation inst) throws Exception {
        premain(args, inst);
    }
}
