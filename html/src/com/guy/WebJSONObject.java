package com.guy;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

public class WebJSONObject implements com.guy.JSONObject{
    JSONObject jsObject;

    public WebJSONObject(JavaScriptObject jsValue) {
        jsObject = new JSONObject(jsValue);
    }

    @Override
    public Object get(String key) throws JSONException {
        return getNormalValue(jsObject.get(key));
    }

    @Override
    public String getString(String key) throws JSONException {
        return jsObject.get(key).isString().stringValue();
    }

    @Override
    public double getDouble(String key) throws JSONException {
        return jsObject.get(key).isNumber().doubleValue();
    }

    @Override
    public boolean getBoolean(String key) throws JSONException {
        return jsObject.get(key).isBoolean().booleanValue();
    }

    @Override
    public int getInt(String key) throws JSONException {
        return ((Double)jsObject.get(key).isNumber().doubleValue()).intValue();
    }

    @Override
    public long getLong(String key) throws JSONException {
        return ((Double)jsObject.get(key).isNumber().doubleValue()).longValue();
    }

    @Override
    public float getFloat(String key) throws JSONException {
        return ((Double)jsObject.get(key).isNumber().doubleValue()).floatValue();
    }

    private Object getNormalValue(JSONValue value){
        if(value.isNumber() != null){
            return value.isNumber().doubleValue();
        } else if(value.isString() != null){
            return value.isString().stringValue();
        } else if(value.isBoolean() != null){
            return value.isBoolean().booleanValue();
        } else if(value.isArray() != null){
            Object[] normalArray = new Object[value.isArray().size()];
            for(int i = 0; i < value.isArray().size(); i++){
                normalArray[i] = getNormalValue(value.isArray().get(i));
            } return normalArray;
        } else if(value.isObject() != null){
            //Not sure what to do here yet.
        } return null;
    }

}
