package com.mygdx.screens;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.mygdx.gameworld.GameRenderer;
import com.mygdx.gameworld.GameWorld;

import my.gdx.helpers.InputHandler;

/**
 * Screen with game, it updates GameWorld and GameRender
 * @author Kokos
 *
 */
public class GameScreen implements Screen{
	
	private GameWorld world;
	private GameRenderer renderer;

	private float runTime = 0;

	/**
	 * Class constructor 
	 */
	public GameScreen()
	{       
		Gdx.app.log("GameScreen", "Attached");
		world = new GameWorld();
		renderer = new GameRenderer(world);
		Gdx.input.setInputProcessor(new InputHandler(world));
	}
	
	@Override

	public void render(float delta) {
		runTime += delta;
		world.update(delta);
		renderer.render(runTime);
	}

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("GameScreen", "resizing");
    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "show called");
    }

    @Override
    public void hide() {
        Gdx.app.log("GameScreen", "hide called");     
    }

    @Override
    public void pause() {
        Gdx.app.log("GameScreen", "pause called");        
    }

    @Override
    public void resume() {
        Gdx.app.log("GameScreen", "resume called");       
    }

    @Override
    public void dispose() {
    }
    /**
     * Sets value with borders
     * @param var value to be set
     * @param min minimum border
     * @param max maximzm border
     * @return value(if between border) or min or max border
     */
	public static float clamp(float var, float min, float max)
	{
		if(var >= max)
			return var = max;
		else if(var <= min)
			return var = min;
		else
			return var;
	}

}
