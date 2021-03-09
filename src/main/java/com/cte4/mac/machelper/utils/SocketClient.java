package com.cte4.mac.machelper.utils;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class SocketClient extends WebSocketClient {

    private AgentConnector callback;
    public SocketClient(URI serverUri, AgentConnector callback) {
        super(serverUri);
        this.callback = callback;
        init();
    }

    private void init() {
        try {
            this.connectBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("<<sidecar:SocketClient>> onOpen - ok");
    }

    @Override
    public void onMessage(String message) {
        callback.onMessage(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("<<sidecar:SocketClient>> onClose - ok");
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("<<sidecar:SocketClient>> on error - " + ex.getMessage());
        ex.printStackTrace();
    }
    
}
