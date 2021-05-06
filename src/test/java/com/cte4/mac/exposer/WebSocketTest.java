package com.cte4.mac.exposer;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;


public class WebSocketTest {
    
    public static void main(String[] args) {

        WebSocketTest wt = new WebSocketTest();
        String wsUrl = "ws://localhost:7011/websocket/123";
        URI uri = URI.create(wsUrl);
        SidecarSocketClient client = wt.new SidecarSocketClient(uri);
        client.send("hello");
    }

    public class SidecarSocketClient extends WebSocketClient {

        public SidecarSocketClient(URI serverUri) {
            super(serverUri);
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
            System.out.println("open connection");
        }
    
        @Override
        public void onMessage(String message) {
            System.out.println("client meesage:" + message);
        }
    
        @Override
        public void onClose(int code, String reason, boolean remote) {
            // retry - init()
            System.out.println("lost connection");
        }
    
        @Override
        public void onError(Exception ex) {
            System.out.println("error from server:" + ex.getMessage());
        }
        
    }
    
}
