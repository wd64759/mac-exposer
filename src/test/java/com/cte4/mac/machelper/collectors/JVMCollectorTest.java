package com.cte4.mac.machelper.collectors;

public class JVMCollectorTest {
    public static void main(String[] args) throws Exception {
        JVMCollector.start();
        System.in.read();
        JVMCollector.stop();
    }
}
