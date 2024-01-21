package Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Worm {
    public Texture snakeHead;
    public SpriteBatch batch;
    public float x;
    public float y;
    public String id;
    public int point;
    public boolean bonus;

    public Worm(String texturePath, SpriteBatch batch, float x, float y, String id, int point, boolean bonus){
        this.snakeHead = new Texture(texturePath);
        this.batch = batch;
        this.x = x;
        this.y = y;
        this.id = id;
        this.point = point;
        this.bonus = bonus;
    }
}