package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Music;

import GameState.PlayState;

public class Dice {
    public Texture[] texture;
    public SpriteBatch batch;
    public float x;

    public float y;
    public static int currentNumber = 0;
    public static int turn = 0;
    public static float dmVolume = 10.0F;
    public static Music dm;
    public Dice(SpriteBatch batch, float x, float y){
        texture = new Texture[6];
        for (int i = 0; i < 6; i++){
            texture[i] = new Texture("img/PlayState/dices/Dice " + (i + 1) + ".png");
        }
        this.batch = batch;
        this.x = x;
        this.y = y;
    }
}