package com.cte4.mac.machelper.rules.standard;

import com.cte4.mac.machelper.collectors.JVMCollector;

import org.jboss.byteman.rule.Rule;
import org.jboss.byteman.rule.helper.Helper;

public class JvmStdHelper extends Helper {

    protected JvmStdHelper(Rule rule) {
        super(rule);
    }
    
    public void enableMonitor() {
        System.out.println(String.format("::enable rule::", this.rule.getName()));
        JVMCollector.start();
    }

    public static void uninstalled(Rule rule) {
        System.out.println("::disable rule::" + rule.getName());
        JVMCollector.stop();
    }

}
