package Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * This class represents a Worm entity in the game. 
 * It contains the logic for moving the Worm, updating the game state, and rendering the Worm.
 */
public class Worm {
    // Declare necessary variables for the Worm
    public Texture snakeHead;
    public SpriteBatch batch;
    public float x;
    public float y;
    public String id;
    public int point;
    public boolean bonus;

    /**
     * Constructor for the Worm class. It initializes the Worm's properties.
     *
     * @param texturePath The path to the texture file for the Worm.
     * @param batch The SpriteBatch to be used for drawing the Worm.
     * @param x The x-coordinate of the Worm.
     * @param y The y-coordinate of the Worm.
     * @param id The unique identifier of the Worm.
     * @param point The initial point of the Worm.
     * @param bonus The bonus status of the Worm.
     */
    public Worm(String texturePath, SpriteBatch batch, float x, float y, String id, int point, boolean bonus){
        this.snakeHead = new Texture(texturePath);
        this.batch = batch;
        this.x = x;
        this.y = y;
        this.id = id;
        this.point = point;
        this.bonus = bonus;
    }

    /**
     * Returns the current point of the Worm.
     *
     * @return The current point of the Worm.
     */
    public int getPoint(){
        return this.point;
    }

    public void update(){
    }

    /**
     * Renders the Worm at its current position.
     */
    public void render(){
        batch.draw(snakeHead, x, y);
    }

    /**
     * Moves the Worm by a certain step.
     *
     * @param step The step to move the Worm.
     */
    public void move(int step){
        x = x + step * 20;
        point += step;
    }
}