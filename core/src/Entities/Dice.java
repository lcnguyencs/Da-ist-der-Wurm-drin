package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Music;
import GameState.PlayState;

/**
 * This class represents a Dice entity in the game. 
 * It contains the logic for rolling the dice, updating the game state, rendering the dice, and playing sounds.
 */
public class Dice {
    // Declare necessary variables for the Dice
    public Texture[] texture;
    public SpriteBatch batch;
    public float x;
    public float y;
    public static int currentNumber = 0;
    public static int turn = 0;
    public static float dmVolume = 10.0F;
    public static Music dm;

    /**
     * Constructor for the Dice class. It initializes the Dice's properties.
     *
     * @param batch The SpriteBatch to be used for drawing the Dice.
     * @param x The x-coordinate of the Dice.
     * @param y The y-coordinate of the Dice.
     */
    public Dice(SpriteBatch batch, float x, float y){
        texture = new Texture[6];
        for (int i = 0; i < 6; i++){
            texture[i] = new Texture("img/PlayState/dices/Dice " + (i + 1) + ".png");
        }
        this.batch = batch;
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the current turn number.
     *
     * @return The current turn number.
     */
    public int getTurn(){
        return turn;
    }

    /**
     * Updates the game state based on user input.
     */
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

    /**
     * Renders the Dice at its current position.
     */
    public void render(){
        batch.draw(texture[(currentNumber + 5) % 6], x, y);
    }

    /**
     * Plays the dice rolling sound.
     */
    public void sound(){
        dm = Gdx.audio.newMusic(Gdx.files.internal("music/diceSound.mp3"));
        dm.setVolume(0.1F * dmVolume);
        dm.play();
    }

    /**
     * Rolls the dice.
     *
     * @return The rolled number.
     */
    public int roll(){

        return (int)(Math.random() * 6) % 6;
    }

    public void dispose(){
    }
}
