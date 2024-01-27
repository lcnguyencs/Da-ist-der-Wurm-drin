package com.wurm.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import GameState.MenuState;
import Managers.GameStateManager;

public class Wurm extends Game {
    public static final int V_WIDTH = 1280;
    public static final int V_HEIGHT = 720;
    public static SpriteBatch batch;
    public GameStateManager gsm;
    @Override
    public void create () {
        batch = new SpriteBatch();
        gsm = new GameStateManager(this);
        gsm.push(new MenuState(this, gsm)); // Pass the GameStateManager to MenuState
    }
    @Override
    public void render () {
        super.render();
    }
}
