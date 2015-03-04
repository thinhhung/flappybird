package com.yuri.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.yuri.gameworld.GameRenderer;
import com.yuri.gameworld.GameWorld;
import com.yuri.helpers.InputHandler;

public class GameScreen implements Screen {
	private GameWorld world;
	private GameRenderer renderer;
	private float runTime;

	public GameScreen() {
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float gameWidth = 136;
		float gameHeight = screenHeight / (screenWidth / gameWidth);
		
		int midPointY = (int) (gameHeight / 2);
		
		this.world = new GameWorld(midPointY);
		this.renderer = new GameRenderer(this.world, (int)gameHeight, midPointY);
		
		Gdx.input.setInputProcessor(new InputHandler(world));
	}

	@Override
	public void show() {
		Gdx.app.log("GameScreen", "show()");

	}

	@Override
	public void render(float delta) {
		runTime += delta;
		this.world.update(delta);
		this.renderer.render(runTime);
	}

	@Override
	public void resize(int width, int height) {
		Gdx.app.log("GameScreen", "resize()");

	}

	@Override
	public void pause() {
		Gdx.app.log("GameScreen", "pause()");

	}

	@Override
	public void resume() {
		Gdx.app.log("GameScreen", "resume()");

	}

	@Override
	public void hide() {
		Gdx.app.log("GameScreen", "hide()");

	}

	@Override
	public void dispose() {
		Gdx.app.log("GameScreen", "dispose()");

	}

}
