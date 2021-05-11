package com.cte4.mac.exposer.rules.standard;

import com.cte4.mac.exposer.collectors.JVMCollector;

import org.jboss.byteman.rule.Rule;
import org.jboss.byteman.rule.helper.Helper;

public class JvmStdHelper extends Helper {

    protected JvmStdHelper(Rule rule) {
        super(rule);
    }
    
    public void enableMonitor() {
        JVMCollector.start();
    }

    public static void uninstalled(Rule rule) {
        System.out.println("::disable rule::" + rule.getName());
        JVMCollector.stop();
    }

}
