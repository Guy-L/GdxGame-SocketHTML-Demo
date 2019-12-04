package com.guy;

public interface JSONObject {
    Object get(String key) throws Exception;
    String getString(String key) throws Exception;
    double getDouble(String key) throws Exception;
    boolean getBoolean(String key) throws Exception;
    int getInt(String key) throws Exception;
    long getLong(String key) throws Exception;
    float getFloat(String key) throws Exception; //This one's a bonus from me :)

}
