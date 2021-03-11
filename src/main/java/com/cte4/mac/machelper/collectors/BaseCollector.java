package com.cte4.mac.machelper.collectors;

import java.util.HashMap;
import java.util.Map;

public class BaseCollector {

    static Map<String, TaskCarrier> runners = new HashMap<>();

    public static void startRunner(String runner, TaskExecutor executor) {
        TaskCarrier tc = null;
        synchronized (runners) {
            if (runners.keySet().contains(runner)) {
                return;
            }
            tc = new TaskCarrier(executor);
            runners.put(runner, tc);
        }
        Thread t = new Thread(tc);
        t.start();
    }

    public static void stopRunner(String runner) {
        synchronized (runners) {
            TaskCarrier tc = runners.remove(runner);
            if (tc != null) {
                tc.stop();
            }
        }
    }

    static class TaskCarrier implements Runnable {
        private boolean endFlag;
        private TaskExecutor executor;
        private long sleepTime = 5000;
        private Object locker = new Object();

        TaskCarrier(TaskExecutor executor) {
            this.executor = executor;
        }

        @Override
        public void run() {
            while (!endFlag) {
                executor.execute();
                synchronized (locker) {
                    try {
                        locker.wait(sleepTime);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }

        public void stop() {
            endFlag = true;
            synchronized (locker) {
                locker.notifyAll();
            }
        }
    }

}
