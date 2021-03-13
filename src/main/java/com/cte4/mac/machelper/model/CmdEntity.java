package com.cte4.mac.machelper.model;

import java.io.Serializable;
import java.util.List;

import io.prometheus.jmx.shaded.io.prometheus.client.Collector.MetricFamilySamples;


public class ReqEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    int standard = 0;
    public long timestamp;
    List<MetricFamilySamples> metrics;
}
