package com.yuri.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
	public static Texture texture;
	public static TextureRegion bg, grass;
	
	public static Animation birdAnimation;
	public static TextureRegion bird, birdDown, birdUp;
	
	public static TextureRegion skullUp, skullDown, bar;

    public static Sound dead;
    public static Sound flap;
    public static Sound coin;

    public static BitmapFont font, shadow;

    public static Preferences preferences;
	
	public static void load() {
		texture = new Texture(Gdx.files.internal("sky.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		bg = new TextureRegion(texture);
		bg.flip(false, true);

		texture = new Texture(Gdx.files.internal("land.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		grass = new TextureRegion(texture);
		grass.flip(false, true);

		texture = new Texture(Gdx.files.internal("bird.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		birdDown = new TextureRegion(texture, 0, 0, 36, 25);
		birdDown.flip(false, true);
		bird = new TextureRegion(texture, 0, 25, 36, 25);
		bird.flip(false, true);
		birdUp = new TextureRegion(texture, 0, 50, 36, 25);
		birdUp.flip(false, true);
		
		TextureRegion[] birds = { birdDown, bird, birdUp};
		birdAnimation = new Animation(0.06f,  birds);
		birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
		
		texture = new Texture(Gdx.files.internal("pipe-up.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		skullUp = new TextureRegion(texture);
		skullUp.flip(false, true);

		texture = new Texture(Gdx.files.internal("pipe-down.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		skullDown = new TextureRegion(texture);
		skullDown.flip(false, true);

		texture = new Texture(Gdx.files.internal("pipe.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		bar = new TextureRegion(texture);
		bar.flip(false, true);

        dead = Gdx.audio.newSound(Gdx.files.internal("sounds/sfx_die.ogg"));
        flap = Gdx.audio.newSound(Gdx.files.internal("sounds/sfx_wing.ogg"));
        coin = Gdx.audio.newSound(Gdx.files.internal("sounds/sfx_point.ogg"));

        font = new BitmapFont(Gdx.files.internal("fonts/text.fnt"));
        font.setScale(.25f, -.25f);
        shadow = new BitmapFont(Gdx.files.internal("fonts/shadow.fnt"));
        shadow.setScale(.25f, -.25f);

        preferences = Gdx.app.getPreferences("FlappyBird");
        if (!preferences.contains("highScore")) {
            preferences.putInteger("highScore", 0);
        }
	}

    public static void setHighScore(int value) {
        preferences.putInteger("highScore", value);
        preferences.flush();
    }

    public static int getHighScore() {
        return preferences.getInteger("highScore");
    }
	
	public static void dispose() {
        // Dispose texture
		texture.dispose();

        // Dispose sounds
        dead.dispose();
        flap.dispose();
        coin.dispose();

        // Dispose fonts
	}
}
