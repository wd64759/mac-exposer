package com.cte4.mac.machelper;

import com.cte4.mac.machelper.collectors.JVMCollector;

import org.jboss.byteman.rule.Rule;
import org.jboss.byteman.rule.helper.Helper;

public class JVMHelper extends Helper  {

    protected JVMHelper(Rule rule) {
        super(rule);
    }
    
    public void enableMonitor() {
        System.out.println(String.format("Rule:[%s] is enabled", this.rule.getName()));
        JVMCollector.start();
    }

    public static void uninstalled(Rule rule) {
        System.out.println("===uninstalled===" + rule.getName());
        JVMCollector.stop();
    }
}
