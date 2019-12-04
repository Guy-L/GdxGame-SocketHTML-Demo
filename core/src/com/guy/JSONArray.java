package com.guy;

public interface JSONArray {
    Object get(int key) throws JSONException;
    JSONObject getJSONObject(int key) throws JSONException;
    JSONArray getJSONArray(int key) throws JSONException;
}
