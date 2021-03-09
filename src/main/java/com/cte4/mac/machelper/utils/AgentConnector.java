package com.cte4.mac.machelper.utils;

import java.lang.management.ManagementFactory;
import java.net.URI;

import com.cte4.mac.machelper.model.ReqEntity;
import com.cte4.mac.machelper.model.RespEntity;
import com.google.gson.Gson;

public class AgentConnector {

    private static AgentConnector instance;
    private static Gson gson = new Gson();
    private static Object lock = new Object();
    private SocketClient sc;

    private AgentConnector() {
        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        String wsUrl = "ws://localhost:7011/websocket/" + pid;
        URI uri = URI.create(wsUrl);
        sc = new SocketClient(uri, this);
    }

    public static AgentConnector build() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new AgentConnector();
                }
            }
        }
        return instance;
    }

    public void sendMessage(ReqEntity req) {
        String reqText = null;
        try {
            reqText = gson.toJson(req);
            sc.send(reqText);
            System.out.println("<<sidecar:AgentConnector>> sendMessage - " + reqText);
        } catch (Exception e) {
            System.out.println("<<sidecar:AgentConnector>> sendMessage - unexpected happened");
            e.printStackTrace();
        }
    }

    public void onMessage(String resp) {
        try {
            // RespEntity pe = gson.fromJson(resp, RespEntity.class);
            System.out.println("<<sidecar:AgentConnector>> onMessage - " + resp);
            // if("run_dc".equalsIgnoreCase(pe.cmd)) {
            //     String dcName = pe.params;
            //     String fullName = "com.cte4.mac.machelper.collectors." + dcName;
            //     Class dcRunner = Class.forName(fullName);
            //     new Thread().start();
            // }
        } catch (Exception e) {
            System.out.println("<<sidecar:AgentConnector>> onMessage - unexpected happened");
            e.printStackTrace();
        }
    }

}
