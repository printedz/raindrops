package org.printed.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

    final Drop game;
    private Texture backgroundTexture;
    private Rectangle playButton;
    private GlyphLayout glyphLayout;
    private final Color buttonColor = new Color(0.5f, 0.5f, 0.5f, 1); // Grey color
    private Vector3 touchPos = new Vector3();
    private Texture buttonTexture;

    public MainMenuScreen(final Drop game) {
        this.game = game;

        // Load the background texture
        backgroundTexture = new Texture(Gdx.files.internal("mainbackground.png"));

        // Create a glyph layout for measuring text dimensions
        glyphLayout = new GlyphLayout();
        glyphLayout.setText(game.font, "Play");

        // Create a play button (position and size will be set in resize())
        playButton = new Rectangle();
        playButton.width = 2f; // Width in world units
        playButton.height = 0.8f; // Height in world units

        // Create a button texture (grey 1x1 pixel)
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(buttonColor);
        pixmap.fill();
        buttonTexture = new Texture(pixmap);
        pixmap.dispose();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();

        // Draw background
        float bgWidth = game.viewport.getWorldWidth();
        float bgHeight = game.viewport.getWorldHeight();
        game.batch.draw(backgroundTexture, 0, 0, bgWidth, bgHeight);

        // Draw button background (grey rectangle)
        game.batch.draw(buttonTexture, playButton.x, playButton.y,
            playButton.width, playButton.height);

        // Draw button text
        float textX = playButton.x + (playButton.width - glyphLayout.width) / 2;
        float textY = playButton.y + (playButton.height + glyphLayout.height) / 2;
        game.font.draw(game.batch, "Play", textX, textY);

        game.batch.end();

        // Handle button click
        if (Gdx.input.justTouched()) {
            // Convert screen coordinates to world coordinates
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.viewport.unproject(touchPos);

            // Check if the play button was clicked
            if (playButton.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new GameScreen(game));
                dispose();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);

        // Center the play button
        playButton.x = (game.viewport.getWorldWidth() - playButton.width) / 2;
        playButton.y = (game.viewport.getWorldHeight() - playButton.height) / 2;
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        buttonTexture.dispose();
    }
}
