package com.mygdx.gameobjects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.gameworld.GameRenderer;
import com.mygdx.gameworld.GameWorld;

import my.gdx.helpers.AssetLoader;

public class ShootingEnemy extends GameObject{

	private GameWorld world;

	private Circle boundingCircle;
	private Random r;
	private int reloadTime = 40;
	private int reload = reloadTime;
	private int speedY = 80;
	private int speedX = 60;
	private int hp = 20;
	
	public ShootingEnemy(float x, float y, ID id, GameWorld world) {
		super(x, y, id);
		this.world = world;
		texture = AssetLoader.shootingEnemy;
		width = 24;
		height = 24;
		boundingCircle = new Circle();
		r = new Random();
		speedX += r.nextInt(10);
		speedY += r.nextInt(20);
		if(r.nextInt(2) == 0) speedX *= -1;
		}

	public void update(float delta)
	{
		move(delta);
		shoot();
		boundingCircle.set(x + width/2, y + height/2, width/2f);
		if(y < -20)
		{	
			world.removeObject(this);	
		}
		if(texture == AssetLoader.shootingEnemyHit)
			texture = AssetLoader.shootingEnemy;
		if(hp <= 0)
			world.removeObject(this);
		
	}
	
	private void move(float delta)
	{
		y -= speedY * Gdx.graphics.getDeltaTime();	
		x -= speedX * Gdx.graphics.getDeltaTime();
		if(x <= 0) speedX = speedX * -1;
		if(x >= 215) speedX = speedX * -1;
	}
    public TextureRegion getTexture()
    {
    	return texture;
    }
    
    public Circle getBoundingCircle() {
        return boundingCircle;
    }

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
        		hp -= 10;
        		texture = AssetLoader.shootingEnemyHit;
        		world.removeObject(tempObject);
        	}
        }
	}
    public void shoot()
    {
    	if(reload <= 0)
    	{
    		world.addObject(new ShootingEnemyBullet(x + width/2 - 1, y , ID.ShootingEnemyBullet, world));
    		GameRenderer.playLaser();
    		reload = reloadTime;
    	}
    	else reload--;
    }


}
