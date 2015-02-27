package com.yuri.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.yuri.gameobjects.Bird;
import com.yuri.helpers.AssetLoader;

public class GameRenderer {
	private GameWorld world;
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	
	private SpriteBatch batch;
	
	private int midPointY;
	private int gameHeight;
	
	// Game Objects
	private Bird bird;
	
	// Game Assets
	public static TextureRegion bg, grass;
	public static Animation birdAnimation;
	public static TextureRegion birdMid, birdDown, birdUp;
	public static TextureRegion skullUp, skullDown, bar;
	
	public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
		this.world = world;
		
		this.gameHeight = gameHeight;
		this.midPointY = midPointY;
		
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(true, 136, gameHeight);
		
		this.batch = new SpriteBatch();
		this.batch.setProjectionMatrix(this.camera.combined);
		this.shapeRenderer = new ShapeRenderer();
		this.shapeRenderer.setProjectionMatrix(this.camera.combined);
		
		// Call helper methods to initialize instance variables
		initGameObjects();
		initAssets();
	}
	
	public void render(float runTime) {
		
		Bird bird = world.getBird();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Begin ShapeRenderer
        shapeRenderer.begin(ShapeType.Filled);

        // Draw Background color
        shapeRenderer.setColor(76 / 255.0f, 191 / 255.0f, 200 / 255.0f, 1);
        shapeRenderer.rect(0, 0, 136, midPointY + 66);

        // Draw Grass
        shapeRenderer.setColor(221 / 255.0f, 216 / 255.0f, 152 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 66, 136, 11);

        // Draw Dirt
        shapeRenderer.setColor(221 / 255.0f, 216 / 255.0f, 152 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 77, 136, 52);

        // End ShapeRenderer
        shapeRenderer.end();
        
        // Begin SpriteBatch
        batch.begin();
        // Disable transparency
        // This is good for performance when drawing images that do not require
        // transparency.
        batch.disableBlending();
        batch.draw(AssetLoader.bg, 0, midPointY + 23, 136, 43);
        batch.draw(AssetLoader.grass, 0, midPointY + 66, 136, 45);

        // The bird needs transparency, so we enable that again.
        batch.enableBlending();

        if (bird.shouldntFlap()) {
        	batch.draw(birdMid, bird.getX(), bird.getY(), 
        			bird.getWidth() / 2.0f, bird.getHeight() / 2.0f, 
        			bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
        } else {
        	batch.draw(birdAnimation.getKeyFrame(runTime), bird.getX(), bird.getY(), 
        			bird.getWidth() / 2.0f, bird.getHeight() / 2.0f, 
        			bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
        }

        // End SpriteBatch
        batch.end();
	}
	
	private void initGameObjects() {
		bird = world.getBird();
	}
	
	private void initAssets() {
		bg = AssetLoader.bg;
		grass = AssetLoader.grass;
		birdAnimation = AssetLoader.birdAnimation;
		birdMid = AssetLoader.bird;
		birdUp = AssetLoader.birdUp;
		birdDown = AssetLoader.birdDown;
		skullUp = AssetLoader.skullUp;
		skullDown = AssetLoader.skullDown;
		bar = AssetLoader.bar;
	}
}
