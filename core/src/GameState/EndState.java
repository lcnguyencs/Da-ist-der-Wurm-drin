package GameState;

import com.badlogic.gdx.Gdx;
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
import com.lcnguyencs.src.Wurm;

import Managers.GameStateManager;

public class EndState implements Screen {
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
    public Texture newgameButton, exitButton;
    public BitmapFont buttonFont;
    GlyphLayout layout = new GlyphLayout();
    private GameStateManager gsm;
    public EndState(Wurm game,GameStateManager gsm) {
        this.gsm = gsm;
        this.game = game;
        this.batch = game.batch;
        gamecamEnd = new OrthographicCamera();
        gamePortEnd = new FitViewport(Wurm.V_WIDTH, Wurm.V_HEIGHT, gamecamEnd);

        mapLoaderEnd = new TmxMapLoader();
        mapEnd = mapLoaderEnd.load("map/backgroundEnd.tmx");
        rendererEnd = new OrthogonalTiledMapRenderer(mapEnd);
        gamecamEnd.position.set(Wurm.V_WIDTH / 2, Wurm.V_HEIGHT / 2, 0);

        GameStateManager.bgm.pause();
        wm = Gdx.audio.newMusic(Gdx.files.internal("music/winnerSound.mp3"));
        wm.setVolume(0.1F * wmVolume);
        wm.play();

        newgameButton = new Texture("img/PlayButtonNew1.png");
        exitButton = new Texture("img/PlayButtonNew.png");
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
        // Initialize your End resources here (buttons, backgrounds, etc.)
    }

    public void handleInput(float dt) {
        if (Gdx.input.isTouched()) {
            System.out.println("Clicked");
        }
        gamecamEnd.update();
        rendererEnd.setView(gamecamEnd);
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

        rendererEnd.render();
        game.batch.setProjectionMatrix(gamecamEnd.combined);

        batch.begin();

        game.batch.draw(newgameButton,770, 350);
        game.batch.draw(exitButton, 800, 250);

        String newgameText = "New game";
        layout.setText(buttonFont, newgameText);
        float textX = 770 + (newgameButton.getWidth() - layout.width) / 2;
        float textY = 350 + (newgameButton.getHeight() + layout.height) / 2;
        buttonFont.draw(game.batch,newgameText,textX,textY);

        String exitText = "Exit";
        layout.setText(buttonFont, exitText);
        textX = 800 + (exitButton.getWidth() - layout.width) / 2;
        textY = 250 + (exitButton.getHeight() + layout.height) / 2;
        buttonFont.draw(game.batch,exitText,textX,textY);


        batch.end();

        if (Gdx.input.isTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (x >= 770 && x <= 800 + 220 &&
                    y >= 350 && y <= 350 + 70) {
                if (wm.isPlaying()) {
                    wm.stop();
                }
                GameStateManager.bgm.play();
                gsm.push(new PlayState(game, gsm));
            }

            if (x >= 800 && x <= 800 + 160 &&
                    y >= 250 && y <= 250 + 70) {
                Gdx.app.exit();
            }

        }
    }

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
        //fontEnd.dispose();
        //batch.dispose();
        // Dispose of your End resources when they are no longer needed
    }
}