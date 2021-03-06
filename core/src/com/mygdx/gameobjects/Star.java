package com.mygdx.gameobjects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.gameworld.GameWorld;

/**
 * Upgrade, adds points to score
 * @author Kokos
 *
 */
public class Star extends GameObject{

    /**
     * world with list of object to check for collisions 
     */
	private GameWorld world;

	private int speed = 100;
	
	/**
	 * Class constructor
	 * @param x horizontal position 
	 * @param y vertical position
	 * @param id of object
	 */
	public Star(float x, float y, ID id, GameWorld world) {
		super(x, y, id);
		this.world = world;
		width = 12;
		height = 12;
	}

	@Override
	public void update(float delta) {
		move(delta);
		collision();
		boundingCircle.set(x + width/2, y + width/2, 7f);
		if(y < -20 )
		{	
			world.removeObject(this);	
		}
	}
	/**
	 * moves object. Goes only down
	 * @param delta
	 */
	private void move(float delta)
	{
		y -= speed * Gdx.graphics.getDeltaTime();	
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
        		world.addToScore(100);
        		world.removeObject(this);
        	}

        }
	}


}
