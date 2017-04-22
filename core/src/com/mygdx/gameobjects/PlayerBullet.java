package com.mygdx.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.gameworld.GameWorld;

import my.gdx.helpers.AssetLoader;


public class PlayerBullet extends GameObject{

	private TextureRegion texture;
	private GameWorld world;
	private int speed = 250;
	public PlayerBullet(float x, float y, ID id, GameWorld world) {
		super(x, y, id);
		width = 3;
		height = 15;
		texture = AssetLoader.bulletTexture;
		this.world = world;
		
	}

	@Override
	public void update(float delta) {
		move(delta);
		collision();
		if(y > 320) world.removeObject(this);
		boundingCircle.set(x+1,y+12,2f);
			
	}
	
    private void move(float delta)
    {	
			y += speed * Gdx.graphics.getDeltaTime();
    }
    public TextureRegion getTexture()
    {
    	return texture;
    }

	@Override
	public void collision() {
		
		
	}

}
