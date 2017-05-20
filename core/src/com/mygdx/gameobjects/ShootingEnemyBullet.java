package com.mygdx.gameobjects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.gameworld.GameWorld;



/**
 * bullet of ShootingEnemy. Goes only down
 * @author Kokos
 *
 */
public class ShootingEnemyBullet extends GameObject{

	private GameWorld world;
	private int speedY;
	
	/**
	 * Class constructor
	 * @param x horizontal position 
	 * @param y vertical position
	 * @param id of object
	 */
	public ShootingEnemyBullet(float x, float y, ID id, GameWorld world) {
		super(x, y, id);
		width = 3;
		height = 8;
		this.world = world;
		speedY = 180;			
	}

	@Override
	public void update(float delta) {
		move(delta);
		collision();
		if(y > 320) world.removeObject(this);
		boundingCircle.set(x + width/2 + 1, y + 3, 2f);			
	}
	
	/**
	 * moves object only down
	 * @param delta
	 */
    private void move(float delta)
    {	
			y -= speedY * Gdx.graphics.getDeltaTime();
    }

	@Override
	public void collision() {
        for(int i = 0; i < world.getSize(); i++)
        {	
        	GameObject tempObject = world.getObject(i);
	
        	if(tempObject == this) continue;
        	
        	if(tempObject.id == ID.Player && Intersector.overlaps(tempObject.getBoundingCircle(), getBoundingCircle()) && world.isAlive())
        	{
        		world.getPlayer().addHealth(-10);
        		world.removeObject(this);
        	}

        }
		
	}

}
