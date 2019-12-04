package com.guy;

import org.json.JSONArray;

public class NonWebJSONArray implements com.guy.JSONArray{
    JSONArray object;

    public NonWebJSONArray(JSONArray value) {
        object = value;
    }

    @Override
    public Object get(int key) throws JSONException {
        try {
            return object.get(key);
        } catch (org.json.JSONException e) {
            throw new JSONException();
        }
    }

    @Override
    public JSONObject getJSONObject(int key) throws JSONException {
        try {
            return new NonWebJSONObject(object.getJSONObject(key));
        } catch (org.json.JSONException e) {
            throw new JSONException();
        }
    }

    @Override
    public com.guy.JSONArray getJSONArray(int key) throws JSONException {
        try {
            return new NonWebJSONArray(object.getJSONArray(key));
        } catch (org.json.JSONException e) {
            throw new JSONException();
        }
    }

    @Override
    public int size() {
        return object.length();
    }
}
