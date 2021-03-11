package com.cte4.mac.machelper.collectors;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

import com.cte4.mac.machelper.model.ReqEntity;
import com.cte4.mac.machelper.utils.AgentConnector;

public class AgentEnvCollector {
    public static void start() {
        final AgentConnector conn = AgentConnector.build();
        final OperatingSystemMXBean osMXBean = ManagementFactory.getOperatingSystemMXBean();
        BaseCollector.startRunner(AgentEnvCollector.class.getName(), new TaskExecutor(){
            @Override
            public void execute() {
                ReqEntity req = new ReqEntity("os.processors", "gauge", Integer.toString(osMXBean.getAvailableProcessors()));
                req.attibutes = "name,arch,version";
                req.values = String.format("%s,%s,%s", osMXBean.getName(), osMXBean.getArch(), osMXBean.getVersion());
                conn.sendMessage(req);
            }
        });
    }

    public static void stop() {
        BaseCollector.stopRunner(AgentEnvCollector.class.getName());
    }
}
