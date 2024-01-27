package GameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
 * This class represents the end state of the game. 
 * It contains the logic for handling user input, updating the game state, and rendering the game.
 */
public class EndState implements Screen {
    // Declare necessary variables for the game state
    public Wurm game;
    public SpriteBatch batch;
    public float countTime = 0;
    public OrthographicCamera gamecamEnd;
    public Viewport gamePortEnd;
    public TmxMapLoader mapLoaderEnd;
    public TiledMap mapEnd;
    public OrthogonalTiledMapRenderer rendererEnd;
    public static float wmVolume = 4.0F;
    public static Music wm;
    public Texture newgameButton, exitButton, wood;
    public BitmapFont buttonFont;
    GlyphLayout layout = new GlyphLayout();
    private GameStateManager gsm;

    /**
     * Constructor for the EndState class. It initializes the GameStateManager and other necessary variables.
     *
     * @param game The current game instance.
     * @param gsm The GameStateManager for managing game states.
     */
    public EndState(Wurm game,GameStateManager gsm) {
        this.gsm = gsm;
        this.game = game;
        this.batch = Wurm.batch;
        gamecamEnd = new OrthographicCamera();
        gamePortEnd = new FitViewport(Wurm.V_WIDTH, Wurm.V_HEIGHT, gamecamEnd);
        //load map
        mapLoaderEnd = new TmxMapLoader();
        mapEnd = mapLoaderEnd.load("map/EndState/backgroundMenu.tmx");
        rendererEnd = new OrthogonalTiledMapRenderer(mapEnd);
        gamecamEnd.position.set(Wurm.V_WIDTH / 2, Wurm.V_HEIGHT / 2, 0);
        //load button
        GameStateManager.bgm.pause();
        wm = Gdx.audio.newMusic(Gdx.files.internal("music/winnerSound.mp3"));
        wm.setVolume(0.1F * wmVolume);
        wm.play();
        //load font
        newgameButton = new Texture("img/rectangleButtonBig.png");
        exitButton = new Texture("img/rectangleButton.png");
        wood = new Texture("img/MenuState/wood.png");

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
        if (Gdx.input.isTouched()) {
            System.out.println("Clicked");
        }
        // Update camera and renderer view
        gamecamEnd.update();
        rendererEnd.setView(gamecamEnd);
    }
    public void update(float dt){
        handleInput(dt);
    }

    /**
     * Renders the game state.
     *
     * @param delta The time delta since the last frame.
     */
    @Override
    public void render(float delta) {
        update(delta);
        countTime += delta;
        // Clear the screen
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Render the map
        rendererEnd.render();
        Wurm.batch.setProjectionMatrix(gamecamEnd.combined);
        // Render the buttons
        batch.begin();
        Wurm.batch.draw(wood,970,50);
        Wurm.batch.draw(newgameButton,870, 350);
        Wurm.batch.draw(exitButton, 900, 250);
        // Render the text
        String newgameText = "New game";
        layout.setText(buttonFont, newgameText);
        float textX = 870 + (newgameButton.getWidth() - layout.width) / 2;
        float textY = 350 + (newgameButton.getHeight() + layout.height) / 2;
        buttonFont.draw(Wurm.batch,newgameText,textX,textY);

        String exitText = "Exit";
        layout.setText(buttonFont, exitText);
        textX = 900 + (exitButton.getWidth() - layout.width) / 2;
        textY = 250 + (exitButton.getHeight() + layout.height) / 2;
        buttonFont.draw(Wurm.batch,exitText,textX,textY);


        batch.end();
        // Handle user input
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (x >= 870 && x <= 870 + 220 &&
                    y >= 350 && y <= 350 + 70) {
                if (wm.isPlaying()) {
                    wm.stop();
                }
                GameStateManager.bgm.play();
                gsm.push(new PlayState(game, gsm));
            }

            if (x >= 900 && x <= 900 + 160 &&
                    y >= 250 && y <= 250 + 70) {
                Gdx.app.exit();
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
        gamePortEnd.update(width, height);
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
