package com.yuri.helpers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.yuri.gameobjects.Bird;
import com.yuri.gameworld.GameWorld;

public class InputHandler implements InputProcessor {
	
	private Bird bird;
    private GameWorld world;
	
	public InputHandler(GameWorld world) {
		this.world = world;
        bird = this.world.getBird();
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.SPACE) {
            tapped();
            return true;
        }
        return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		tapped();
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

    public void tapped() {
        if (world.isReady()) {
            world.start();
        }
        bird.onClick();
        if (world.isGameOver()) {
            world.restart();
        }
    }

}
