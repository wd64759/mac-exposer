package com.cte4.mac.machelper.collectors;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import com.cte4.mac.machelper.model.ReqEntity;
import com.cte4.mac.machelper.utils.AgentConnector;

public class JVMCollector implements Runnable {

    public boolean endFlag;
    private AgentConnector conn;
    private long sleepTime = 5000;
    private static Object lock = new Object();

    public static JVMCollector runner;

    @Override
    public void run() {
        ThreadMXBean threads = ManagementFactory.getThreadMXBean();
        while (!endFlag && conn != null) {
            ReqEntity threadCount = new ReqEntity("jvm.threads.total.count", "gauge",
                    String.valueOf(threads.getThreadCount()));
            ReqEntity peakCount = new ReqEntity("jvm.threads.peak.count", "gauge",
                    String.valueOf(threads.getPeakThreadCount()));
            ReqEntity daemonCount = new ReqEntity("jvm.threads.daemon.count", "gauge",
                    String.valueOf(threads.getPeakThreadCount()));
            conn.sendMessage(threadCount);
            conn.sendMessage(peakCount);
            conn.sendMessage(daemonCount);
            try {
                synchronized (lock) {
                    lock.wait(sleepTime);
                }
            } catch (InterruptedException e) {
            }
        }
    }

    public void stopProcess() {
        endFlag = true;
        try {
            synchronized (lock) {
                lock.notifyAll();
            }
        } catch (Exception e) {
        }
    }

    public void setConn(AgentConnector conn) {
        this.conn = conn;
    }

    public static void start() {
        if (runner == null) {
            synchronized(lock) {
                if(runner == null) {
                    AgentConnector conn = AgentConnector.build();
                    runner = new JVMCollector();
                    runner.setConn(conn);
                    runner.endFlag = false;
                    new Thread(runner).start();
                    System.out.println("<<sidecar:JVMController>> start");
                }
            }
        }
    }

    public static void stop() {
        if (runner != null) {
            runner.stopProcess();
            runner = null;
            System.out.println("<<sidecar:JVMController>> stop");
        }
    }

}
