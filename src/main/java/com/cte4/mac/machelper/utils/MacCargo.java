package com.cte4.mac.machelper.utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;

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
                heatbeat();
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

    /**
     * send agent port
     */
    private void heatbeat() {
    }

    /**
     * to read and upload MAC annotation configuration
     */
    private void uploadCfg() {
        String uri = String.format("http://%s", AgentConnector.MAC_SC_CONN);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();
        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            System.out.println(">>" + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
