package com.cte4.mac.machelper.utils;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * this store init settings from server side.
 */
public class AgentSettings {
    private static Map<String,String> settings = new ConcurrentHashMap<>();
    public static void setProperty(String key, String value) {
        settings.put(key, value);
    }
    public static String getProperty(String key, String value) {
        return Optional.ofNullable(settings.get(key)).orElse("");
    }
}
