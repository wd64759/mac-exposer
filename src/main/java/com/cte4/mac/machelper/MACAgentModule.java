package com.cte4.mac.machelper;

import org.jboss.byteman.modules.NonModuleSystem;

public class MACAgentModule extends NonModuleSystem {
    public void initialize(String args) {
        super.initialize(args);
        System.out.println(">>>>>>>>>> my init invoked !");
    }
}
