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
}