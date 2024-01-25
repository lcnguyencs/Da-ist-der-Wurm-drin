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
        
        FreeTypeFontGenerator.FreeTypeFontParameter param_fontButton = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param_fontButton.size = 40;
        param_fontButton.borderWidth = 1;
        param_fontButton.borderColor = Color.BLACK;
        buttonFont = generator.generateFont(param_fontButton);
        buttonFont.setColor(Color.RED);
    }
}