package com.mygdx.gameobjects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.gameworld.GameWorld;

import my.gdx.helpers.AssetLoader;


public class ShootingEnemyBullet extends GameObject{

	private TextureRegion texture;
	private GameWorld world;
	private int speedY;
	public ShootingEnemyBullet(float x, float y, ID id, GameWorld world) {
		super(x, y, id);
		width = 3;
		height = 8;
		texture = AssetLoader.shootingEnemyBullet;
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
	
    private void move(float delta)
    {	
			y -= speedY * Gdx.graphics.getDeltaTime();
    }
    public TextureRegion getTexture()
    {
    	return texture;
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
