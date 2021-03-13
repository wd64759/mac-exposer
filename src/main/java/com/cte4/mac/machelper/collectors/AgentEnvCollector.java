package com.cte4.mac.machelper.collectors;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

import com.cte4.mac.machelper.model.MetricsEntity;
import com.cte4.mac.machelper.utils.AgentConnector;

import io.prometheus.jmx.shaded.io.prometheus.client.GaugeMetricFamily;

public class AgentEnvCollector {
    public static void start() {
        final AgentConnector conn = AgentConnector.build();
        BaseCollector.startRunner(AgentEnvCollector.class.getName(), new TaskExecutor(){
            @Override
            public void execute() {
                MetricsEntity metrics = new MetricsEntity();
                OperatingSystemMXBean osMXBean = ManagementFactory.getOperatingSystemMXBean();
                metrics.getMetrics().add(new GaugeMetricFamily("process_start_time_seconds", "Start time of the process since unix epoch in seconds.",
                osMXBean.getAvailableProcessors()));
                conn.sendMessage(metrics);
            }
        });
    }

    public static void stop() {
        BaseCollector.stopRunner(AgentEnvCollector.class.getName());
    }
}
