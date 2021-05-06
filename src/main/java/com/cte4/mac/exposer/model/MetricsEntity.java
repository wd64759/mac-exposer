package com.cte4.mac.exposer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.prometheus.jmx.shaded.io.prometheus.client.Collector.MetricFamilySamples;

/**
 * All models sent from applications are metrics format
 */
public class MetricsEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private MetricsEntity(String ruleName, CmdTypEnum cmdType) {
        this.ruleName = ruleName;
        this.cmdType = cmdType;
        this.timestamp = System.currentTimeMillis();
    }

    public static MetricsEntity funcBundle(String ruleName) {
        return new MetricsEntity(ruleName, CmdTypEnum.FUNC);
    }

    public static MetricsEntity stdBundle(String ruleName) {
        return new MetricsEntity(ruleName, CmdTypEnum.STD);
    }

    private String ruleName;
    private CmdTypEnum cmdType;  
    // Message generation timestamp
    private long timestamp;
    // Matric content
    private List<MetricFamilySamples> metrics = new ArrayList<>();

    public String getRuleName() {
        return ruleName;
    }

    public CmdTypEnum getCmdType() {
        return cmdType;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public List<MetricFamilySamples> getMetrics() {
        return metrics;
    }

}
