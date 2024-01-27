package Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Game;
import java.util.Stack;

public class GameStateManager {
    private final Game game;
    private final Stack<com.badlogic.gdx.Screen> screens;

    public static Music bgm;
    public static float bgmVolume = 0.5f;

    public GameStateManager(Game game) {
        this.game = game;
        this.screens = new Stack<>();
        bgm = Gdx.audio.newMusic(Gdx.files.internal("music/backgroundSound.mp3"));
        bgm.setLooping(true);
        bgm.setVolume(bgmVolume);
        bgm.play();
    }

    public void set(com.badlogic.gdx.Screen screen) {
        if (!screens.isEmpty()) {
            screens.pop().dispose();
        }
        screens.push(screen);
        game.setScreen(screen);
    }

    public void push(com.badlogic.gdx.Screen screen) {
        screens.push(screen);
        game.setScreen(screen);
    }

    public void pop() {
        com.badlogic.gdx.Screen screen = screens.pop();
        screen.dispose();
        if (!screens.isEmpty()) {
            game.setScreen(screens.peek());
        }
    }

    public void popAndDisposeAll() {
        while (!screens.isEmpty()) {
            screens.pop().dispose();
        }
    }

    public com.badlogic.gdx.Screen peek() {
        return screens.peek();
    }
}