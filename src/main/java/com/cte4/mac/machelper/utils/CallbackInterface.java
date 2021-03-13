package com.cte4.mac.machelper.utils;

public interface CallbackInterface {
    public String getServiceType();
    public boolean isAccept(String msgTyp);
    public boolean process(String msgContent);
}
