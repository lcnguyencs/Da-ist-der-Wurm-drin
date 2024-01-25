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

public class SettingState implements Screen{
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

    public SettingState(Wurm game, GameStateManager gsm) {
        this.batch = game.batch;
        gamecamSetting = new OrthographicCamera();

        mapLoaderSetting = new TmxMapLoader();
        mapSetting = mapLoaderSetting.load("map/backgroundMenu.tmx");
        rendererSetting = new OrthogonalTiledMapRenderer(mapSetting);
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
}