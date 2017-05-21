package com.mygdx.gameobjects;

import com.badlogic.gdx.math.Circle;


/**
 * Main object class to extend from
 * @author Kokos
 *
 */
public abstract class GameObject {

    /**
     * position horizontal
     */
	protected float x;
	/**
	 * position vertical
	 */
	protected float y;
	/**
	 * width of object
	 */
	protected int width;
	/**
	 * height of object
	 */
	protected int height;
	/**
	 * ID of object
	 */
	protected ID id;
	/**
	 * bounding circle - hit box of object
	 */
	protected Circle boundingCircle;
	/**
	 * if object was hit in this frame
	 */
	protected boolean isHit = false;
	/**
	 * Class constructor
	 * @param x horizontal position 
	 * @param y vertical position
	 * @param id of object
	 */
	public GameObject(float x, float y, ID id){
		this.x = x;
		this.y = y;
		this.id = id;
		boundingCircle = new Circle();
	}
	
	/**
	 * update position, shoot, health etc
	 * @param delta time between frames
	 */
	public abstract void update(float delta);
	/**
	 * check if there is collision between other objects and do anything is needed if that happen(delte object, remove HP etc)
	 */
	public abstract void collision();
	
	/**
	 * set horizontal position
	 * @param x position
	 */
	public void setX(float x){
		this.x = x;
	}
	/**
	 * set vartical position
	 * @param x position
	 */
	public void setY(float y){
		this.y = y;
	}
	/**
	 * get horizontal position
	 */
	public float getX(){
		return this.x;
	}
	/**
	 * get vertical position
	 */
	public float getY(){
		return this.y;
	}
	/**
	 * set ID of object
	 * @param id what class is it
	 */
	public void setID(ID id){
		this.id = id;
	}
	/**
	 * get id of object
	 * @return id of object
	 */
	public ID getID(){
		return id;
	}
	/**
	 * get width of object
	 * @return width
	 */
	public int getWidth()
	{
		return width;
	}
	/**
	 * get height of object
	 * @return height
	 */
	public int getHeight()
	{
		return height;
	}
	/**
	 * get bound circle to hitbox 
	 */
    public Circle getBoundingCircle() {
        return boundingCircle;
    }
    
    /**
     * if object was hit this frame
     * @return true if yes false if no
     */
	public boolean isHit() {
		return isHit;
	}
	/**
	 * sets that object was hit
	 * @param isHit true if yes false if no
	 */
	public void setHit(boolean isHit) {
		this.isHit = isHit;
	}

}
