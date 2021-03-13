package com.cte4.mac.machelper.collectors;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import com.cte4.mac.machelper.model.MetricsEntity;
import com.cte4.mac.machelper.utils.AgentConnector;

import io.prometheus.jmx.shaded.io.prometheus.client.GaugeMetricFamily;

public class JVMCollector {

    public static void start() {
        final AgentConnector conn = AgentConnector.build();
        BaseCollector.startRunner(AgentEnvCollector.class.getName(), new TaskExecutor() {
            @Override
            public void execute() {
                MetricsEntity metrics = new MetricsEntity();
                ThreadMXBean threads = ManagementFactory.getThreadMXBean();
                metrics.getMetrics().add(new GaugeMetricFamily("jvm_threads_total_count", "the totle threads count",
                threads.getThreadCount()));
                metrics.getMetrics().add(new GaugeMetricFamily("jvm_threads_peak_count", "the peak threads count",
                threads.getPeakThreadCount()));
                metrics.getMetrics().add(new GaugeMetricFamily("jvm_threads_daemon_count", "the daemon threads count",
                threads.getDaemonThreadCount()));

                conn.sendMessage(metrics);
            }
        });
    }

    public static void stop() {
        BaseCollector.stopRunner(JVMCollector.class.getName());
    }

}
