package com.guy;

public interface SocketIO {
    void connect(String url) throws Exception;
    SocketIO on(String event, NetTask task);
    void confirm();
    void emit(String event, Object... args) throws JSONException;
    void log(String msg, boolean important);
    void log(String msg);
    JSONObject parseObject(String string); //Todo this method may be renamed or implemented within another method.
    JSONArray parseArray(String string); //Todo this method may be renamed or implemented within another method.
}
