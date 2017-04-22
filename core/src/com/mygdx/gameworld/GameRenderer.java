package com.mygdx.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.game.Galaxy;
import com.mygdx.gameobjects.GameObject;


import my.gdx.helpers.AssetLoader;



public class GameRenderer {
	
	private GameWorld world;
	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batcher;
	private float greenValue;
	public static boolean drawHB = false;
	public static boolean mute = false;

	
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
	
	private void renderObjects()
	{
        for(int i = 0; i < world.objects.size(); i++)
        {	
        	GameObject tempObject = world.objects.get(i);
        	batcher.draw(tempObject.getTexture(), tempObject.getX(), tempObject.getY(), tempObject.getWidth(), tempObject.getHeight());
        }
	}
	
	private void renderHUD()
	{
       
        if(world.isAlive())
        {
        //Print lvl
        AssetLoader.shadow.draw(batcher, "Level: " + world.getLevel(), 15, 310);

        AssetLoader.font.draw(batcher, "Level: " + world.getLevel(), 15, 310);
        
        //Print score
        AssetLoader.shadow.draw(batcher, "Score: " + world.getScore(), 15, 290);

        AssetLoader.font.draw(batcher, "Score: " + world.getScore(), 15, 290);
        
		//render HP bar
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(9, 14, 12, 102);
		
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(10, 15, 10, 100);
        
        greenValue = (float) (world.getPlayer().getHealth() * 0.01);
               
        shapeRenderer.setColor(new Color(1 - greenValue, greenValue, 0, 1));
        shapeRenderer.rect(10, 15, 10, world.getPlayer().getHealth());
        
        }

        if(world.isBoss() && world.isAlive())
        {
            shapeRenderer.setColor(Color.CYAN);
            shapeRenderer.rect(100, 280, 102, 17);
    		
            shapeRenderer.setColor(Color.GRAY);
            shapeRenderer.rect(101, 281, 100, 15);
            
            greenValue = (float) (world.getBoss().getHealth() * 0.01);
                   
            shapeRenderer.setColor(new Color(1 - greenValue, greenValue, 0, 1));
            shapeRenderer.rect(101, 281, world.getBoss().getHealth(), 15);

        }
            
        
        if(world.isBossKilled())
        {

	        AssetLoader.font.getData().setScale(0.4f);
	        AssetLoader.shadow.getData().setScale(0.4f);
	            
	        AssetLoader.shadow.draw(batcher, "You Won!", 60, 250);
	        AssetLoader.font.draw(batcher, "You Won!", 60, 250);
	            
	        AssetLoader.font.getData().setScale(0.3f);
	        AssetLoader.shadow.getData().setScale(0.3f);
	        AssetLoader.shadow.draw(batcher, "Score: " + world.getScore(), 65, 220);
	        AssetLoader.font.draw(batcher, "Score: " + world.getScore(), 65, 220);
	            
	        AssetLoader.font.getData().setScale(0.2f);
	        AssetLoader.shadow.getData().setScale(0.2f);
        }
        
        //Game Over 
        if(world.isKilled())
        {
        AssetLoader.font.getData().setScale(0.4f);
        AssetLoader.shadow.getData().setScale(0.4f);
        
        AssetLoader.shadow.draw(batcher, "Game Over!", 45, 250);
        AssetLoader.font.draw(batcher, "Game Over!", 45, 250);
        
        AssetLoader.font.getData().setScale(0.3f);
        AssetLoader.shadow.getData().setScale(0.3f);
        AssetLoader.shadow.draw(batcher, "Score: " + world.getScore(), 65, 220);
        AssetLoader.font.draw(batcher, "Score: " + world.getScore(), 65, 220);
        
        
        AssetLoader.font.getData().setScale(0.2f);
        AssetLoader.shadow.getData().setScale(0.2f);
        }
        
	}
	
	public static void playLaser()
	{
		if(!mute)AssetLoader.laser.play(1);
	}
	public static void playMusic()
	{
		if(!mute)
		{
	        AssetLoader.music.play();
	        AssetLoader.music.setVolume(0.05f);
	        AssetLoader.music.setLooping(true);
		}
	}
	public static void stopMusic()
	{
		AssetLoader.music.pause();
	}
		
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
