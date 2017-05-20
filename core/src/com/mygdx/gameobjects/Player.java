package com.mygdx.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.mygdx.gameworld.GameRenderer;
import com.mygdx.gameworld.GameWorld;
import com.mygdx.gameworld.GameWorld.GameState;
import com.mygdx.screens.GameScreen;

/**
 * Main space ship, player controls it, shoots.
 * @author Kokos
 *
 */
public class Player extends GameObject{

	private GameWorld world;
	private int reloadTime = 30;
	private int reload = reloadTime;
	private boolean reloaded = true;
	private int speed = 230;
	private int HEALTH = 100;
	public boolean moveRight = false;
	public boolean moveLeft = false;
	public boolean moveUp = false;
	public boolean moveDown = false;
	
	/**
	 * Class constructor
	 * @param x horizontal position 
	 * @param y vertical position
	 * @param id of object
	 * @param world reference to world with objects
	 */
	public Player(float x, float y, ID id, GameWorld world) {
		super(x, y, id);
		width = 40;
	    height = 27;
	    this.world = world;
	    boundingCircle = new Circle();
	    HEALTH = 100;
	}
	
	@Override
	public void update(float delta)
	{
		isHit = false;
		move();
		reload(delta);
		if(world.getCurrentState() == GameState.RUNNING)
		{
			shoot();
		}
		collision();
		if(x <= 0) x = 0;
		if(x >= 198) x = 198;
		if(y <= 0) y = 0;
		if(y >= 294) y = 294;
		boundingCircle.set(x + width/2, y + height/2 - 2, 18f);

		HEALTH = (int) GameScreen.clamp(HEALTH, 0, 100);
		if(HEALTH <= 0)
		{
			world.setHighScore();
			world.removeObject(this);
			world.setAlive(false);
			world.setCurrentState(GameState.GAMEOVER);
			world.kill();
		}
	}
	/**
	 * moves the object depends on which keys are pressed
	 */
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
    
    /**
     * wait time before object can shoot again
     * @param delta time between frames
     */
    private void reload(float delta)
    {
    	if(reload <= 0 && world.isAlive())
    	{
    		reloaded = true;
    		reload = reloadTime;
    	}
    	else reload -= delta;
    }
    
    /**
     * creates bullet
     */
    private void shoot()
    {
    	if(reloaded)
    	{
    		world.addObject(new PlayerBullet(x + width/2 - 1, y + 22, ID.PlayerBullet, world));
    		GameRenderer.playLaser();
    		reloaded = false;
    	}
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
	
    /**
     * return how much health have player
     */
	public int getHealth()
	{
		return HEALTH;
	}
	/**
	 * adds health
	 * @param howMuch to add
	 */
	public void addHealth(int howMuch)
	{	
		if(howMuch < 0){
			isHit = true;
		}
		HEALTH = (int) GameScreen.clamp(HEALTH + howMuch, 0 , 100);	
	}
	/**
	 * sets health
 	 * @param howMuch health should be set
	 */
	public void setHealth(int howMuch)
	{
		HEALTH = (int) GameScreen.clamp(howMuch, 0 , 100);	
	}
	/**
	 * sets to move left
	 * @param value true to move false to not move
	 */
	public void moveLeft(boolean value)
	{
		this.moveLeft = value;
	}
	/**
	 * sets to move right
	 * @param value true to move false to not move
	 */
	public void moveRight(boolean value)
	{
		this.moveRight = value;
	}
	/**
	 * sets to move down
	 * @param value true to move false to not move
	 */
	public void moveDown(boolean value)
	{
		this.moveDown = value;
	}
	/**
	 * sets to move up
	 * @param value true to move false to not move
	 */
	public void moveUp(boolean value)
	{
		this.moveUp = value;
	}

}
