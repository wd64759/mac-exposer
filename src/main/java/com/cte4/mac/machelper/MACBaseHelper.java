package com.cte4.mac.machelper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;

import org.jboss.byteman.rule.Rule;
import org.jboss.byteman.rule.helper.Helper;

public class MACBaseHelper extends Helper {

    protected MACBaseHelper(Rule rule) {
        super(rule);
    }

    public void send(){
        HttpClient client = HttpClient.newBuilder().build();
        // client.
    }

    public static void main(String[] args) {
        // HttpRequest request = HttpRequest.newBuilder()
        //  .uri(URI.create("https://foo.com/"))
        //  .timeout(Duration.ofMinutes(2))
        //  .header("Content-Type", "application/json").build();
        //  request
        HttpClient client = HttpClient.newHttpClient();
        // client.send(request, responseBodyHandler)
    }
    
}
