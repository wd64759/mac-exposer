package com.cte4.mac.exposer.collectors;

import com.cte4.mac.exposer.model.CmdEntity;

/**
 * Only collector which triggerred by websocket notification 
 * needs to implement this interface 
 */
public interface TaskCallback {
    public void callback(CmdEntity cmdEntity);
    public boolean isAcceptable(CmdEntity ce);
}

