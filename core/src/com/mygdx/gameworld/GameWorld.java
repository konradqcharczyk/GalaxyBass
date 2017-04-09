package com.mygdx.gameworld;



import java.util.LinkedList;
import java.util.Random;

import com.mygdx.gameobjects.BasicEnemy;
import com.mygdx.gameobjects.BasicStone;
import com.mygdx.gameobjects.Boss;
import com.mygdx.gameobjects.GameObject;
import com.mygdx.gameobjects.Heal;
import com.mygdx.gameobjects.ID;
import com.mygdx.gameobjects.Player;

public class GameWorld {

	LinkedList<GameObject> objects;
	private Player player;
	private boolean isAlive = true;
	private boolean isKilled = false;
	private int score = 0;
	private int lvl = 0;
	private int lvlReload = 0;
	private int scoreSpeed = 15; // co ile klatek +1 score
	private Random r;
	private boolean isBoss = false;
	private boolean isBossKilled = false;
	private boolean onlyBoss = false;


	public GameWorld()
	{	
		objects = new LinkedList<GameObject>();
		player = new Player(100, 20, ID.Player, this);
		objects.add(player);
		r = new Random();
		if(onlyBoss) addObject(new Boss(90, 350, ID.Boss,this));
	}
	public void update(float delta)
	{
		if(isAlive)
		{
			if(scoreSpeed == 0) 				
			{
				score++;
				scoreSpeed = 5;
			}
			else scoreSpeed--;
		}
			
	        for(int i = 0; i < objects.size(); i++)
	        {
	        	GameObject tempObject = objects.get(i);
	        	tempObject.update(delta);
	        	tempObject.collision();
	        }
		

	}
    public void spawn()
    {
    	if(!onlyBoss)
    	{
	    	if(lvlReload <= 0)
	    	{
	    		lvlReload = 300;
	    		lvl++;
	
	    		if(lvl == 1)
	    		{
	    			for(int i = 0; i < 10; i++)
	    				addObject(new BasicStone(r.nextInt(200), r.nextInt(200) + 400, ID.BasicStone,this));
	    		}
	    		else if(lvl == 2)
	    		{
	    			for(int i = 0; i < 15; i++)
	    				addObject(new BasicStone(r.nextInt(200), r.nextInt(200) + 400, ID.BasicStone,this));
	    		}
	    		else if(lvl == 3)
	    		{
	    			for(int i = 0; i < 15; i++)
	    				addObject(new BasicEnemy(r.nextInt(200), r.nextInt(200) + 400, ID.BasicEnemy,this));
	    		}
	    		else if(lvl == 4)
	    		{
	    			for(int i = 0; i < 10; i++)
	    				addObject(new BasicEnemy(r.nextInt(200), r.nextInt(150) + 400, ID.BasicEnemy,this));
	    			for(int i = 0; i < 10; i++)
	    				addObject(new BasicStone(r.nextInt(200), r.nextInt(150) + 400, ID.BasicStone,this));
	    			
	        		addObject(new Heal(r.nextInt(200), r.nextInt(150) + 310, ID.Heal,this));
	    		}
	    		else if(lvl == 5)
	    		{
	    			addObject(new Boss(90, 350, ID.Boss,this));
	    		}

    	}
    	else 
    		if(isAlive) lvlReload--;
    	  	
    	if(r.nextInt(1500) == 1)
    		addObject(new Heal(r.nextInt(200), r.nextInt(150) + 310, ID.Heal,this));
    	}
    }
	
	
	public void kill()
	{
        for(int i = 0; i < objects.size(); i++)
        {
        	GameObject tempObject = objects.get(i); 
        	if(tempObject.getID() == ID.Player || tempObject.getID() == ID.Boss) continue;
        	else
        		objects.remove(tempObject);
        }
	}
	public Player getPlayer()
	{
		if(isAlive)
			return player;
		else
			return null;
	}
	public GameObject getObject(int i)
	{
		return objects.get(i);
	}
	public void addObject(GameObject temp)
	{
		objects.add(temp);
	}
	public void removeObject(GameObject temp)
	{
		objects.remove(temp);
	}
	public int getSize()
	{
		return objects.size();
	}
	
	public boolean isAlive() 
	{
		return isAlive;
	}
	public void setAlive(boolean isAlive) 
	{
		this.isAlive = isAlive;
	}
	public int getScore() 
	{
		return score;
	}
	public int getLevel() 
	{
		return lvl;
	}
	public void setScore(int score) 
	{
		this.score = score;
	}
	public void setLevel(int level) 
	{
		this.lvl = level;
	}
	public void addToScore(int howMuch) 
	{
		this.score += howMuch;
	}
	public boolean isBoss() {
		return isBoss;
	}
	public void setBoss(boolean isBoss) {
		this.isBoss = isBoss;
	}
	public Boss getBoss()
	{
        for(int i = 0; i < objects.size(); i++)
        {
        	GameObject tempObject =  objects.get(i);
        	if(tempObject.getID() == ID.Boss)
        		return (Boss)tempObject;
        }
        return null;
	}
	public boolean isBossKilled() {
		return isBossKilled;
	}
	public void setBossKilled(boolean isBossKilled) {
		this.isBossKilled = isBossKilled;
	}
	public boolean isKilled() {
		return isKilled;
	}
	public void setKilled(boolean isKilled) {
		this.isKilled = isKilled;
	}
}
