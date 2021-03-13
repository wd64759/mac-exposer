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
                Thread.sleep(sleeptime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void init() throws Exception {
        Thread t = new Thread(this);
        t.start();
        System.out.println("<<client:cargo>> cargo train is ready...");
    }

    /**
     * To be used to waeving in MAC sidecar
     * @throws Exception
     */
    public void process() throws Exception {
    }

}
