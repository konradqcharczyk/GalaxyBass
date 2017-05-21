package com.mygdx.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.gameworld.GameWorld;
import com.mygdx.gameworld.GameWorld.GameState;

/**
 * Boss of 1st level, shoots randomly in all directions
 * @author Kokos
 *
 */
public class Boss extends GameObject{

    /**
     * world with list of object to check for collisions 
     */
	private GameWorld world;
    /**
     * counter of reload time
     */
	private int reload = 0;
	/**
	 * how much time to reload
	 */
	private int reloadTime = 30;
	/**
	 * how many bullets are shoot
	 */
	private int bulletAmmount = 7;
	
	private int speedY = 40;
	private int speedX = 80;
	private int HP = 100;
	
	/**
	 * time when boss is just going down 
	 */
	private float commingDownTime = 4;
	
	private boolean isKilled = false;
	
	
	/**
	 * Class constructor
	 * @param x horizontal position 
	 * @param y vertical position
	 * @param id of object
	 * @param world reference to world with objects
	 */
	public Boss(float x, float y, ID id, GameWorld world) {
		super(x, y, id);
		this.world = world;
		width = 64;
		height = 64;
	}

	@Override
	public void update(float delta) {
		if(!isKilled) isHit = false;
		move(delta);
		collision();
		shoot();
		commingDownTime -= delta;
		boundingCircle.set(x+32, y+32, 32f);

		
		if(y <= 0)
		{
			world.setBoss(false);
		}
		if(commingDownTime < 0 && HP > 0) world.setBoss(true);
		
	}
	
	/**
	 * Moves the object, at start only down but then side to side
	 * @param delta time between frames
	 */
	private void move(float delta)
	{
		if(commingDownTime >= 0 )
			y -= speedY * Gdx.graphics.getDeltaTime();	
		else if(!isKilled)
		{
			x -= speedX * Gdx.graphics.getDeltaTime();
			if(x <= 5) speedX = speedX * -1;
			if(x >= 175) speedX = speedX * -1;
		}
		else
		{
			y -= speedY * Gdx.graphics.getDeltaTime();
			x -= speedX * Gdx.graphics.getDeltaTime();
		}
		
	}
    

	@Override
	public void collision() 
	{
        for(int i = 0; i < world.getSize(); i++)
        {	
        	GameObject tempObject = world.getObject(i);
	
        	if(tempObject == this) continue;
        	
        	if(tempObject.id == ID.PlayerBullet && Intersector.overlaps(tempObject.getBoundingCircle(), getBoundingCircle()) && (commingDownTime < 0 ) && !isKilled)
        	{
        		world.removeObject(tempObject);
        		HP -= 5;
        		isHit = true;
        		if(HP <= 0)
        		{ 	
        			world.setHighScore();
        			speedY = (int) (speedY * 1.5);
        			isKilled = true;
        			world.setBoss(false);
        			world.setCurrentState(GameState.WIN);
        		}
        	}
        	if(tempObject.id == ID.Player && Intersector.overlaps(tempObject.getBoundingCircle(), getBoundingCircle()) && (commingDownTime < 0 ) && !isKilled)
        	{
        		if(world.isAlive())
        			world.getPlayer().addHealth(-2);
        	}
        }
	}
	
	/**
	 * get bound circle to hitbox 
	 */
//    public Circle getBoundingCircle() {
//        return boundingCircle;
//    }
	/**
	 * return hp of boss
	 */
	public int getHealth()
	{
		return HP;
	}
	/**
	 * sets if boss is killed
	 * @return true if yes false if no
	 */
	public boolean isKilled()
	{
		return isKilled;
	}
	/**
	 * shoots in all directions
	 */
	private void shoot()
	{
		if(!isKilled && commingDownTime < 0)
	    	if(reload <= 0)
	    	{
	    		for(int i = 0; i < bulletAmmount; i++)
	    			world.addObject(new BossBullet(x + width/2 - 1, y + 32, ID.BossBullet, world));
	    		
	    		reload = reloadTime;
	    	}
	    	else reload--;
	}

}
