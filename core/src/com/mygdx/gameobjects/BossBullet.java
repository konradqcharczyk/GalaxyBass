package com.mygdx.gameobjects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.gameworld.GameWorld;

import my.gdx.helpers.AssetLoader;


public class BossBullet extends GameObject{

	private TextureRegion texture;
	private GameWorld world;
	private int speedY, speedX;
	private Random r;
	public BossBullet(float x, float y, ID id, GameWorld world) {
		super(x, y, id);
		width = 6;
		height = 6;
		texture = AssetLoader.bossBullet;
		this.world = world;
		r = new Random();
		speedY = r.nextInt(300) - 200;
		speedX = r.nextInt(360) - 180;
		if(speedX + speedY < 60 && speedX + speedY > -60)
		{
			speedX = 0;
			speedY = -130;
		}
			
	}

	@Override
	public void update(float delta) {
		move(delta);
		if(y > 320) world.removeObject(this);
		boundingCircle.set(x+3,y+3,3f);
			
	}
	
    private void move(float delta)
    {	
			y += speedY * Gdx.graphics.getDeltaTime();
			x += speedX * Gdx.graphics.getDeltaTime();
    }
    public TextureRegion getTexture()
    {
    	return texture;
    }

	@Override
	public void collision() {
        for(int i = 0; i < world.getSize(); i++)
        {	
        	GameObject tempObject = world.getObject(i);
	
        	if(tempObject == this) continue;
        	
        	if(tempObject.id == ID.Player && Intersector.overlaps(tempObject.getBoundingCircle(), getBoundingCircle()) && world.isAlive())
        	{
        		world.getPlayer().addHealth(-5);
        		world.removeObject(this);
        	}

        }
		
	}

}
