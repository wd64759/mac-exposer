package com.cte4.mac.machelper.utils;

import java.lang.management.ManagementFactory;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cte4.mac.machelper.collectors.TaskCallback;
import com.cte4.mac.machelper.model.CmdEntity;
import com.cte4.mac.machelper.model.MetricsEntity;
import com.google.gson.Gson;

public class AgentConnector {

    private static AgentConnector instance;
    private static Gson gson = new Gson();
    private static Object lock = new Object();
    private SocketClient sc;
    public static Map<String, TaskCallback> listeners = new ConcurrentHashMap<>();

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

    public void sendMessage(MetricsEntity req) {
        String reqText = null;
        try {
            reqText = gson.toJson(req);
            sc.send(reqText);
            // System.out.println("<<sidecar:AgentConnector>> sendMessage - " + reqText);
        } catch (Exception e) {
            System.out.println("<<sidecar:AgentConnector>> sendMessage - unexpected happened : " + e);
            // e.printStackTrace();
        }
    }

    public void onMessage(String resp) {
        // System.out.println("::websockt::on_message:" + resp);
        try {
            CmdEntity ce = gson.fromJson(resp, CmdEntity.class);
            listeners.values().stream().forEach(t->{
                if(t.isAcceptable(ce)) {
                    t.callback(ce);
                }
            });
        } catch (Exception e) {
            System.out.println("::websockt::on_message: unknown message format - " + e);
        }
    }

    public void addListener(String name, TaskCallback collector) {
        System.out.println("listener hooked for rule:" + name);
        listeners.put(name, collector);
    }

    public void removeListener(String name) {
        System.out.println("listener detached for rule:" + name);
        listeners.remove(name);
    }

}
