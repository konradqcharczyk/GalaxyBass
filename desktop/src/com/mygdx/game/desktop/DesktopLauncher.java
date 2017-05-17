package com.mygdx.game.desktop;


import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Galaxy;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Galaxy.WIDTH;
		config.height = Galaxy.HEIGHT;
		config.title = "GalaxyBass";
		config.resizable = false;
		config.addIcon("iconBig.png", FileType.Internal);
		config.addIcon("icon.png", FileType.Internal);
		new LwjglApplication(new Galaxy(), config);
	}
}
