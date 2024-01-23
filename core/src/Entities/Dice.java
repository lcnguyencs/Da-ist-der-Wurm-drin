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

    public int getTurn(){
        return turn;
    }
    public void update(){
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            float mouse_x = Gdx.input.getX();
            float mouse_y = Gdx.graphics.getHeight() - Gdx.input.getY();
            System.out.println(mouse_x + " " + mouse_y);
            if ((mouse_x >= x && mouse_y >= y) && ((mouse_x <= x+texture[0].getWidth()) && (mouse_y <= y + texture[0].getHeight())))
            {
                currentNumber = roll() + 1;
                sound();
                PlayState.worms[turn].move(currentNumber);
                turn = (turn + 1) % 4;
            }
            for (int i = 0; i < 4; i++){
                System.out.println("Point of snake " + i + " = " + PlayState.worms[i].getPoint());
            }
        }
    }

    public void render(){
        batch.draw(texture[(currentNumber + 5) % 6], x, y);
    }

    public void sound(){
        dm = Gdx.audio.newMusic(Gdx.files.internal("music/diceSound.mp3"));
        dm.setVolume(0.1F * dmVolume);
        dm.play();
    }
    public int roll(){

        return (int)(Math.random() * 6) % 6;
    }
    public void dispose(){
        // Dispose of the Texture array elements
        for (Texture dieTexture : texture) {
            if (dieTexture != null) dieTexture.dispose();
        }

        // Dispose of the Music object if it's been initialized
        if (dm != null) {
            dm.dispose();
        }
    }
}