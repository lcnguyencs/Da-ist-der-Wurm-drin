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

public class SettingState implements Screen{
    public Wurm game;
    public SpriteBatch batch;
    public float countTime;
    public OrthographicCamera gamecamSetting;
    public Viewport gamePortSetting;

    public TmxMapLoader mapLoaderSetting;
    public TiledMap mapSetting;
    public OrthogonalTiledMapRenderer rendererSetting;
    public Texture volumeUpButton, volumeDownButton, doneButton;
    public GlyphLayout layout;
    public BitmapFont buttonFont, buttonFont1;

    private GameStateManager gsm;

    public SettingState(Wurm game, GameStateManager gsm) {
        this.gsm = gsm;

        this.game = game;
        this.batch = game.batch;
        gamecamSetting = new OrthographicCamera();
        gamePortSetting = new FitViewport(Wurm.V_WIDTH, Wurm.V_HEIGHT, gamecamSetting);

        mapLoaderSetting = new TmxMapLoader();
        mapSetting = mapLoaderSetting.load("map/backgroundMenu.tmx");
        rendererSetting = new OrthogonalTiledMapRenderer(mapSetting);
        gamecamSetting.position.set(Wurm.V_WIDTH / 2, Wurm.V_HEIGHT / 2, 0);

        volumeUpButton = new Texture("img/squareButton.png");
        volumeDownButton = new Texture("img/squareButton.png");
        doneButton = new Texture("img/PlayButtonNew.png");

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
        // Initialize your Setting resources here (buttons, backgrounds, etc.)
    }

    public void handleInput(float dt) {
        if (Gdx.input.isTouched()) {
            System.out.println("Clicked");
        }
        gamecamSetting.update();
        rendererSetting.setView(gamecamSetting);
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

        rendererSetting.render();
        game.batch.setProjectionMatrix(gamecamSetting.combined);

        game.batch.begin();
        game.batch.draw(volumeUpButton,900 - 80, 350);
        game.batch.draw(volumeDownButton,900 + 170, 350);
        game.batch.draw(doneButton,900, 250);

        String lefText = "+";
        layout.setText(buttonFont1, lefText);
        float textX = 900 - 80 + (volumeUpButton.getWidth() - layout.width) / 2;
        float textY = 350 + 5 + (volumeUpButton.getHeight() + layout.height) / 2;
        buttonFont1.draw(game.batch,lefText,textX,textY);

        String rightText = "-";
        layout.setText(buttonFont1, rightText);
        textX = 900 + 170 + (volumeDownButton.getWidth() - layout.width) / 2;
        textY = 350 + 5 + (volumeDownButton.getHeight() + layout.height) / 2;
        buttonFont1.draw(game.batch,rightText,textX,textY);

        String doneText = "Done";
        layout.setText(buttonFont, doneText);
        textX = 900 + (doneButton.getWidth() - layout.width) / 2;
        textY = 250 + (doneButton.getHeight() + layout.height) / 2;
        buttonFont.draw(game.batch,doneText,textX,textY);

        game.batch.end();

        if (Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (x >= 900 && x <= 900 + doneButton.getWidth() &&
                    y >= 250 && y <= 250 + doneButton.getHeight()) {
                //back to MenuState
                gsm.pop();
            }
            // Volume up button
            if (x >= 900 - 80 && x <= 900 - 80 + volumeUpButton.getWidth() &&
                    y >= 350 && y <= 350 + volumeUpButton.getHeight()) {
                GameStateManager.bgmVolume = Math.min(GameStateManager.bgmVolume + 0.1f, 1.0f); // Increase volume
                GameStateManager.bgm.setVolume(GameStateManager.bgmVolume); // Apply new volume
            }
            // Volume down button
            if (x >= 900 + 170 && x <= 900 + 170 + volumeDownButton.getWidth() &&
                    y >= 350 && y <= 350 + volumeDownButton.getHeight()) {
                GameStateManager.bgmVolume = Math.max(GameStateManager.bgmVolume - 0.1f, 0.0f); // Decrease volume
                GameStateManager.bgm.setVolume(GameStateManager.bgmVolume); // Apply new volume
            }
        }
    }

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
        //fontSetting.dispose();
        //batch.dispose();
        // Dispose of your Setting resources when they are no longer needed
    }

}