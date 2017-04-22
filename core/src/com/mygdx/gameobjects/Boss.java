package com.mygdx.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.gameworld.GameWorld;

import my.gdx.helpers.AssetLoader;

public class Boss extends GameObject{

	private GameWorld world;

	private Circle boundingCircle;
	private int reload = 0;
	private int reloadTime = 30;
	private int bulletAmmount = 7;
	private int speedY = 40;
	private int speedX = 80;
	private int HP = 100;
	private float commingDownTime = 4;
	private boolean isKilled = false;
	
	public Boss(float x, float y, ID id, GameWorld world) {
		super(x, y, id);
		this.world = world;
		texture = AssetLoader.boss;
		width = 64;
		height = 64;
		boundingCircle = new Circle();

	}

	@Override
	public void update(float delta) {
		move(delta);
		collision();
		shoot();
		commingDownTime -= delta;
		boundingCircle.set(x+32, y+32, 32f);
		if(!isKilled) texture = AssetLoader.boss;
		if(y <= 0)
		{
			world.setBoss(false);
			//world.removeObject(this);
		}
		if(commingDownTime < 0) world.setBoss(true);
		
	}
	
	private void move(float delta)
	{
		if(commingDownTime >= 0 )
			y -= speedY * Gdx.graphics.getDeltaTime();	
		else if(!isKilled)
		{
			x -= speedX * Gdx.graphics.getDeltaTime();
			if(x <= 0) speedX = speedX * -1;
			if(x >= 175) speedX = speedX * -1;
		}
		else
		{
			y -= speedY * Gdx.graphics.getDeltaTime();
			x -= speedX * Gdx.graphics.getDeltaTime();
		}
		
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
        	
        	if(tempObject.id == ID.PlayerBullet && Intersector.overlaps(tempObject.getBoundingCircle(), getBoundingCircle()) && (commingDownTime < 0 ) && !isKilled)
        	{
        		world.removeObject(tempObject);
        		HP -= 5;
        		texture = AssetLoader.bosshit;
        		if(HP <= 0)
        		{ 	
        			speedY = (int) (speedY * 1.5);
        			isKilled = true;
        			world.setBoss(false);
        			world.setAlive(false);
        			world.setBossKilled(true);
        		}
        	}
        	if(tempObject.id == ID.Player && Intersector.overlaps(tempObject.getBoundingCircle(), getBoundingCircle()) && (commingDownTime < 0 ) && !isKilled)
        	{
        		if(world.isAlive())
        			world.getPlayer().addHealth(-2);
        	}
        }
	}
	
	public int getHealth()
	{
		return HP;
	}
	public boolean isKilled()
	{
		return isKilled;
	}
	
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
