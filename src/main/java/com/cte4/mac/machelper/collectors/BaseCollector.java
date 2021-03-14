package com.cte4.mac.machelper.collectors;

import java.util.HashMap;
import java.util.Map;

public class BaseCollector {

    static Map<String, TaskCarrier> runners = new HashMap<>();
    // -1: stop for notificaiton
    static final long DEFAULT_LOOPSPAN = 5000;

    public static boolean startRunner(String runner, TaskExecutor executor) {
        return startRunner(runner, executor, DEFAULT_LOOPSPAN);
    }

    public static boolean startRunner(String runner, TaskExecutor executor, long loopSpan) {
        TaskCarrier tc = null;
        synchronized (runners) {
            if (runners.keySet().contains(runner)) {
                return false;
            }
            loopSpan = loopSpan > 0 ? loopSpan : -1;
            tc = new TaskCarrier(executor, loopSpan);
            runners.put(runner, tc);
        }
        Thread t = new Thread(tc);
        t.start();
        return true;
    }

    public static void invoke(String runner) {
        TaskCarrier tc = runners.get(runner);
        if (tc != null) {
            tc.invoke();
        }
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
        private long sleepTime;
        private Object locker = new Object();

        TaskCarrier(TaskExecutor executor, long sleepTime) {
            this.executor = executor;
            this.sleepTime = sleepTime;
        }

        @Override
        public void run() {
            if (sleepTime > 0) {
                executor.execute();
            }
            while (!endFlag) {
                synchronized (locker) {
                    try {
                        locker.wait(sleepTime);
                        executor.execute();
                    } catch (InterruptedException e) {
                    }
                }
            }
        }

        public void invoke() {
            synchronized (locker) {
                locker.notifyAll();
            }
        }

        public void stop() {
            endFlag = true;
            invoke();
        }
    }

}
