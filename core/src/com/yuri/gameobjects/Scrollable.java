package com.yuri.gameobjects;

import com.badlogic.gdx.math.Vector2;

public class Scrollable {
	protected Vector2 position;
	protected Vector2 velocity;
	protected int width;
	protected int height;
	protected boolean isScrolledLeft;
	
	public Scrollable(float x, float y, int width, int height, float scrollSpeed) {
		position = new Vector2(x, y);
		velocity = new Vector2(scrollSpeed, 0);
		this.width = width;
		this.height = height;
		isScrolledLeft = false;
	}
	
	public void update(float delta) {
		position.add(velocity.cpy().scl(delta));
		
		// If the Scrollable object is no longer visible
		if (position.x + width < 0) {
			isScrolledLeft = true;
		}
	}
	
	public void reset(float x) {
		position.x = x;
		isScrolledLeft = false;
	}

    public void stop() {
        velocity.x = 0;
    }

    public void onRestart(float x, float scrollSpeed) {
        position.x = x;
        velocity.x = scrollSpeed;
    }

	public boolean isScrolledLeft() {
		return isScrolledLeft;
	}
	
	public float getTailX() {
		return position.x + width;
	}
	
	public float getX() {
		return position.x;
	}
	
	public float getY() {
		return position.y;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
}
