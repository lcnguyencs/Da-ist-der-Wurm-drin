package Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * This class represents a Tick entity in the game. 
 * It contains the logic for updating and rendering the Tick.
 */
public class Tick {
    // Declare necessary variables for the Tick
    public Texture tick;
    public SpriteBatch batch;
    public float x;
    public float y;
    public int id;
    public boolean status;

    /**
     * Constructor for the Tick class. It initializes the Tick's properties.
     *
     * @param texturePath The path to the texture file for the Tick.
     * @param batch The SpriteBatch to be used for drawing the Tick.
     * @param x The x-coordinate of the Tick.
     * @param y The y-coordinate of the Tick.
     * @param id The unique identifier of the Tick.
     * @param status The status of the Tick.
     */
    public Tick(String texturePath, SpriteBatch batch, float x, float y, int id,boolean status){
        this.tick = new Texture(texturePath);
        this.batch = batch;
        this.x = x;
        this.y = y;
        this.id = id;
        this.status = status;
    }

    public void update(){
    }

    /**
     * Renders the Tick at its current position.
     */
    public void render(){
        batch.draw(tick, x, y);
    }

    /**
     * Checks whether the mouse pointer is within the bounds of the Tick.
     *
     * @param mouse_x The x-coordinate of the mouse pointer.
     * @param mouse_y The y-coordinate of the mouse pointer.
     * @return True if the mouse pointer is within the bounds of the Tick, false otherwise.
     */
    public boolean check(float mouse_x, float mouse_y){
        return (mouse_x >= x && mouse_y >= y) && (mouse_x <= x + 64 && mouse_y <= y + 64);
    }
}