package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.screens.GameScreen;

import my.gdx.helpers.AssetLoader;

public class Galaxy extends Game {
	SpriteBatch batch;
	Texture img;
	
	public static int WIDTH = 475;
	public static int HEIGHT = 650;
	
	@Override
	public void create () {
		Gdx.app.log("Galaxy", "Created");
		AssetLoader.load();
		setScreen(new GameScreen());
		
	}
	
	@Override
	public void dispose () {
		super.dispose();
		AssetLoader.dispose();
	}
}
