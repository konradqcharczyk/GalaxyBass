package com.mygdx.gameobjects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.gameworld.GameWorld;


/**
 * Bullet of 1st boss in game
 * @author Kokos
 *
 */
public class BossBullet extends GameObject{

	private GameWorld world;
	private int speedY, speedX;
	private Random r;
	/**
	 * Class constructor
	 * @param x horizontal position 
	 * @param y vertical position
	 * @param id of object
	 * @param world reference to world with objects
	 */
	public BossBullet(float x, float y, ID id, GameWorld world) {
		super(x, y, id);
		width = 6;
		height = 6;
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
		collision();
		if(y > 320) world.removeObject(this);
		boundingCircle.set(x+3,y+3,3f);
			
	}
	/**
	 * moves object
	 * @param delta time between frames
	 */
    private void move(float delta)
    {	
			y += speedY * Gdx.graphics.getDeltaTime();
			x += speedX * Gdx.graphics.getDeltaTime();
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
