package com.mygdx.gameobjects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.gameworld.GameWorld;

import my.gdx.helpers.AssetLoader;

public class Heal extends GameObject{

	private GameWorld world;

	private Circle boundingCircle;
	private int speed = 40;
	
	public Heal(float x, float y, ID id, GameWorld world) {
		super(x, y, id);
		this.world = world;
		texture = AssetLoader.heal;
		width = 12;
		height = 12;
		boundingCircle = new Circle();
	}

	@Override
	public void update(float delta) {
		move(delta);
		boundingCircle.set(x+6, y+6, 7f);
		if(y < -20 )
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
        	
        	if(tempObject.id == ID.Player && Intersector.overlaps(tempObject.getBoundingCircle(), getBoundingCircle()) && world.isAlive())
        	{
        		world.getPlayer().addHealth(20);
        		world.removeObject(this);
        	}

        }
	}


}
