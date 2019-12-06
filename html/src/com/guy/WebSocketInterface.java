package com.guy;

import com.epiko.TJSocketIO;
import com.google.gwt.core.client.Callback;
import com.google.gwt.json.client.*;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONArray;
import java.util.HashMap;

public class WebSocketInterface implements SocketIO {
    TJSocketIO socket;

    String testUrl;

    //***** IMPORTANT *****
    //If your server is online and not local, swap commented lines with uncommented lines for methods "on", "confirm", and the next line.
    //HashMap<String, NetTask> testMap = new HashMap<String, NetTask>();

    @Override
    public void connect(final String url) {
        testUrl = url;
    }

    @Override
    public SocketIO on(final String event, final NetTask task) {
        //**LOCAL SERVERS**
        ScriptInjector.fromUrl("/socket.io/socket.io.js").setCallback(new Callback<Void, Exception>() {
            @Override
            public void onSuccess(Void result) {
                socket = TJSocketIO.connect(testUrl);

                socket.onSocket(event, new TJSocketIO.SocketHandler<JavaScriptObject>() {
                    @Override
                    public void onSocket(JavaScriptObject data) {
                        try {
                            task.run(new WebJSONArray(data));
                        } catch (ClassCastException e) {
                            try { //This is a really bad way to do this TODO Improve!
                                task.run(new WebJSONObject(data));
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

        //**ONLINE SERVERS**
//        testMap.put(event, task);

        //Both
        return this;
    }

    @Override
    public void confirm() {
        //**ONLINE SERVERS** **YOU MUST REPLACE THE URL**
//        ScriptInjector.fromUrl("/socket.io/socket.io.js").setCallback(new Callback<Void, Exception>() {
//            @Override
//            public void onSuccess(Void result) {
//                socket = TJSocketIO.connect(testUrl);
//
//                for(String event : testMap.keySet()){
//                    socket.onSocket(event, (TJSocketIO.SocketHandler<JavaScriptObject>) data -> {
//                        try {
//                            testMap.get(event).run(new WebJSONArray(data));
//                        } catch (ClassCastException e) {
//                            try { //This is a really bad way to do this TODO Improve!
//                                testMap.get(event).run(new WebJSONObject(data));
//                            } catch (Exception ex) {
//                                ex.printStackTrace();
//                                testMap.get(event).run();
//                            }
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onFailure(Exception reason) {
//                log("There was an error during connection", true);
//            }
//        }).inject();
    }

    @Override
    public void emit(String event, Object... args) throws JSONException {
        JSONObject data = new JSONObject();
        for (int i = 0; i < args.length - 1; i += 2) {
            data.put((String) args[i], getJSONValue(args[i + 1]));
        } socket.emitSocket(event, data.getJavaScriptObject());
    }

    @Override
    public com.guy.JSONObject parseObject(String string) {
        return new WebJSONObject(JSONParser.parseStrict(string).isObject());
    }

    @Override
    public com.guy.JSONArray parseArray(String string) {
        return new WebJSONArray(JSONParser.parseStrict(string).isArray());
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

            case "JavaScriptObject":
                try {
                    return new JSONArray((JavaScriptObject) value);
                } catch (ClassCastException e) {
                    try { //This is a really bad way to do this TODO Improve!
                        return new JSONObject((JavaScriptObject) value);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return JSONNull.getInstance();
                    }
                }

            case "Null":
            default:
                log(value.getClass().getSimpleName(), true);
                return JSONNull.getInstance();
        }
    }
}
