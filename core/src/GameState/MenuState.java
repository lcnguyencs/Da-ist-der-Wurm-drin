package GameState;

import com.badlogic.gdx.Gdx;
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

public class MenuState implements Screen {

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

    public MenuState(Wurm game,  GameStateManager gsm) {

        this.gsm = gsm;

        this.game = game;
        this.batch = game.batch;
        gamecamMenu = new OrthographicCamera();
        gamePortMenu = new FitViewport(Wurm.V_WIDTH, Wurm.V_HEIGHT, gamecamMenu);

        mapLoaderMenu = new TmxMapLoader();
        mapMenu = mapLoaderMenu.load("map/backgroundMenu.tmx");
        rendererMenu = new OrthogonalTiledMapRenderer(mapMenu);
        gamecamMenu.position.set(Wurm.V_WIDTH / 2, Wurm.V_HEIGHT / 2, 0);

        playButton = new Texture("img/PlayButtonNew.png");
        settingButton = new Texture("img/PlayButtonNew.png");
        exitButton = new Texture("img/PlayButtonNew.png");
        wood = new Texture("img/wood.png");

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
        // Initialize your menu resources here (buttons, backgrounds, etc.)
    }

    public void handleInput(float dt) {
        if (Gdx.input.isTouched()) {
            System.out.println("Clicked");
        }
        gamecamMenu.update();
        rendererMenu.setView(gamecamMenu);
    }
    public void update(float dt){
        handleInput(dt);
    }

    @Override
    public void render(float delta) {
        update(delta);
        countTime += delta;

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        rendererMenu.render();
        game.batch.setProjectionMatrix(gamecamMenu.combined);

        game.batch.begin();

            game.batch.draw(wood,970,50);
            game.batch.draw(playButton,900, 350);
            game.batch.draw(settingButton,900, 250);
            game.batch.draw(exitButton,900, 150);

            String playText = "Play";
            GlyphLayout layout = new GlyphLayout();
            layout.setText(buttonFont, playText);
            float textX = 900 + (playButton.getWidth() - layout.width) / 2;
            float textY = 350 + (playButton.getHeight() + layout.height) / 2;
            buttonFont.draw(game.batch,playText,textX,textY);

            String settingText = "Setting";
            layout.setText(buttonFont, settingText);
            textX = 900 + (settingButton.getWidth() - layout.width) / 2;
            textY = 250 + (settingButton.getHeight() + layout.height) / 2;
            buttonFont.draw(game.batch,settingText,textX,textY);

            String exitText = "Exit";
            layout.setText(buttonFont, exitText);
            textX = 900 + (exitButton.getWidth() - layout.width) / 2;
            textY = 150 + (exitButton.getHeight() + layout.height) / 2;
            buttonFont.draw(game.batch,exitText,textX,textY);

        game.batch.end();

        if (Gdx.input.justTouched()) {
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