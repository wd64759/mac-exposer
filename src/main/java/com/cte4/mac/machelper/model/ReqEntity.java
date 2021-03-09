package com.cte4.mac.machelper.model;

import java.io.Serializable;


public class ReqEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    public ReqEntity(String name, String meter, String metric) {
        this.name = name;
        this.meter = meter;
        this.metric = metric;
        this.timestamp = System.currentTimeMillis();
    }
    public String name;
    public String meter;
    public String metric;
    public String attibutes;
    public String values;
    public long timestamp;
    public String context;
}
