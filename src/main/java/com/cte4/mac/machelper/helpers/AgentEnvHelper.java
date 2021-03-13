package com.cte4.mac.machelper.helpers;

import com.cte4.mac.machelper.collectors.AgentEnvCollector;

import org.jboss.byteman.rule.Rule;
import org.jboss.byteman.rule.helper.Helper;

public class AgentEnvHelper extends Helper {

    protected AgentEnvHelper(Rule rule) {
        super(rule);
    }
    
    public void enableMonitor() {
        System.out.println(String.format("::enable rule::", this.rule.getName()));
        AgentEnvCollector.start();
    }

    public static void uninstalled(Rule rule) {
        System.out.println("::disable rule::" + rule.getName());
        AgentEnvCollector.stop();
    }
}