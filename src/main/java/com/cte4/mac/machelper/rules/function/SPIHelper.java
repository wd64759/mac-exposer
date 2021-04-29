package com.cte4.mac.machelper.rules.function;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.jboss.byteman.rule.Rule;
import org.jboss.byteman.rule.helper.Helper;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

public class SPIHelper extends Helper {

    private static String RULE_NAME = "SPI_FUNC";

    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>(){
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<>();
        } 
    };

    protected SPIHelper(Rule rule) {
        super(rule);
    }

    public void enableLatency(MeterRegistry registry) {
        System.out.println(registry);
        try {
            Timer timer = Timer.builder("customer_service").tags("severity", "LOW", "function", "customer").publishPercentileHistogram().minimumExpectedValue(Duration.ofMillis(1))
            .maximumExpectedValue(Duration.ofSeconds(5))
            .publishPercentiles(0.5, 0.95).register(registry);
            Timer.Sample sample = Timer.start();     
            threadLocal.get().put("timer", timer);   
            threadLocal.get().put("sample", sample);  
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    public void disableLatency() {
        Timer timer = (Timer) threadLocal.get().get("timer");
        Timer.Sample sample = (Timer.Sample) threadLocal.get().get("sample");
        sample.stop(timer);
    }
}
