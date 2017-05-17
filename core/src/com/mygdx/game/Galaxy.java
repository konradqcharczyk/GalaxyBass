package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.screens.GameScreen;

import my.gdx.helpers.AssetLoader;
/**
 * Main class that keeps screens.
 * @author Kokos
 *
 */
public class Galaxy extends Game {
	SpriteBatch batch;
	Texture img;
	
	public static int WIDTH = 475;
	public static int HEIGHT = 650;
	
	@Override
	/**
	 * Load assets and sets screen
	 */
	public void create () {
		Gdx.app.log("Galaxy", "Created");
		AssetLoader.load();
		setScreen(new GameScreen());
		
	}
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Game#dispose()
	 */
	public void dispose () {
		super.dispose();
		AssetLoader.dispose();
	}
}
