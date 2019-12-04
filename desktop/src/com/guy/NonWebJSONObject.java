package com.guy;

import org.json.JSONException;
import org.json.JSONObject;

public class NonWebJSONObject implements com.guy.JSONObject{
    JSONObject object;

    public NonWebJSONObject(JSONObject value) {
        object = value;
    }

    @Override
    public Object get(String key) throws Exception {
        return object.get(key);
    }

    @Override
    public String getString(String key) throws JSONException {
        return object.getString(key);
    }

    @Override
    public double getDouble(String key) throws JSONException {
        return object.getDouble(key);
    }

    @Override
    public boolean getBoolean(String key) throws JSONException {
        return object.getBoolean(key);
    }

    @Override
    public int getInt(String key) throws JSONException {
        return object.getInt(key);
    }

    @Override
    public long getLong(String key) throws JSONException {
        return object.getLong(key);
    }

    @Override
    public float getFloat(String key) throws JSONException {
        return ((Double)object.getDouble(key)).floatValue();
    }
}
