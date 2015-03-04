package com.yuri.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.yuri.gameobjects.Bird;
import com.yuri.gameobjects.Grass;
import com.yuri.gameobjects.Pipe;
import com.yuri.gameobjects.ScrollHandler;
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
	private ScrollHandler scroller;
	private Grass frontGrass, backGrass;
	private Pipe pipe1, pipe2, pipe3;
	
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
		this.camera.setToOrtho(true, 136, this.gameHeight);
		
		this.batch = new SpriteBatch();
		this.batch.setProjectionMatrix(this.camera.combined);
		this.shapeRenderer = new ShapeRenderer();
		this.shapeRenderer.setProjectionMatrix(this.camera.combined);
		
		// Call helper methods to initialize instance variables
		initGameObjects();
		initAssets();
	}
	
	public void render(float runTime) {
		
		bird = world.getBird();

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

        // 1. Draw Grass
        drawGrass();
        batch.enableBlending();
        // 2. Draw Pipes
        drawPipes();
        // 3. Draw Skulls
        drawSkulls();

        if (bird.shouldntFlap()) {
        	batch.draw(birdMid, bird.getX(), bird.getY(), 
        			bird.getWidth() / 2.0f, bird.getHeight() / 2.0f, 
        			bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
        } else {
        	batch.draw(birdAnimation.getKeyFrame(runTime), bird.getX(), bird.getY(), 
        			bird.getWidth() / 2.0f, bird.getHeight() / 2.0f, 
        			bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
        }

        // TODO: TEMPORARY CODE! Fix this section later

        if (world.isReady()) {
            AssetLoader.shadow.draw(batch, "Touch me", 136 / 2 - 42, 76);
            AssetLoader.font.draw(batch, "Touch me", 136 / 2 - 42 - 1, 75);
        } else {
            if (world.isGameOver() || world.isHighScore()) {
                if (world.isGameOver()) {
                    AssetLoader.shadow.draw(batch, "Game Over", 25, 56);
                    AssetLoader.font.draw(batch, "Game Over", 24, 55);

                    AssetLoader.shadow.draw(batch, "High Score:", 23, 106);
                    AssetLoader.font.draw(batch, "High Score:", 22, 105);

                    String highScore = AssetLoader.getHighScore() + "";
                    AssetLoader.shadow.draw(batch, highScore,
                            136 / 2 - 3 * highScore.length(), 128);
                    AssetLoader.font.draw(batch, highScore,
                            136 / 2 - 3 * highScore.length() - 1, 127);
                } else {
                    AssetLoader.shadow.draw(batch, "High Score!", 19, 56);
                    AssetLoader.font.draw(batch, "High Score!", 18, 55);
                }

                AssetLoader.shadow.draw(batch, "Try again?", 24, 76);
                AssetLoader.font.draw(batch, "Try again?", 23, 75);
            }

            // Draw score
            String score = world.getScore() + "";
            AssetLoader.shadow.draw(batch, score, (136 / 2) - (3 * score.length()), 12);
            AssetLoader.font.draw(batch, score, (136 / 2) - (3 * score.length()), 11);
        }

        // End SpriteBatch
        batch.end();
    }
	
	private void initGameObjects() {
		bird = world.getBird();
		scroller = world.getScroller();
		frontGrass = scroller.getFrontGrass();
		backGrass = scroller.getBackGrass();
		pipe1 = scroller.getPipe1();
		pipe2 = scroller.getPipe2();
		pipe3 = scroller.getPipe3();
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
	
	private void drawGrass() {
		batch.draw(grass, frontGrass.getX(), frontGrass.getY(), 
				frontGrass.getWidth(), frontGrass.getHeight());
		batch.draw(grass, backGrass.getX(), backGrass.getY(), 
				backGrass.getWidth(), backGrass.getHeight());
	}
	
	private void drawSkulls() {
        // TODO: TEMPORARY CODE! Fix this section later

		batch.draw(skullUp, pipe1.getX() - 1, 
				pipe1.getY() + pipe1.getHeight() -14, 24 , 14);
		batch.draw(skullDown, pipe1.getX() - 1, 
				pipe1.getY() + pipe1.getHeight() +45, 24 , 14);

		batch.draw(skullUp, pipe2.getX() - 1,
				pipe2.getY() + pipe2.getHeight() -14, 24 , 14);
		batch.draw(skullDown, pipe2.getX() - 1, 
				pipe2.getY() + pipe2.getHeight() +45, 24 , 14);

		batch.draw(skullUp, pipe3.getX() - 1, 
				pipe3.getY() + pipe3.getHeight() -14, 24 , 14);
		batch.draw(skullDown, pipe3.getX() - 1, 
				pipe3.getY() + pipe3.getHeight() +45, 24 , 14);
	}
	
	private void drawPipes() {
        // TODO: TEMPORARY CODE! Fix this section later

		batch.draw(bar, pipe1.getX(), pipe1.getY(), 
				pipe1.getWidth(), pipe1.getHeight());
		batch.draw(bar, pipe1.getX(), pipe1.getY() + pipe1.getHeight() + 45, 
				pipe1.getWidth(), midPointY + 66 - (pipe1.getHeight() + 45));
		
		batch.draw(bar, pipe2.getX(), pipe2.getY(), 
				pipe2.getWidth(), pipe2.getHeight());
		batch.draw(bar, pipe2.getX(), pipe2.getY() + pipe2.getHeight() + 45, 
				pipe2.getWidth(), midPointY + 66 - (pipe2.getHeight() + 45));
		
		batch.draw(bar, pipe3.getX(), pipe3.getY(), 
				pipe3.getWidth(), pipe3.getHeight());
		batch.draw(bar, pipe3.getX(), pipe3.getY() + pipe3.getHeight() + 45, 
				pipe3.getWidth(), midPointY + 66 - (pipe3.getHeight() + 45));
	}
}
