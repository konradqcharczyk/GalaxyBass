package com.mygdx.gameobjects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.gameworld.GameWorld;

/**
 * upgrade, adds some HP to player if he hits it
 * @author Kokos
 *
 */
public class Heal extends GameObject{

	private GameWorld world;

	private Circle boundingCircle;
	private int speed = 40;
	
	/**
	 * Class constructor
	 * @param x horizontal position 
	 * @param y vertical position
	 * @param id of object
	 */
	public Heal(float x, float y, ID id, GameWorld world) {
		super(x, y, id);
		this.world = world;
		width = 12;
		height = 12;
		boundingCircle = new Circle();
	}

	@Override
	public void update(float delta) {
		move(delta);
		collision();
		boundingCircle.set(x+6, y+6, 7f);
		if(y < -20 )
		{	
			world.removeObject(this);	
		}
	}
	/**
	 * moves object, always go down
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
        	
        	if(tempObject.id == ID.Player && Intersector.overlaps(tempObject.getBoundingCircle(), getBoundingCircle()) && world.isAlive())
        	{
        		world.getPlayer().addHealth(20);
        		world.removeObject(this);
        	}

        }
	}


}
