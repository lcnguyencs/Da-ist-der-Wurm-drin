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
}