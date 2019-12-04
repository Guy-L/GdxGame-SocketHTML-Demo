package com.guy;


import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class NonWebSocketInterface implements SocketIO {
    private Socket socket;

    @Override
    public void connect(String url) throws URISyntaxException {
        socket = IO.socket(url);
        socket.connect();
    }

    @Override
    public SocketIO on(String event, final NetTask task) {
        socket.on(event, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                if(args.length>0) { //TODO to be removed, handled by constructor
                    task.run(new NonWebJSONObject((JSONObject)args[0]));
                }
            }
        }); return this;
    }

    @Override
    public void emit(String event, Object... args) throws JSONException {
        JSONObject data = new JSONObject();
        for(int i = 0; i < args.length-1; i+=2){
            data.put((String)args[i], args[i+1]);
        } socket.emit(event, data);
    }

    @Override
    public void log(String msg, boolean important) {
        System.out.println(msg);
    }

    @Override
    public void log(String msg) {
        System.out.println(msg);
    }
}