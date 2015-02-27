package com.yuri.flappybird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.yuri.helpers.AssetLoader;
import com.yuri.screens.GameScreen;

public class FlappyBird extends Game {
	
	@Override
	public void create () {
		Gdx.app.log("Game", "Created");
		AssetLoader.load();
		this.setScreen(new GameScreen());
	}
	
	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}
}
