package com.mygdx.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.game.Galaxy;
import com.mygdx.gameobjects.BasicStone;
import com.mygdx.gameobjects.GameObject;
import com.mygdx.gameobjects.ID;
import com.mygdx.gameobjects.SmallStone;
import com.mygdx.gameworld.GameWorld.GameState;

import my.gdx.helpers.AssetLoader;


/**
 * View class, renders graphics, HUD and keeps Camera
 * @author Kokos
 *	
 */
public class GameRenderer {
	
	private GameWorld world;
	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batcher;
	private float greenValue;
	public static boolean drawHB = false;
	public static boolean mute = false;
	public static boolean showState = false;
	/**
	 * Class Constructor
	 * @param world - world that needs to be rendered
	 */
	public GameRenderer(GameWorld world)
	{
		this.world = world;
			
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Galaxy.WIDTH/2, Galaxy.HEIGHT/2);
		
		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(cam.combined);
		
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);
		playMusic();
		}
	/**
	 * Renders objects and HUD
	 * @param runTime how long does the game is played 
	 */
	public void render(float runTime)
	{	
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batcher.begin();
        batcher.draw(AssetLoader.bg, 0, 0 , 250, 325);     
        shapeRenderer.begin(ShapeType.Filled);
      
        renderObjects();
        renderHUD();
        
        batcher.end();
        shapeRenderer.end();      
        if(drawHB) drawHitboxes(); 
	}

	/**
	 * Renders all objects from 'objects' vector in GameWorld class  
	 */
	private void renderObjects()
	{
		TextureRegion texture;
        for(int i = 0; i < world.objects.size(); i++)
        {	
        	GameObject tempObject = world.objects.get(i);
        	texture = findTexture(tempObject);
        	batcher.draw(texture, tempObject.getX(), tempObject.getY(), tempObject.getWidth(), tempObject.getHeight());
        }
	}

	/**
	 * Find textures by object ID and load it from AssetLoader
	 * @param object - object that texture is searched 
	 * @return TextureRegion that fits object
	 */
	private TextureRegion findTexture(GameObject object)
	{
		if(object.getID() == ID.Player)
		{
			if(object.isHit())
				return AssetLoader.playerAdidasHit;
			else
				return AssetLoader.playerAdidas;
		}
		else if(object.getID() == ID.PlayerBullet)
		{
			return AssetLoader.bulletTexture;				
		}
		else if(object.getID() == ID.BasicStone)
		{
			BasicStone tmp = (BasicStone)object;
			switch(tmp.getTypeOfStone())
			{
			case 1:
				return AssetLoader.stone1;
			case 2:
				return AssetLoader.stone2;
			case 3:
				return AssetLoader.stone3;
			case 4:
				return AssetLoader.stone4;
			default:
				return AssetLoader.stone1;
			}
		}
		else if(object.getID() == ID.SmallStone)
		{
			SmallStone tmp = (SmallStone)object;
			switch(tmp.getTypeOfStone())
			{
			case 1:
				return AssetLoader.stone1;
			case 2:
				return AssetLoader.stone2;
			case 3:
				return AssetLoader.stone3;
			case 4:
				return AssetLoader.stone4;
			default:
				return AssetLoader.stone1;
			}
		}
		else if(object.getID() == ID.BasicEnemy)
		{
			return AssetLoader.basicEnemy;
		}
		else if(object.getID() == ID.ShootingEnemy)
		{
			if(object.isHit())
				return AssetLoader.shootingEnemyHit;
			else
				return AssetLoader.shootingEnemy;
		}
		else if(object.getID() == ID.ShootingEnemyBullet)
		{
			return AssetLoader.shootingEnemyBullet;				
		}
		else if(object.getID() == ID.Boss)
		{
			if(object.isHit())
				return AssetLoader.bosshit;
			else
				return AssetLoader.boss;			
		}
		else if(object.getID() == ID.BossBullet)
		{
			return AssetLoader.bossBullet;				
		}
		else if(object.getID() == ID.Heal)
		{
			return AssetLoader.heal;				
		}
		else if(object.getID() == ID.Star)
		{
			return AssetLoader.star;				
		}
		
		return null;
	}
	
	/**
	 * Renders HUD for menu, running game, win and lost
	 */
	private void renderHUD()
	{
		GameState currentState = world.getCurrentState();
		boolean isBoss = world.isBoss();
		
        AssetLoader.font.getData().setScale(0.08f);
        AssetLoader.shadow.getData().setScale(0.08f);
        AssetLoader.shadow.draw(batcher, "By Konrad Kucharczyk", 165, 10);
        AssetLoader.font.draw(batcher, "By Konrad Kucharczyk", 165, 10);
		
		if(showState)
		{
	        AssetLoader.font.getData().setScale(0.1f);
	        AssetLoader.shadow.getData().setScale(0.1f);
	        AssetLoader.shadow.draw(batcher, "State: " + currentState, 180, 310);
	        AssetLoader.font.draw(batcher, "State: " + currentState, 180, 310);
		}
		
        if(currentState == GameState.READY)
        {        
	        AssetLoader.font.getData().setScale(0.5f);
	        AssetLoader.shadow.getData().setScale(0.5f);
	            
	        AssetLoader.shadow.draw(batcher, "Galaxy Bass", 18, 250);
	        AssetLoader.font.draw(batcher, "Galaxy Bass", 18, 250);
	        
	        AssetLoader.font.getData().setScale(0.25f);
	        AssetLoader.shadow.getData().setScale(0.25f);
	            
	        AssetLoader.shadow.draw(batcher, "Press space!", 60, 200);
	        AssetLoader.font.draw(batcher, "Press space!", 60, 200);
	                    
	        AssetLoader.font.getData().setScale(0.2f);
	        AssetLoader.shadow.getData().setScale(0.2f);
        }
		
		
        if(currentState == GameState.RUNNING)
        {
        AssetLoader.font.getData().setScale(0.15f);
        AssetLoader.shadow.getData().setScale(0.15f);
        AssetLoader.shadow.draw(batcher, "Level: " + world.getLevel(), 15, 310);
        AssetLoader.font.draw(batcher, "Level: " + world.getLevel(), 15, 310);
        
        AssetLoader.shadow.draw(batcher, "Score: " + world.getScore(), 15, 290);
        AssetLoader.font.draw(batcher, "Score: " + world.getScore(), 15, 290);
        
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(9, 14, 12, 102);
		
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(10, 15, 10, 100);
        
        greenValue = (float) (world.getPlayer().getHealth() * 0.01);
               
        shapeRenderer.setColor(new Color(1 - greenValue, greenValue, 0, 1));
        shapeRenderer.rect(10, 15, 10, world.getPlayer().getHealth());
        
        if(isBoss)
       	{
            shapeRenderer.setColor(Color.CYAN);
            shapeRenderer.rect(100, 280, 102, 17);
    		
            shapeRenderer.setColor(Color.GRAY);
            shapeRenderer.rect(101, 281, 100, 15);
            
            greenValue = (float) (world.getBoss().getHealth() * 0.01);
                   
            shapeRenderer.setColor(new Color(1 - greenValue, greenValue, 0, 1));
            shapeRenderer.rect(101, 281, world.getBoss().getHealth(), 15);
       	}
        
        }
          
        
        if(currentState == GameState.WIN || currentState == GameState.GAMEOVER)
        {        
	        AssetLoader.font.getData().setScale(0.3f);
	        AssetLoader.shadow.getData().setScale(0.3f);
	        AssetLoader.shadow.draw(batcher, "Score: " + world.getScore(), 65, 290);
	        AssetLoader.font.draw(batcher, "Score: " + world.getScore(), 65, 290);
        	
        	
	        AssetLoader.font.getData().setScale(0.45f);
	        AssetLoader.shadow.getData().setScale(0.45f);
	        
	        if(currentState == GameState.WIN)
	        {
		        AssetLoader.shadow.draw(batcher, "You Won!", 50, 250);
		        AssetLoader.font.draw(batcher, "You Won!", 50, 250);
	        }
	        else{
		        AssetLoader.shadow.draw(batcher, "Game Over!", 35, 250);
		        AssetLoader.font.draw(batcher, "Game Over!", 35, 250);
	        }
	            
	        
	        AssetLoader.font.getData().setScale(0.2f);
	        AssetLoader.shadow.getData().setScale(0.2f);
	        
	        AssetLoader.shadow.draw(batcher, "High Score: " + AssetLoader.getHighScore(), 65, 210);
	        AssetLoader.font.draw(batcher, "High Score: " + AssetLoader.getHighScore(), 65, 210);
	        
	        AssetLoader.shadow.draw(batcher, "Press space to restart", 35, 180);
	        AssetLoader.font.draw(batcher, "Press space to restart", 35, 180);
	          
        }
        
     
	}
	
	/**
	 * play Laser sound
	 */
	public static void playLaser()
	{
		if(!mute)AssetLoader.laser.play(0.3f);
	}
	/**
	 * starts music
	 */
	public static void playMusic()
	{
		if(!mute)
		{
	        AssetLoader.music.setVolume(0.05f);
	        AssetLoader.music.setLooping(true);
	        AssetLoader.music.play();
		}
	}
	/**
	 * stops music
	 */
	public static void stopMusic()
	{
		AssetLoader.music.pause();
	}
	/**
	 * shows hit boxes of object, only for debug and setting hit boxes
	 */
	private void drawHitboxes()
	{
	  shapeRenderer.begin(ShapeType.Line);
	  
      shapeRenderer.setColor(Color.YELLOW);   
      for(int i = 0; i < world.objects.size(); i++)
      {	
      	GameObject tempObject = world.objects.get(i);
      	shapeRenderer.circle(tempObject.getBoundingCircle().x, tempObject.getBoundingCircle().y, tempObject.getBoundingCircle().radius);
      }
      
      shapeRenderer.end();
	}

}
