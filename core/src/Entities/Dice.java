package Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * This class represents a Box entity in the game. 
 * It contains the logic for updating and rendering the Box.
 */
public class Box {
    // Declare necessary variables for the Box
    public Texture box;
    public SpriteBatch batch;
    public float x;
    public float y;
    public int id;
    public boolean status;

    /**
     * Constructor for the Box class. It initializes the Box's properties.
     *
     * @param texturePath The path to the texture file for the Box.
     * @param batch The SpriteBatch to be used for drawing the Box.
     * @param x The x-coordinate of the Box.
     * @param y The y-coordinate of the Box.
     * @param id The unique identifier of the Box.
     * @param status The status of the Box.
     */
    public Box(String texturePath, SpriteBatch batch, float x, float y, int id, boolean status){
        this.box = new Texture(texturePath);
        this.batch = batch;
        this.x = x;
        this.y = y;
        this.id = id;
        this.status = status;

    }
    /**
     * Updates the Box. 
     * Currently, there is no implementation in this method.
     */
    public void update(){
    }

    /**
     * Renders the Box at its current position.
     */
    public void render(){
        batch.draw(box, x, y);
    }
}