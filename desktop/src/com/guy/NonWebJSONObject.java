package com.guy;

import org.json.JSONObject;

public class NonWebJSONObject implements com.guy.JSONObject{
    JSONObject object;

    public NonWebJSONObject(JSONObject value) {
        object = value;
    }

    @Override
    public Object get(String key) throws JSONException {
        try {
            return object.get(key);
        } catch (org.json.JSONException e) {
            throw new JSONException();
        }
    }

    @Override
    public String getString(String key) throws JSONException {
        try {
            return object.getString(key);
        } catch (org.json.JSONException e) {
            throw new JSONException();
        }
    }

    @Override
    public double getDouble(String key) throws com.guy.JSONException {
        try {
            return object.getDouble(key);
        } catch (org.json.JSONException e) {
            throw new JSONException();
        }
    }

    @Override
    public boolean getBoolean(String key) throws com.guy.JSONException {
        try {
            return object.getBoolean(key);
        } catch (org.json.JSONException e) {
            throw new JSONException();
        }
    }

    @Override
    public int getInt(String key) throws com.guy.JSONException {
        try {
            return object.getInt(key);
        } catch (org.json.JSONException e) {
            throw new JSONException();
        }
    }

    @Override
    public long getLong(String key) throws com.guy.JSONException {
        try {
            return object.getLong(key);
        } catch (org.json.JSONException e) {
            throw new JSONException();
        }
    }

    @Override
    public float getFloat(String key) throws com.guy.JSONException {
        try {
            return ((Double)object.getDouble(key)).floatValue();
        } catch (org.json.JSONException e) {
            throw new JSONException();
        }
    }
}
