package com.mygdx.gameobjects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.gameworld.GameWorld;

import my.gdx.helpers.AssetLoader;

public class BasicEnemy extends GameObject{

	private GameWorld world;

	private Circle boundingCircle;
	private Random r;
	private int speed;
	
	public BasicEnemy(float x, float y, ID id, GameWorld world) {
		super(x, y, id);
		this.world = world;
		texture = AssetLoader.basicEnemy;
		width = 16;
		height = 16;
		boundingCircle = new Circle();
		r = new Random();
		speed = r.nextInt(20) + 180;
	}

	@Override
	public void update(float delta) {
		move(delta);
		if(r.nextInt(4) == 0) speed++;
		boundingCircle.set(x + 8, y + 8, 8f);
		if(y < -20)
		{	
			world.removeObject(this);	
		}
	}
	
	private void move(float delta)
	{
		y -= speed * Gdx.graphics.getDeltaTime();	
	}
    public TextureRegion getTexture()
    {
    	return texture;
    }
    
    public Circle getBoundingCircle() {
        return boundingCircle;
    }

	@Override
	public void collision() 
	{
        for(int i = 0; i < world.getSize(); i++)
        {	
        	GameObject tempObject = world.getObject(i);
	
        	if(tempObject == this) continue;
        	
        	if(tempObject.id == ID.Player && Intersector.overlaps(tempObject.getBoundingCircle(), getBoundingCircle()))
        	{
        		world.getPlayer().addHealth(-20);
        		world.removeObject(this);
        	}

        	if(tempObject.id == ID.PlayerBullet && Intersector.overlaps(tempObject.getBoundingCircle(), getBoundingCircle()))
        	{
        		world.addToScore(50);
        		world.removeObject(tempObject);
        		world.removeObject(this);
        	}
        }
	}


}
