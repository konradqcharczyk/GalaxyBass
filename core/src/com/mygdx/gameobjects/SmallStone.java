package com.mygdx.gameobjects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.gameworld.GameWorld;

/**
 * Small stone which moves down and to side, one of 4 stone type
 * @author Kokos
 *
 */
public class SmallStone extends GameObject{

	private GameWorld world;

	private Random r;
	private int speedY, speedX;
	
	private int typeOfStone;
	
	/**
	 * Class constructor
	 * @param x horizontal position 
	 * @param y vertical position
	 * @param id of object
	 * @param world reference to world with objects
	 */
	public SmallStone(float x, float y, ID id, GameWorld world) {
		super(x, y, id);
		this.world = world;
		width = 16;
		height = 16;
		boundingCircle = new Circle();
		r = new Random();
		speedY = r.nextInt(20) + 70;
		speedX = r.nextInt(30) - 15;
		
		if(r.nextInt(3) == 0)
			typeOfStone = 1;
		else if(r.nextInt(3) == 1)
			typeOfStone = 2;
		else if(r.nextInt(3) == 2)
			typeOfStone = 3;
		else if(r.nextInt(3) == 3)
			typeOfStone = 4;
		else
			typeOfStone = 1;
	}



	@Override
	public void update(float delta) {
		move(delta);
		collision();
		boundingCircle.set(x+8, y+8, 8f);
		if(y < -20)
		{	
			world.removeObject(this);	
		}
		
		if(x < -20 || x > 240)
		{	
			world.removeObject(this);	
		}
		
	}
	/**
	 * move the object
	 * @param delta time between frames
	 */
	private void move(float delta)
	{
		y -= speedY * Gdx.graphics.getDeltaTime();	
		x += speedX * Gdx.graphics.getDeltaTime();	
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
        		world.getPlayer().addHealth(-10);
        		world.removeObject(this);
        	}

        	if(tempObject.id == ID.PlayerBullet && Intersector.overlaps(tempObject.getBoundingCircle(), getBoundingCircle()))
        	{
        		world.addToScore(10);
        		world.removeObject(tempObject);
        		world.removeObject(this);
        	}
        }
	}
	
	/**
	 * get bound circle to hitbox 
	 */
    public Circle getBoundingCircle() {
        return boundingCircle;
    }

	/**
	 * return what type of stone is it
	 * @return type <1-4>
	 */
	public int getTypeOfStone() {
		return typeOfStone;
	}
	/**
	 * sets what type of stone is it
	 * @param typeOfStone <1-4>, 1 as default
	 */
	public void setTypeOfStone(int typeOfStone) {
		if(typeOfStone < 1 || typeOfStone > 4) {
			this.typeOfStone = 1;
			return;
		}
		this.typeOfStone = typeOfStone;
	}

}
