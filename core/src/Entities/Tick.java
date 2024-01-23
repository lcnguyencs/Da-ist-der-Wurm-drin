package Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Tick {
    public Texture tick;
    public SpriteBatch batch;
    public float x;
    public float y;
    public int id;
    public boolean status;
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

    public void render(){
        batch.draw(tick, x, y);
    }

    public boolean check(float mouse_x, float mouse_y){
        return (mouse_x >= x && mouse_y >= y) && (mouse_x <= x + 64 && mouse_y <= y + 64);
    }
}