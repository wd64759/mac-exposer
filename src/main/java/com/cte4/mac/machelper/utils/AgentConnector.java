package com.cte4.mac.machelper.utils;

import java.lang.management.ManagementFactory;
import java.net.URI;
import java.nio.channels.NotYetConnectedException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cte4.mac.machelper.collectors.TaskCallback;
import com.cte4.mac.machelper.model.CmdEntity;
import com.cte4.mac.machelper.model.MetricsEntity;
import com.google.gson.Gson;

import org.java_websocket.exceptions.WebsocketNotConnectedException;

public class AgentConnector {

    private static AgentConnector instance;
    private static Gson gson = new Gson();
    private SocketClient sc;
    private String pid;
    private URI wsURI;
    private static long RETRY_INTERVAL = 500;
    private boolean isConnected = false;
    public Map<String, TaskCallback> listeners = new ConcurrentHashMap<>();

    private AgentConnector() {
        pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        String wsUrl = "ws://localhost:7011/websocket/" + pid;
        wsURI = URI.create(wsUrl);
        reconnect();
    }

    /**
     * init ws connection
     */
    private boolean reconnect() {
        if (isConnected) {
            return isConnected;
        }

        int retryTimes = 3;
        int i = 0;
        while (i++ <= retryTimes && (sc == null || sc.isClosed())) {
            try {
                System.out.println("::agent-conn: init ws connection");
                sc = new SocketClient(wsURI, this);
                isConnected = true;
                break;
            } catch (Exception e) {
                System.out.println("fail to init ws connection" + e);
            }
            if (!isConnected) {
                try {
                    Thread.sleep(RETRY_INTERVAL);
                } catch (InterruptedException e) {
                }
            }
        }
        return isConnected;
    }

    public static AgentConnector build() {
        if (instance == null) {
            synchronized (AgentConnector.class) {
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
            if (isConnected || !reconnect()) {
                reqText = gson.toJson(req);
                sc.send(reqText);
            } else {
                System.out.println("::agent-send: request drop for conn broken");
            }
        } catch (NotYetConnectedException | WebsocketNotConnectedException nyc) {
            isConnected = false;
        } catch (Exception e) {
            System.out.println("::agent-send: fail to send out message : " + e);
        }
    }

    public void close() {
        try {
            if (sc != null) {
                sc.close();
            }
        } catch (Exception e) {
        }
    }

    public void onMessage(String resp) {
        try {
            CmdEntity ce = gson.fromJson(resp, CmdEntity.class);
            listeners.values().stream().forEach(t -> {
                if (t.isAcceptable(ce)) {
                    t.callback(ce);
                }
            });
        } catch (Exception e) {
            System.out.println("::agent-receive: failure of on message process : " + e);
        }
    }

    public void addListener(String name, TaskCallback collector) {
        System.out.println("::agent-listener: listener hooked for rule:" + name);
        listeners.put(name, collector);
    }

    public void removeListener(String name) {
        System.out.println("::agent-listener: detached for rule:" + name);
        listeners.remove(name);
    }

    public static void main(String[] args) {
        AgentConnector ac = AgentConnector.build();
        System.out.println(ac.isConnected);
        MetricsEntity metrics = MetricsEntity.funcBundle("abc");
        while (true) {
            ac.sendMessage(metrics);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        // ac.close();
    }

}
