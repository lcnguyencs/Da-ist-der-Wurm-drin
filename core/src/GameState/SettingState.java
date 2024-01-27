package GameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wurm.game.Wurm;

import Manager.GameStateManager;
/**
 * This class represents the settings state of the game. 
 * It contains the logic for handling user input, updating the game state, and rendering the game.
 */
public class SettingState implements Screen{
    // Declare necessary variables for the game state
    public Wurm game;
    public SpriteBatch batch;
    public float countTime;
    public OrthographicCamera gamecamSetting;
    public Viewport gamePortSetting;
    public TmxMapLoader mapLoaderSetting;
    public TiledMap mapSetting;
    public OrthogonalTiledMapRenderer rendererSetting;
    public BitmapFont fontSetting;
    public Texture volumeUpButton, volumeDownButton, doneButton;
    public GlyphLayout layout;
    public BitmapFont buttonFont, buttonFont1;
    private GameStateManager gsm;

    /**
     * Constructor for the SettingState class. It initializes the GameStateManager and other necessary variables.
     *
     * @param game The current game instance.
     * @param gsm The GameStateManager for managing game states.
     */
    public SettingState(Wurm game, GameStateManager gsm) {
        this.gsm = gsm;
        this.game = game;
        this.batch = Wurm.batch;
        gamecamSetting = new OrthographicCamera();
        gamePortSetting = new FitViewport(Wurm.V_WIDTH, Wurm.V_HEIGHT, gamecamSetting);
        //load map
        mapLoaderSetting = new TmxMapLoader();
        mapSetting = mapLoaderSetting.load("map/SettingState/backgroundMenu.tmx");
        rendererSetting = new OrthogonalTiledMapRenderer(mapSetting);
        gamecamSetting.position.set(Wurm.V_WIDTH / 2, Wurm.V_HEIGHT / 2, 0);
        //load button
        volumeUpButton = new Texture("img/squareButton.png");
        volumeDownButton = new Texture("img/squareButton.png");
        doneButton = new Texture("img/rectangleButton.png");
        //load font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("font/KOMTXT__.ttf")
        );
        FreeTypeFontGenerator.FreeTypeFontParameter param_fontButton = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param_fontButton.size = 40;
        param_fontButton.borderWidth = 1;
        param_fontButton.borderColor = Color.BLACK;
        buttonFont = generator.generateFont(param_fontButton);
        buttonFont.setColor(Color.RED);

        FreeTypeFontGenerator.FreeTypeFontParameter param_fontButton1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param_fontButton1.size = 60;
        param_fontButton1.borderWidth = 1;
        param_fontButton1.borderColor = Color.BLACK;
        buttonFont1 = generator.generateFont(param_fontButton1);
        buttonFont1.setColor(Color.RED);
    }

    @Override
    public void show() {
    }

    /**
     * Handles user input. If the left mouse button is clicked, it prints "Clicked" to the console.
     *
     * @param dt The time delta since the last frame.
     */
    public void handleInput(float dt) {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            System.out.println("Clicked");
        }
        // Update camera and renderer view
        gamecamSetting.update();
        rendererSetting.setView(gamecamSetting);
    }

    /**
     * Updates the setting state.
     *
     * @param dt The time delta since the last frame.
     */
    public void update(float dt){
        handleInput(dt);
    }
    
    /**
     * Renders the setting state.
     *
     * @param delta The time delta since the last frame.
     */
    @Override
    public void render(float delta) {
        update(delta);
        countTime += delta;
        //clear screen
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        rendererSetting.render();
        Wurm.batch.setProjectionMatrix(gamecamSetting.combined);

        Wurm.batch.begin();
        Wurm.batch.draw(volumeUpButton,900 - 80, 350);
        Wurm.batch.draw(volumeDownButton,900 + 170, 350);
        Wurm.batch.draw(doneButton,900, 250);


        String lefText = "+";
        GlyphLayout layout = new GlyphLayout();
        layout.setText(buttonFont1, lefText);
        float textX = 900 - 80 + (volumeUpButton.getWidth() - layout.width) / 2; // Center X
        float textY = 350 + 5 + (volumeUpButton.getHeight() + layout.height) / 2;
        buttonFont1.draw(Wurm.batch,lefText,textX,textY);

        String rightText = "-";
        layout.setText(buttonFont1, rightText);
        textX = 900 + 170 + (volumeDownButton.getWidth() - layout.width) / 2; // Center X
        textY = 350 + 5 + (volumeDownButton.getHeight() + layout.height) / 2;
        buttonFont1.draw(Wurm.batch,rightText,textX,textY);

        String doneText = "Done";
        layout.setText(buttonFont, doneText);
        textX = 900 + (doneButton.getWidth() - layout.width) / 2; // Center X
        textY = 250 + (doneButton.getHeight() + layout.height) / 2;
        buttonFont.draw(Wurm.batch,doneText,textX,textY);


        Wurm.batch.end();

        if (Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (x >= 900 && x <= 900 + doneButton.getWidth() &&
                    y >= 250 && y <= 250 + doneButton.getHeight()) {
                gsm.pop();
            }
            // Volume up button
            if (x >= 900 - 80 && x <= 900 - 80 + volumeUpButton.getWidth() &&
                    y >= 350 && y <= 350 + volumeUpButton.getHeight()) {
                GameStateManager.bgmVolume = Math.min(GameStateManager.bgmVolume + 0.1f, 1.0f);
                GameStateManager.bgm.setVolume(GameStateManager.bgmVolume);
            }

            // Volume down button
            if (x >= 900 + 170 && x <= 900 + 170 + volumeDownButton.getWidth() &&
                    y >= 350 && y <= 350 + volumeDownButton.getHeight()) {
                GameStateManager.bgmVolume = Math.max(GameStateManager.bgmVolume - 0.1f, 0.0f); // Decrease volume
                GameStateManager.bgm.setVolume(GameStateManager.bgmVolume);
            }
        }
    }

    /**
     * Resizes the game viewport when the window size changes.
     *
     * @param width The new window width.
     * @param height The new window height.
     */
    @Override
    public void resize(int width, int height) {
        gamePortSetting.update(width, height);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
    }

}