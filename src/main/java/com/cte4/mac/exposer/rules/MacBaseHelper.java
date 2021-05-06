package com.cte4.mac.exposer.rules;

import java.util.HashMap;
import java.util.Map;

import org.jboss.byteman.rule.Rule;
import org.jboss.byteman.rule.helper.Helper;

public abstract class MacBaseHelper extends Helper {

    String ruleName;
    long updated;
    private static Map<String, MacBaseHelper> ruleRepo = new HashMap<>();

    protected MacBaseHelper(Rule rule, String ruleName) {
        super(rule);
        this.ruleName = ruleName;
        this.updated = System.currentTimeMillis();
    }

    protected String getRuleName() {
        return ruleName;
    }

    public static void register(String ruleName, MacBaseHelper mbh) {
        ruleRepo.put(ruleName, mbh);
    }

    public static void detach(String ruleName) {
        ruleRepo.remove(ruleName);
    }
    
}
