package com.guy;


public interface SocketIO {
    void connect(String url) throws Exception;
    SocketIO on(String event, NetTask task);
    void emit(String event, Object... args) throws Exception;
    void log(String msg, boolean important);
    void log(String msg);
}
