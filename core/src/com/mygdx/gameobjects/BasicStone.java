package com.mygdx.gameobjects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.gameworld.GameWorld;

import my.gdx.helpers.AssetLoader;

public class BasicStone extends GameObject{

	private GameWorld world;

	private Circle boundingCircle;
	private Random r;
	private int speed;
	
	public BasicStone(float x, float y, ID id, GameWorld world) {
		super(x, y, id);
		this.world = world;
		width = 32;
		height = 32;
		boundingCircle = new Circle();
		r = new Random();
		speed = r.nextInt(20) + 180;
		
		if(r.nextInt(3) == 0)
			texture = AssetLoader.stone1;
		else if(r.nextInt(3) == 1)
			texture = AssetLoader.stone2;
		else if(r.nextInt(3) == 2)
			texture = AssetLoader.stone3;
		else if(r.nextInt(3) == 3)
			texture = AssetLoader.stone4;
		else
			texture = AssetLoader.stone1;
		
	}

	@Override
	public void update(float delta) {
		move(delta);
		collision();
		boundingCircle.set(x+16, y+16, 15f);
		if(y < -20 )
		{	
//			world.addObject(new BasicStone(r.nextInt(220),r.nextInt(20) + 325 ,ID.BasicStone, world));
			world.removeObject(this);	
		}
	}
	
	private void move(float delta)
	{
		y -= speed * Gdx.graphics.getDeltaTime();	
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


}
