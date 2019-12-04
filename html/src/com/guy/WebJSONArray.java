package com.guy;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;

public class WebJSONArray implements com.guy.JSONArray{
     JSONArray object;

    public WebJSONArray(JavaScriptObject jsValue) {
        object = new JSONArray(jsValue);
    }

    public WebJSONArray(JSONArray value) {
        object = value;
    }

    @Override
    public Object get(int key) throws JSONException {
        return object.get(key);
    }

    @Override
    public JSONObject getJSONObject(int key) throws JSONException {
        return new WebJSONObject(object.get(key).isObject().getJavaScriptObject());
    }

    @Override
    public com.guy.JSONArray getJSONArray(int key) throws JSONException {
        return new WebJSONArray(object.get(key).isArray());
    }
}
