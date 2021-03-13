package com.cte4.mac.machelper.helpers.thirdparties.promethues;

import com.cte4.mac.machelper.model.MetricsEntity;
import com.cte4.mac.machelper.utils.AgentConnector;
import com.cte4.mac.machelper.utils.CallbackInterface;

import io.prometheus.jmx.shaded.io.prometheus.client.hotspot.StandardExports;

public class StandardExportsHelper implements CallbackInterface {

    final String SERVICE_TYPE = "THIRDPARTY";
    final String RULE_NAME = "StandardExports";

    private StandardExports export;

    public StandardExportsHelper() {
        export = new StandardExports();
    }

    @Override
    public String getServiceType() {
        return SERVICE_TYPE;
    }

    @Override
    public boolean isAccept(String ruleName) {
        return RULE_NAME.equalsIgnoreCase(ruleName);
    }

    @Override
    public boolean process(String requestUID) {
        AgentConnector conn = AgentConnector.build();
        if (conn != null) {
            MetricsEntity me = new MetricsEntity();
            me.setRequestor(RULE_NAME);
            me.setReqestUID(requestUID);
            me.getMetrics().addAll(export.collect());
            conn.sendMessage(me);
        }
        return false;
    }

}
