package com.guy;

import java.net.URISyntaxException;

public interface SocketIO {
    void connect(String url) throws URISyntaxException;
    SocketIO on(String event, NetTask task);
    void emit(String event, Object... args) throws JSONException;
    void log(String msg, boolean important);
    void log(String msg);
}
