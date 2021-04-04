package com.cte4.mac.machelper.rules.function;

import java.util.concurrent.atomic.AtomicLong;
import static java.util.Arrays.asList;

import com.cte4.mac.machelper.collectors.BaseCollector;
import com.cte4.mac.machelper.collectors.TaskExecutor;
import com.cte4.mac.machelper.model.MetricsEntity;
import com.cte4.mac.machelper.utils.AgentConnector;

import org.jboss.byteman.rule.Rule;
import org.jboss.byteman.rule.helper.Helper;

import io.prometheus.jmx.shaded.io.prometheus.client.CounterMetricFamily;

public class MacHealthHelper extends Helper {

    private static String RULE_NAME = "MACHEALTH_FNC";
    private static long hbInterval = 30000;
    private static AtomicLong beats = new AtomicLong(0);

    protected MacHealthHelper(Rule rule) {
        super(rule);
    }

    public static void activated() {
        BaseCollector.startRunner(RULE_NAME, new TaskExecutor() {
            @Override
            public void execute() {
                MetricsEntity metrics = MetricsEntity.funcBundle(RULE_NAME);
                CounterMetricFamily counter = new CounterMetricFamily("mac_heartbeats", "Beats from MAC daemon.",
                        asList("agentPort", "pid"));
                counter = counter.addMetric(asList(System.getProperty("org.jboss.byteman.mac.agentport"),
                        System.getProperty("org.jboss.byteman.mac.pid")), beats.incrementAndGet());
                metrics.getMetrics().add(counter);
                AgentConnector.build().sendMessage(metrics);
            }

        }, hbInterval);
    }

    public static void uninstalled(Rule rule) {
        System.out.println("::disable rule::" + RULE_NAME);
        BaseCollector.stopRunner(RULE_NAME);
    }

}
