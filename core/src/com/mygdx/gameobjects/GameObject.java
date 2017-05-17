package com.mygdx.gameobjects;

import com.badlogic.gdx.math.Circle;



public abstract class GameObject {

	protected float x;
	protected float y;
	protected int width;
	protected int height;
	protected ID id;

	protected Circle boundingCircle;
	
	protected boolean isHit = false;

	public GameObject(float x, float y, ID id){
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
    public Circle getBoundingCircle() {
        return boundingCircle;
    }
    
	public boolean isHit() {
		return isHit;
	}

	public void setHit(boolean isHit) {
		this.isHit = isHit;
	}

}
