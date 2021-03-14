package com.cte4.mac.machelper.collectors;

import com.cte4.mac.machelper.model.CmdEntity;
import com.cte4.mac.machelper.model.CmdTypEnum;
import com.cte4.mac.machelper.model.MetricsEntity;
import com.cte4.mac.machelper.utils.AgentConnector;

import io.prometheus.jmx.shaded.io.prometheus.client.hotspot.StandardExports;

public class JVMCollector implements TaskExecutor, TaskCallback {

    private static final String RULE_NAME = "JVM";
    private AgentConnector conn;
    StandardExports stdExports;

    public JVMCollector() {
        conn = AgentConnector.build();
        stdExports = new StandardExports();
    }

    public static void start() {
        JVMCollector collector = new JVMCollector();
        boolean status = BaseCollector.startRunner(RULE_NAME, collector, -1);
        if (status) {
            AgentConnector.build().addListener(RULE_NAME, collector);
        }
    }

    public static void invoke() {
        BaseCollector.invoke(RULE_NAME);
    }

    public static void stop() {
        BaseCollector.stopRunner(RULE_NAME);
        AgentConnector.build().removeListener(RULE_NAME);
    }

    @Override
    public void callback(CmdEntity cmdEntity) {
        BaseCollector.invoke(RULE_NAME);
    }

    @Override
    public boolean isAcceptable(CmdEntity ce) {
        if (ce.getCmdType().equals(CmdTypEnum.DATA) && RULE_NAME.equalsIgnoreCase(ce.getRuleName()))
            return true;
        return false;
    }

    @Override
    public void execute() {
        MetricsEntity metrics = new MetricsEntity();
        metrics.getMetrics().addAll(stdExports.collect());
        conn.sendMessage(metrics);
    }

}
