package com.mygdx.gameobjects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.gameworld.GameWorld;


public class SmallStone extends GameObject{

	private GameWorld world;

	private Circle boundingCircle;
	private Random r;
	private int speedY, speedX;
	
	private int typeOfStone;
	
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
	
	private void move(float delta)
	{
		y -= speedY * Gdx.graphics.getDeltaTime();	
		x += speedX * Gdx.graphics.getDeltaTime();	
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
	public int getTypeOfStone() {
		return typeOfStone;
	}

	public void setTypeOfStone(int typeOfStone) {
		this.typeOfStone = typeOfStone;
	}

}
