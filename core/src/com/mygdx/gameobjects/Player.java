package com.mygdx.gameobjects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.mygdx.gameworld.GameRenderer;
import com.mygdx.gameworld.GameWorld;
import com.mygdx.screens.GameScreen;

import my.gdx.helpers.AssetLoader;


public class Player extends GameObject{

	private GameWorld world;
	private int reloadTime = 20;
	private int reload = reloadTime;
	private int speed = 200;
	private int HEALTH = 100;
	private boolean shoots = true;
	public boolean moveRight = false;
	public boolean moveLeft = false;
	public boolean moveUp = false;
	public boolean moveDown = false;
	
	private Circle boundingCircle;
	
	public Player(float x, float y, ID id, GameWorld world) {
		super(x, y, id);
		width = 40;
	    height = 27;
	    texture = AssetLoader.playerAdidas;
	    this.world = world;
	    boundingCircle = new Circle();
	    
		
	}

	public void update(float delta)
	{
		move();
		shoot();
		collision();
		if(x <= 0) x = 0;
		if(x >= 198) x = 198;
		if(y <= 0) y = 0;
		if(y >= 294) y = 294;
		boundingCircle.set(x+20, y+12, 18f);
		texture = AssetLoader.playerAdidas;
		HEALTH = (int) GameScreen.clamp(HEALTH, 0, 100);
		if(HEALTH <= 0)
		{
			world.removeObject(this);
			world.setAlive(false);
			world.setKilled(true);
			world.kill();
		}
	}

    public void move()
    {	
    	
		if(moveRight)
			x += speed * Gdx.graphics.getDeltaTime();
		
		if(moveLeft)
			x -= speed * Gdx.graphics.getDeltaTime();
		
		if(moveUp)
			y += speed * Gdx.graphics.getDeltaTime();
		
		if(moveDown)
			y -= speed * Gdx.graphics.getDeltaTime();
    }
    public void keyDown(int keyCode)
    {
    	
    }
    public void shoot()
    {
    	if(reload <= 0 && shoots && world.isAlive())
    	{
    		world.addObject(new PlayerBullet(x + width/2 - 1, y + 22, ID.PlayerBullet, world));
    		GameRenderer.playLaser();
    		reload = reloadTime;
    	}
    	else reload--;
    }
    public Circle getBoundingCircle() {
        return boundingCircle;
    }

	@Override
	public void collision() {
        for(int i = 0; i < world.getSize(); i++)
        {	
        	GameObject tempObject = world.getObject(i);
	
        	if(tempObject == this) continue;
        	
        	if(tempObject.id == ID.PlayerBullet) continue;
        }
		
	}
	
	public int getHealth()
	{
		return HEALTH;
	}
	
	public void addHealth(int howMuch)
	{	
		if(howMuch < 0)	texture = AssetLoader.playerAdidasHit;
		HEALTH = (int) GameScreen.clamp(HEALTH + howMuch, 0 , 100);	
	}
	
	public void moveLeft(boolean value)
	{
		this.moveLeft = value;
	}
	public void moveRight(boolean value)
	{
		this.moveRight = value;
	}
	public void moveDown(boolean value)
	{
		this.moveDown = value;
	}
	public void moveUp(boolean value)
	{
		this.moveUp = value;
	}
}
