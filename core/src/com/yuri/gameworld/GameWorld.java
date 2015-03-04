package com.yuri.gameworld;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.yuri.gameobjects.Bird;
import com.yuri.gameobjects.ScrollHandler;
import com.yuri.helpers.AssetLoader;

public class GameWorld {
	private Bird bird;
	private ScrollHandler scroller;
    private Rectangle ground;
    private int score;

    private int midPointY;

    private GameState currentState;

    private enum GameState {
        READY, RUNNING, GAME_OVER, HIGH_SCORE
    }
	
	public GameWorld(int midPointY) {
        currentState = GameState.READY;
        this.midPointY = midPointY;
		bird = new Bird(33, midPointY - 5, 17, 13);
		// The grass should start 66 pixels below the midPointY
		scroller = new ScrollHandler(this, midPointY + 66);
        ground = new Rectangle(0,  midPointY + 66, 136, 11);
	}

    public void update(float delta) {
        switch (currentState) {
            case READY:
                updateReady(delta);
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            default:
                break;
        }
    }

    public void updateReady(float delta) {

    }
	
	public void updateRunning(float delta) {
        if (delta > .15f) {
            delta = .15f;
        }

		bird.update(delta);
		scroller.update(delta);

        if (bird.isAlive() && scroller.collides(bird)) {
            scroller.stop();
            bird.die();
            AssetLoader.dead.play();
        }

        if (Intersector.overlaps(bird.getBoundingCircle(), ground)) {
            scroller.stop();
            bird.die();
            bird.decelerate();
            currentState = GameState.GAME_OVER;

            if (score > AssetLoader.getHighScore()) {
                AssetLoader.setHighScore(score);
                currentState = GameState.HIGH_SCORE;
            }
        }
	}
	
	public Bird getBird() {
		return bird;
	}
	
	public ScrollHandler getScroller() {
		return scroller;
	}

    public int getScore() {
        return score;
    }

    public void addScore(int increment) {
        score += increment;
    }

    public boolean isReady() {
        return currentState == GameState.READY;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAME_OVER;
    }

    public boolean isHighScore() {
        return currentState == GameState.HIGH_SCORE;
    }

    public void start() {
        currentState = GameState.RUNNING;
    }

    public void restart() {
        score = 0;
        bird.onRestart(midPointY - 5);
        scroller.onRestart();
        currentState = GameState.READY;
    }
}
