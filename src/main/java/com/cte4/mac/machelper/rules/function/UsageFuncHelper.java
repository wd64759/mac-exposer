package com.cte4.mac.machelper.rules.function;

import com.cte4.mac.machelper.model.MetricsEntity;
import com.cte4.mac.machelper.utils.AgentConnector;

import org.jboss.byteman.rule.Rule;
import org.jboss.byteman.rule.helper.Helper;

import io.prometheus.jmx.shaded.io.prometheus.client.GaugeMetricFamily;

import static java.util.Arrays.asList;

public class UsageFuncHelper extends Helper {

    static final String RULE_NAME = "USAGE_FNC";

    protected UsageFuncHelper(Rule rule) {
        super(rule);
    }

    public static void installed(Rule rule) {
        System.out.println("::install rule::" + RULE_NAME);
    }
    
    public void enableMonitor() {
        System.out.println("::disable rule::" + RULE_NAME);
    }

    public static void uninstalled(Rule rule) {
        System.out.println("::disable rule::" + RULE_NAME);
    }

    public void sendMetrics(String clz, String method, long timecost) {
        MetricsEntity metrics = MetricsEntity.funcBundle(RULE_NAME);
        GaugeMetricFamily tsGauge = new GaugeMetricFamily(String.format("mac_perf_latency_milliseconds", method),
                "Time cost of method process.", asList("class","method","rulename"));
        tsGauge = tsGauge.addMetric(asList(clz, method, RULE_NAME), timecost);
        synchronized(metrics) {
            metrics.getMetrics().add(tsGauge);
        }
        AgentConnector.build().sendMessage(metrics);
    }

    
}
