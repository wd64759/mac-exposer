package com.cte4.mac.machelper.collectors;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

import com.cte4.mac.machelper.model.MetricsEntity;
import com.cte4.mac.machelper.utils.AgentConnector;

import io.prometheus.jmx.shaded.io.prometheus.client.GaugeMetricFamily;

public class AgentEnvCollector implements TaskExecutor {

    private AgentConnector conn;
    private static final String RULE_NAME = "AgentEnv";

    public AgentEnvCollector() {
        conn = AgentConnector.build();
    }

    public static void start() {
        AgentEnvCollector collector = new AgentEnvCollector();
        BaseCollector.startRunner(RULE_NAME, collector);
    }

    public static void stop() {
        BaseCollector.stopRunner(RULE_NAME);
    }

    @Override
    public void execute() {
        MetricsEntity metrics = new MetricsEntity();
        OperatingSystemMXBean osMXBean = ManagementFactory.getOperatingSystemMXBean();
        metrics.getMetrics().add(new GaugeMetricFamily("process_start_time_seconds",
                "Start time of the process since unix epoch in seconds.", osMXBean.getAvailableProcessors()));
        conn.sendMessage(metrics);
    }

}
