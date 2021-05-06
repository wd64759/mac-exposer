package com.cte4.mac.exposer.collectors;

import com.cte4.mac.exposer.collectors.JVMCollector;

public class JVMCollectorTest {
    public static void main(String[] args) throws Exception {
        JVMCollector.start();
        System.in.read();
        JVMCollector.stop();
    }
}
