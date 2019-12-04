package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.guy.JSONException;
import com.guy.JSONObject;
import com.guy.NetTask;
import com.guy.SocketIO;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	String id;

	SocketIO socketInterface;

	public MyGdxGame(SocketIO socket){
		socketInterface = socket;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		try {
			socketInterface.connect("http://localhost:8081");
			configSocketEvents();
		} catch (Exception e) {
			e.printStackTrace();
			//todo Crash the game
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	private void configSocketEvents() {
		socketInterface
		.on("connect", new NetTask() {
			@Override
			public void run(Object... args) {
				//Your code here!
				socketInterface.log("SOCKET.IO: Connected!", true);
			}
		})
		.on("socketID", new NetTask() {
			@Override
			public void run(Object... args) {
				final JSONObject data = (JSONObject) args[0];
				try {
					id = data.getString("id");
					socketInterface.log(id, true);
					//Your SocketID code here!
					socketInterface.emit("myPing", "value", 0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		})
		.on("pong", new NetTask() {
			@Override
			public void run(Object... args) {
				final JSONObject data = (JSONObject) args[0];
				try {
					socketInterface.log("Pong!! " + data.get("value").toString(), true);
					//Just a demo :)
				} catch (JSONException e) { //TODO JSONException
					e.printStackTrace();
				}
			}
		});
	}
}
