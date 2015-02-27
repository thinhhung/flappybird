package com.yuri.gameworld;

import com.yuri.gameobjects.Bird;

public class GameWorld {
	private Bird bird;
	
	public GameWorld(int midPointY) {
		bird = new Bird(33, midPointY - 5, 34, 25);
	}
	
	public void update(float delta) {
		bird.update(delta);
	}
	
	public Bird getBird() {
		return bird;
	}
}
