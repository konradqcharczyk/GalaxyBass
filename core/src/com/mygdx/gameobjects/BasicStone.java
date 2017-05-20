package com.mygdx.gameobjects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.gameworld.GameWorld;

/**
 * Big stone with 1 of 4 textures, falling down, when it's hit creates 2 small stones
 * @author Kokos
 *
 */
public class BasicStone extends GameObject{

	private GameWorld world;

	private Circle boundingCircle;
	private Random r;
	private int speed;
	
	private int typeOfStone;
	

	/**
	 * Class constructor
	 * @param x horizontal position 
	 * @param y vertical position
	 * @param id of object
	 * @param world reference to world with objects
	 */
	public BasicStone(float x, float y, ID id, GameWorld world) {
		super(x, y, id);
		this.world = world;
		width = 32;
		height = 32;
		boundingCircle = new Circle();
		r = new Random();
		speed = r.nextInt(20) + 180;
		
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
		boundingCircle.set(x+16, y+16, 15f);
		if(y < -20 )
		{	
			world.removeObject(this);	
		}
	}
	
	/**
	 * moves object
	 * @param delta time between frames
	 */
	private void move(float delta)
	{
		y -= speed * Gdx.graphics.getDeltaTime();	
	}
    
	/**
	 * get bound circle to hitbox 
	 */
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
        		world.addToScore(20);
        		world.addObject(new SmallStone( x + 8, y + 8 ,ID.SmallStone, world));
        		world.addObject(new SmallStone( x + 8, y + 8 ,ID.SmallStone, world));
        		world.removeObject(tempObject);
        		world.removeObject(this);
        	}
        }
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
