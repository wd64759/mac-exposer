package com.cte4.mac.machelper.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.prometheus.jmx.shaded.io.prometheus.client.Collector.MetricFamilySamples;

public class MetricsEntity implements Serializable {

    public MetricsEntity() {
        this.timestamp = System.currentTimeMillis();
    }

    private static final long serialVersionUID = 1L;
    // who request it - Nullable if function triggered
    private String requestor;
    // ID of the request (to be used for matching) - Nullable if function triggered
    private String requestUID;    
    // Message generation timestamp
    private long timestamp;
    // Matric content
    private List<MetricFamilySamples> metrics = new ArrayList<>();

    public void setRequestor(String requestor) {
        this.requestor = requestor;
    }

    public String getRequestor() {
        return this.requestor;
    }

    public void setReqestUID(String requestUID) {
        this.requestUID = requestUID;
    }

    public String getRequestUID() {
        return this.requestUID;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public List<MetricFamilySamples> getMetrics() {
        return metrics;
    }

}
