package com.cte4.mac.machelper.utils;

public class MacCargo implements Runnable {

    static boolean stop;
    private static MacCargo instance;
    private long sleeptime = 10000;

    public static MacCargo build() {
        if (instance == null) {
            synchronized (MacCargo.class) {
                if (instance == null) {
                    instance = new MacCargo();
                    try {
                        instance.init();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    @Override
    public void run() {
        while(!stop) {
            try {
                process();
                synchronized(instance) {
                    instance.wait(sleeptime);
                }
            } catch (InterruptedException ie) {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("::target:cargo-daemon: cargo is down");
    }

    public void stop() {
        stop = true;
        synchronized(instance) {
            instance.notifyAll();
        }
    }

    public void init() throws Exception {
        Thread t = new Thread(this);
        t.setDaemon(true);
        t.start();
        System.out.println("::target:cargo-daemon: cargo is up");
    }

    /**
     * To be used to waeving in MAC sidecar
     * @throws Exception
     */
    public void process() throws Exception {
    }

}
