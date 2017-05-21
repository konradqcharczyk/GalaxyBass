package com.mygdx.gameobjects;

import com.badlogic.gdx.Gdx;
import com.mygdx.gameworld.GameWorld;


/**
 * Bullet of player. It goes only up
 * @author Kokos
 *
 */
public class PlayerBullet extends GameObject{

    /**
     * world with list of object to check for collisions 
     */
	private GameWorld world;
	private int speed = 300;
	/**
	 * Class constructor
	 * @param x horizontal position 
	 * @param y vertical position
	 * @param id of object
	 */
	public PlayerBullet(float x, float y, ID id, GameWorld world) {
		super(x, y, id);
		width = 3;
		height = 15;
		this.world = world;
		
	}

	@Override
	public void update(float delta) {
		move(delta);
		collision();
		if(y > 320) world.removeObject(this);
		boundingCircle.set(x+1,y+12,2f);
			
	}
	/**
	 * moves object, only up
	 * @param delta
	 */
    private void move(float delta)
    {	
			y += speed * Gdx.graphics.getDeltaTime();
    }

	@Override
	public void collision() {
		return;
	}

}
