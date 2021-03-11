package com.cte4.mac.machelper;

import java.lang.instrument.Instrumentation;

import com.cte4.mac.machelper.collectors.JVMCollector;

public class MACHelperMain {
    public static boolean firstTime = true;

    public static void main(String[] args) {
        System.out.println("prompt:TODO");
    }

    public static void premain(String args, Instrumentation inst) throws Exception {
        synchronized (MACHelperMain.class) {
            if (firstTime) {
                firstTime = false;
            } else {
                System.out.println("<<agent:helpers>> already loaded");
                return;
            }
            // TODO: re-write the train station
            JVMCollector.start();
            System.out.println("<<agent:helpers>> start ...");
        }
    }

    public static void agentmain(String args, Instrumentation inst) throws Exception
    {
        premain(args, inst);
    }
}
