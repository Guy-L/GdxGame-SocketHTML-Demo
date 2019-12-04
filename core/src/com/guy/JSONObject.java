package com.guy;

public interface JSONObject {
    Object get(String key) throws JSONException;
    String getString(String key) throws JSONException;
    double getDouble(String key) throws JSONException;
    boolean getBoolean(String key) throws JSONException;
    int getInt(String key) throws JSONException;
    long getLong(String key) throws JSONException;
    float getFloat(String key) throws JSONException; //This one's a bonus from me :)

}
