package com.guy;

import com.epiko.TJSocketIO;
import com.google.gwt.core.client.Callback;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.ScriptInjector;
import java.util.HashMap;

public class WebSocketInterface implements SocketIO {
    TJSocketIO socket;

    String id;
    String testUrl;

    @Override
    public void connect(final String url) {
        testUrl = url;
    }

    @Override
    public SocketIO on(final String event, final NetTask task) {
        ScriptInjector.fromUrl("/socket.io/socket.io.js").setCallback(new Callback<Void, Exception>() {
            @Override
            public void onSuccess(Void result) {
                socket = TJSocketIO.connect(testUrl);

                socket.onSocket(event, new TJSocketIO.SocketHandler<JavaScriptObject>() {
                    @Override
                    public void onSocket(JavaScriptObject data) {
                        try {
                            task.run(new WebJSONObject(data));
                        } catch (ClassCastException e) {
                            try { //This is a really bad way to do this, but the alternative would be overcomplicated.
                                task.run(new WebJSONArray(data));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                task.run();
                            }
                        }
                    }
                });
            }

            @Override
            public void onFailure(Exception reason) {
                log("There was an error during connection", true);
            }
        }).inject();
        return this;
    }

    @Override
    public void emit(String event, Object... args) throws JSONException {
        JSONObject data = new JSONObject();
        for (int i = 0; i < args.length - 1; i += 2) {
            data.put((String) args[i], getJSONValue(args[i + 1]));
        } socket.emitSocket(event, data.getJavaScriptObject());
    }

    @Override
    public native void log(String msg, boolean important)/*-{
		if(important){
		    $wnd.alert(msg);
		} else {
		    console.log(msg)
		}
	}-*/;

    @Override
    public native void log(String msg)/*-{
        console.log(msg)
	}-*/;

	private JSONValue getJSONValue(Object value){
	    switch(value.getClass().getSimpleName()){
            case "Integer":
                return new JSONNumber((int)value);

            case "Float":
                return new JSONNumber((float)value);

            case "Double":
                return new JSONNumber((double)value);

            case "String":
                return new JSONString((String)value);

            case "Boolean":
                return JSONBoolean.getInstance((boolean)value);

            case "JavaScriptObject": //Could be array...
                return new JSONObject((JavaScriptObject) value);

            case "Null":
            default:
                log(value.getClass().getSimpleName(), true);
                return JSONNull.getInstance();
        }
    }
}
