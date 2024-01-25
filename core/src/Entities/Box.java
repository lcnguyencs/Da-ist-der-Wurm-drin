package Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Box {
    public Texture box;
    public SpriteBatch batch;
    public float x;
    public float y;
    public int id;
    public boolean status;
    public Box(String texturePath, SpriteBatch batch, float x, float y, int id, boolean status){
        this.box = new Texture(texturePath);
        this.batch = batch;
        this.x = x;
        this.y = y;
        this.id = id;
        this.status = status;

    }
    public void update(){
    }
    public void render(){
        batch.draw(box, x, y);
    }
}