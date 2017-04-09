package com.mygdx.gameobjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

import my.gdx.helpers.AssetLoader;

public abstract class GameObject {

	protected float x;
	protected float y;
	protected int width;
	protected int height;
	protected ID id;
	protected TextureRegion texture;
	
	protected Circle boundingCircle;
	
	
	public GameObject(float x, float y, ID id){
		texture = AssetLoader.bulletTexture;
		this.x = x;
		this.y = y;
		this.id = id;
		boundingCircle = new Circle();
	}
	
	public abstract void update(float delta);
	public abstract void collision();
	
	public void setX(float x){
		this.x = x;
	}
	public void setY(float y){
		this.y = y;
	}
	public float getX(){
		return this.x;
	}
	public float getY(){
		return this.y;
	}
	public void setID(ID id){
		this.id = id;
	}
	public ID getID(){
		return id;
	}
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
    public TextureRegion getTexture()
    {
    	return texture;
    }
    public Circle getBoundingCircle() {
        return boundingCircle;
    }

}
