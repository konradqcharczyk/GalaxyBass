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

import my.gdx.helpers.AssetLoader;
/**
 * keeps all objects in linked list, management of game, logic
 * @author Kokos
 *
 */
public class GameWorld {

	LinkedList<GameObject> objects;
	
	private Player player;
	private Boss boss;
	private boolean isAlive = true;
	private int score = 0;
	private int lvl = 0;
	private int lvlReload = 0;
	private int lvlTime = 400;
	
	private int scoreSpeed = 50;
	private Random r;
	
	private boolean isBoss = false;
	private boolean onlyBoss = false;
	
	public enum GameState{
		READY, RUNNING, GAMEOVER, WIN
	}
	private GameState currentState;
	/**
	 * Class Constructor
	 */
	public GameWorld()
	{	
		currentState = GameState.READY;
		objects = new LinkedList<GameObject>();
		player = new Player(100, 20, ID.Player, this);
		objects.add(player);
		r = new Random();
	}
	
	/**
	 * updates all of objects, score and spawn depending on game state
	 * @param delta time between frames 
	 */
	public void update(float delta)
	{
		switch(currentState){
		case READY:
			updateReady(delta);
			break;
				
		case RUNNING:
			updateRunning(delta);
			break;
			
		case WIN:
			updateRunning(delta);
			break;
			
		case GAMEOVER:
			updateRunning(delta);
			break;
		}
	}
	/**
	 * updates objects, score and spawn when it's running game
	 * @param delta
	 */
	private void updateRunning(float delta)
	{
		updateScore();
		spawn();
		updateObjects(delta);
	}
	/**
	 * spawns objects that are falling down
	 */
    private void spawn()
    {
    	if(!onlyBoss)
    	{
	    	if(lvlReload <= 0)
	    	{
	    		lvlReload = lvlTime;
	    		lvl++;
	    	
	    	}
    	else if(isAlive) lvlReload--;
	    	
	    if(lvlReload <= 0)
	    {
	    	lvlReload = lvlTime;
    		if(lvl == 1)
    		{
    			for(int i = 0; i < 10; i++)
    				addObject(new BasicStone(r.nextInt(200), r.nextInt(350) + 320, ID.BasicStone,this));		
    		}
    		else if(lvl == 2)
    		{
    			for(int i = 0; i < 5; i++)
    				addObject(new BasicStone(r.nextInt(200), r.nextInt(350) + 320, ID.BasicStone,this));
    			for(int i = 0; i < 5; i++)
    				addObject(new BasicEnemy(r.nextInt(200), r.nextInt(350) + 320, ID.BasicEnemy,this));
    		}
    		else if(lvl == 3)
    		{
    			for(int i = 0; i < 13; i++)
    				addObject(new BasicEnemy(r.nextInt(200), r.nextInt(400) + 400, ID.BasicEnemy,this));
    		}
    		else if(lvl == 4)
    		{
    			for(int i = 0; i < 5; i++)
    				addObject(new ShootingEnemy(r.nextInt(200), r.nextInt(350) + 320, ID.ShootingEnemy,this));
    		}
    		else if(lvl == 5)
    		{
    			boss = new Boss(90, 350, ID.Boss,this);
    			addObject(boss);
    			isBoss = true;
    		}
	    }
	    else lvlReload--;
    			
	    
	    if(currentState == GameState.RUNNING)
	    {
	    	if(r.nextInt(1500) == 1)
	    	{
	    		addObject(new Heal(r.nextInt(200), 330, ID.Heal,this));
	    	}
	    	if(r.nextInt(500) == 1)
	    	{
	    		addObject(new Star(r.nextInt(200), 330, ID.Star,this));
	    	}
	    }
    }
    	else{
    		if(!isBoss)
    		{
    		addObject(new Boss(90, 350, ID.Boss,this));
    		isBoss = true;
    		}
    	}
    }
    
    /**
     * sets new high score if new is better then previous 
     */
    public void setHighScore(){
    	if(score > AssetLoader.getHighScore())
    	{
    		AssetLoader.setHighScore(score);
    	}
    }
	/**
	 * Increments score after 'scoreSpeed' frames
	 */
    private void updateScore()
    {
    	if(currentState == GameState.RUNNING)
    	{
    		if(scoreSpeed == 0) 				
    		{
    			score++;
    			scoreSpeed = 5;
    		}
    		else scoreSpeed--;
    	}
    }
    /**
     * updates objects
     * @param delta time between frames 
     */
    private void updateObjects(float delta)
    {
        for(int i = 0; i < objects.size(); i++)
        {
        	GameObject tempObject = objects.get(i);
        	tempObject.update(delta);
        }
    }
	/**
	 * updates if state is ready
	 * @param delta time between frames 
	 */
    private void updateReady(float delta)
    {
    	updatePlayer(delta);
    }
	/**
	 * updates Player
	 * @param delta time between frames 
	 */
    private void updatePlayer(float delta)
    {
    	player.update(delta);
    }
    /**
     * restart after game over or after win, creates new player or heal, starts new game
     */
    public void restart()
    {	
		Player tempPlayer = player;
    	kill();
        if(currentState != GameState.GAMEOVER){
        	player = new Player(tempPlayer.getX(),tempPlayer.getY() , ID.Player, this);
        }
        else
        {
        	player = new Player(100, 20, ID.Player, this);
        }
    	addObject(player);
    	setBoss(false);
		isAlive = true;
		score = 0;
		lvl = 0;
		lvlReload = 0;
		currentState = GameState.READY;
		start();
    }
	/**
	 * change game state to running
	 */
    public void start(){
    	currentState = GameState.RUNNING;
    }
    /**
     * remove all objects beside player
     */
	public void kill()
	{
        objects.clear();
	}
	/**
	 * get player
	 * @return reference to player
	 */
	public Player getPlayer()
	{
		if(isAlive)
			return player;
		else
			return null;
	}
	/**
	 * get object from objects linked list on index
	 * @param i index
	 * @return object on this index in 
	 */
	public GameObject getObject(int i)
	{
		return objects.get(i);
	}
	/**
	 * adds object at the end of objects linked list
	 * @param temp object that will be added
	 */
	public void addObject(GameObject temp)
	{
		objects.add(temp);
	}
	/**
	 * remove object from objects linked list
	 * @param temp object to delete
	 */
	public void removeObject(GameObject temp)
	{
		objects.remove(temp);
	}
	/**
	 * size of objects linked list
	 * @return size
	 */
	public int getSize()
	{
		return objects.size();
	}
	/**
	 * if player is alive
	 * @return true if alive false if dead
	 */
	public boolean isAlive() 
	{
		return isAlive;
	}
	/**
	 * sets if player lives or is dead
	 * @param isAlive true if lives false if dead
	 */
	public void setAlive(boolean isAlive) 
	{
		this.isAlive = isAlive;
	}
	/**
	 * get score of game
	 * @return score
	 */
	public int getScore() 
	{
		return score;
	}
	/**
	 * get lvl of game
	 * @return level
	 */
	public int getLevel() 
	{
		return lvl;
	}
	/**
	 * sets score
	 * @param score what score it should be
	 */
	public void setScore(int score) 
	{
		this.score = score;
	}
	/**
	 * sets level
	 * @param level what level it should be 
	 */
	public void setLevel(int level) 
	{
		this.lvl = level;
	}
	/**
	 * adds to score
	 * @param howMuch it will be added
	 */
	public void addToScore(int howMuch) 
	{
		this.score += howMuch;
	}
	/**
	 * if boss is alive
	 * @return true if alive false if dead
	 */
	public boolean isBoss(){
		return isBoss;
	}
	/**
	 * sets boss is alive
	 * @param true if alive false if dead
	 */
	public void setBoss(boolean isBoss){
		this.isBoss = isBoss;
	}
	/**
	 * get reference to boss
	 * @return reference to boss
	 */
	public Boss getBoss()
	{	
        return boss;
	}
	/**
	 * sets game state
	 * @param state will be set
	 */
	public void setCurrentState(GameState state)
	{
		this.currentState = state;
	}
	/**
	 * get current game state
	 * @return current state
	 */
	public GameState getCurrentState()
	{
		return this.currentState;
	}
	/**
	 * if game is ready to run, state is ready
	 * @return true if state is ready false if it's not
	 */
	public boolean isReady(){
		return currentState == GameState.READY;
	}
	/**
	 * if current state is game over
	 * @return true if state is game over false if it's not
	 */
	public boolean isGameOver(){
		return currentState == GameState.GAMEOVER;
	}
	/**
	 * if current state is win
	 * @return true if state is game over false if it's not
	 */
	public boolean isWin(){
		return currentState == GameState.WIN;
	}
}
