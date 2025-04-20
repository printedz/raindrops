package org.printed.drop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {
    // Class fields for resources to avoid recreation and memory leaks
    private Texture backgroundTexture;
    private Texture bucketTexture;
    private Texture dropTexture;
    private Sound dropSound;
    private Music music;
    private SpriteBatch spriteBatch;
    private FitViewport viewport;
    private OrthographicCamera camera;
    Sprite bucketSprite;

    @Override
    public void create() {
        try {
            // Initialize camera first
            camera = new OrthographicCamera();
            camera.setToOrtho(false, 8, 5);

            // Load texture resources
            backgroundTexture = new Texture(Gdx.files.internal("background.png"));
            bucketTexture = new Texture(Gdx.files.internal("bucket.png"));
            dropTexture = new Texture(Gdx.files.internal("drop.png"));

            // Load audio resources - with error handling
            try {
                dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav")); // Changed to .wav format
            } catch (Exception e) {
                Gdx.app.log("Audio Error", "Could not load drop sound: " + e.getMessage());
                dropSound = null; // Set to null to prevent null pointer exceptions
            }

            try {
                music = Gdx.audio.newMusic(Gdx.files.internal("music.wav")); // Changed to .wav format
            } catch (Exception e) {
                Gdx.app.log("Audio Error", "Could not load music: " + e.getMessage());
                music = null; // Set to null to prevent null pointer exceptions
            }

            // Create sprite batch
            spriteBatch = new SpriteBatch();

            // Create viewport with the camera
            viewport = new FitViewport(8, 5, camera);

        } catch (Exception e) {
            Gdx.app.error("Game Initialization", "Error during game initialization", e);
        }
        bucketSprite = new Sprite(bucketTexture); // Initialize the sprite based on the texture
        bucketSprite.setSize(1, 1); // Define the size of the sprite
    }

    @Override
    public void resize(int width, int height) {
        if (viewport != null) {
            viewport.update(width, height, true); // true centers the camera
        }
    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }

    private void input() {
        float speed = .25f;
        float delta = Gdx.graphics.getDeltaTime(); // retrieve the current delta

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            bucketSprite.translateX(speed * delta); // Move the bucket right
        }
    }

    private void logic() {
        // Your game logic here
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        bucketSprite.draw(spriteBatch); // Sprites have their own draw method

        spriteBatch.end();
    }




    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void dispose() {
        // Properly dispose all resources to prevent memory leaks
        if (backgroundTexture != null) backgroundTexture.dispose();
        if (bucketTexture != null) bucketTexture.dispose();
        if (dropTexture != null) dropTexture.dispose();
        if (dropSound != null) dropSound.dispose();
        if (music != null) music.dispose();
        if (spriteBatch != null) spriteBatch.dispose();
    }
}
