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
import com.mygdx.gameobjects.ShootingEnemy;
import com.mygdx.gameobjects.Star;

public class GameWorld {

	LinkedList<GameObject> objects;
	
	private Player player;
	private boolean isAlive = true;
	private boolean isKilled = false;
	private int score = 0;
	private int lvl = 0;
	private int lvlReload = 0;
	private int lvlTime = 400;
	
	private int scoreSpeed = 50; // co ile klatek +1 score
	private Random r;
	
	private boolean isBoss = false;
	private boolean isBossKilled = false;
	private boolean onlyBoss = false;
	
	public enum GameState{
		READY, RUNNING, GAMEOVER, WIN, BOSS
	}
	private GameState currentState;

	public GameWorld()
	{	
		currentState = GameState.RUNNING;
		objects = new LinkedList<GameObject>();
		player = new Player(100, 20, ID.Player, this);
		objects.add(player);
		r = new Random();
		if(onlyBoss) addObject(new Boss(90, 350, ID.Boss,this));
		
	}
	public void update(float delta)
	{
		updateScore();
		updateObjects(delta);
	}
    public void spawn()
    {
    	if(!onlyBoss)
    	{
	    	if(lvlReload <= 0)
	    	{
	    		lvlReload = lvlTime;
	    		lvl++;
	    	
	    	}
    	else if(isAlive) lvlReload--;
	    	
	    if(!isBoss)
	    {
    		if(lvl == 1)
    		{
    			if(r.nextInt(40) == 1) addObject(new BasicStone(r.nextInt(200), 320, ID.BasicStone,this));		
    		}
    		else if(lvl == 2)
    		{
    			if(r.nextInt(60) == 1) addObject(new BasicStone(r.nextInt(200), 320, ID.BasicStone,this));
    			if(r.nextInt(40) == 1) addObject(new BasicEnemy(r.nextInt(200), 320, ID.BasicEnemy,this));
    		}
    		else if(lvl == 3)
    		{
    			if(r.nextInt(10) == 1) addObject(new BasicEnemy(r.nextInt(200), 320, ID.BasicEnemy,this));
    			//if(r.nextInt(50) == 1) addObject(new ShootingEnemy(r.nextInt(200), 320, ID.ShootingEnemy,this));
    		}
    		else if(lvl == 4)
    		{
    			if(r.nextInt(35) == 1) addObject(new ShootingEnemy(r.nextInt(200), 320, ID.ShootingEnemy,this));
    		}
    		else if(lvl == 5)
    		{
    			addObject(new Boss(90, 350, ID.Boss,this));
    			isBoss = true;
    		}
	    }
    			
    	if(r.nextInt(1500) == 1)
    	{
    		addObject(new Heal(r.nextInt(200), 320, ID.Heal,this));
    	}
    	if(r.nextInt(500) == 1)
    	{
    		addObject(new Star(r.nextInt(200), 320, ID.Star,this));
    	}
    }
    }
	
    private void updateScore()
    {
    	if(currentState == GameState.RUNNING || currentState == GameState.BOSS )
    	{
    		if(scoreSpeed == 0) 				
    		{
    			score++;
    			scoreSpeed = 5;
    		}
    		else scoreSpeed--;
    	}
    }
    
    private void updateObjects(float delta)
    {
        for(int i = 0; i < objects.size(); i++)
        {
        	GameObject tempObject = objects.get(i);
        	tempObject.update(delta);
        }
    }
    
    private void updaateReady(float delta)
    {
    	
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
	public void setCurrentState(GameState state)
	{
		this.currentState = state;
	}
	public GameState getCurrentState()
	{
		return this.currentState;
	}
}
