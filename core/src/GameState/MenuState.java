package GameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wurm.game.Wurm;

import Manager.GameStateManager;

/**
 * This class represents the menu state of the game. 
 * It contains the logic for handling user input, updating the game state, and rendering the game.
 */
public class MenuState implements Screen {
    // Declare necessary variables for the menu state   
    public Wurm game;
    public SpriteBatch batch;
    public float countTime;
    public OrthographicCamera gamecamMenu;
    public Viewport gamePortMenu;
    public TmxMapLoader mapLoaderMenu;
    public TiledMap mapMenu;
    public OrthogonalTiledMapRenderer rendererMenu;
    public Texture playButton, exitButton, settingButton, wood;
    public BitmapFont buttonFont;
    private GameStateManager gsm;

    /**
     * Constructor for the MenuState class. It initializes the GameStateManager and other necessary variables.
     *
     * @param game The current game instance.
     * @param gsm The GameStateManager for managing menu states.
     */
    public MenuState(Wurm game,  GameStateManager gsm) {
        this.gsm = gsm;
        this.game = game;
        this.batch = Wurm.batch;
        //load map
        gamecamMenu = new OrthographicCamera();
        gamePortMenu = new FitViewport(Wurm.V_WIDTH, Wurm.V_HEIGHT, gamecamMenu);
        mapLoaderMenu = new TmxMapLoader();
        mapMenu = mapLoaderMenu.load("map/MenuState/backgroundMenu.tmx");
        rendererMenu = new OrthogonalTiledMapRenderer(mapMenu);
        gamecamMenu.position.set(Wurm.V_WIDTH / 2, Wurm.V_HEIGHT / 2, 0);
        //load button
        playButton = new Texture("img/rectangleButton.png");
        settingButton = new Texture("img/rectangleButton.png");
        exitButton = new Texture("img/rectangleButton.png");
        wood = new Texture("img/MenuState/wood.png");
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
    }

    @Override
    public void show() {
    }

    /**
     * Handles user input. If the screen is touched, it prints "Clicked" to the console.
     *
     * @param dt The time delta since the last frame.
     */
    public void handleInput(float dt) {
        // Update camera and renderer view
        gamecamMenu.update();
        rendererMenu.setView(gamecamMenu);
    }

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

        rendererMenu.render();
        Wurm.batch.setProjectionMatrix(gamecamMenu.combined);

        Wurm.batch.begin();
            //draw button
            Wurm.batch.draw(wood,970,50);
            Wurm.batch.draw(playButton,900, 350);
            Wurm.batch.draw(settingButton,900, 250);
            Wurm.batch.draw(exitButton,900, 150);
            //draw text
            String playText = "Play";
            GlyphLayout layout = new GlyphLayout();
            layout.setText(buttonFont, playText);
            float textX = 900 + (playButton.getWidth() - layout.width) / 2;
            float textY = 350 + (playButton.getHeight() + layout.height) / 2;
            buttonFont.draw(Wurm.batch,playText,textX,textY);

            String settingText = "Volume";
            layout.setText(buttonFont, settingText);
            textX = 900 + (settingButton.getWidth() - layout.width) / 2;
            textY = 250 + (settingButton.getHeight() + layout.height) / 2;
            buttonFont.draw(Wurm.batch,settingText,textX,textY);

            String exitText = "Exit";
            layout.setText(buttonFont, exitText);
            textX = 900 + (exitButton.getWidth() - layout.width) / 2;
            textY = 150 + (exitButton.getHeight() + layout.height) / 2;
            buttonFont.draw(Wurm.batch,exitText,textX,textY);

        Wurm.batch.end();
        // Handle user input
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (x >= 900 && x <= 900 + 160 &&
                    y >= 350 && y <= 350 + 70) {
                //game.setScreen(new PlayState(game));
                gsm.push(new PlayState(game, gsm));
            }

            if (x >= 900 && x <= 900 + settingButton.getWidth() &&
                    y >= 250 && y <= 250 + settingButton.getHeight()) {
                //game.setScreen(new SettingState(game));
                gsm.push(new SettingState(game, gsm));
            }

            if (x >= 900 && x <= 900 + 160 &&
                    y >= 150 && y <= 150 + 70) {
                //Exit the game
                Gdx.app.exit();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        gamePortMenu.update(width, height);
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