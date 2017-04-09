package my.gdx.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class AssetLoader {

	public static TextureRegion bg;
	public static TextureRegion playerTexture, playerAdidas, playerAdidasHit;
	public static TextureRegion bulletTexture;
	public static TextureRegion backGround;
	public static TextureRegion stone1,stone2,stone3,stone4;
	public static TextureRegion basicEnemy;
	public static TextureRegion heal;
	public static TextureRegion boss, bosshit, bossBullet;
	public static BitmapFont font,font2, shadow;
	
	public static Sound laser;
	public static Music music;


	
	public static void load()
	{
		bg =  new TextureRegion(new Texture(Gdx.files.internal("bgb.png")), 256 , 256);
		
		playerTexture = new TextureRegion(new Texture(Gdx.files.internal("playerAsset.png")), 112 , 75);
		bulletTexture = new TextureRegion(new Texture(Gdx.files.internal("bullet.png")), 13 , 54);
		
		playerAdidas = new TextureRegion(new Texture(Gdx.files.internal("playerAdidas.png")), 224 , 150);
		playerAdidasHit = new TextureRegion(new Texture(Gdx.files.internal("playerAdidasHit.png")), 224 , 150);

		stone1 =  new TextureRegion(new Texture(Gdx.files.internal("stone1.png")), 101 , 84);
		stone2 =  new TextureRegion(new Texture(Gdx.files.internal("stone2.png")), 120 , 98);
		stone3 =  new TextureRegion(new Texture(Gdx.files.internal("stone3.png")), 89 , 82);
		stone4 =  new TextureRegion(new Texture(Gdx.files.internal("stone4.png")), 98 , 96);
		

		basicEnemy =  new TextureRegion(new Texture(Gdx.files.internal("enemy.png")), 103 , 84);
		boss =  new TextureRegion(new Texture(Gdx.files.internal("boss1.png")), 91 , 91);
		bosshit =  new TextureRegion(new Texture(Gdx.files.internal("boss1hit.png")), 91 , 91);
		bossBullet =  new TextureRegion(new Texture(Gdx.files.internal("bossBullet.png")), 48, 46);
		
		heal =  new TextureRegion(new Texture(Gdx.files.internal("heal.png")), 34 , 33);

        font = new BitmapFont(Gdx.files.internal("text.fnt"));
        font.getData().setScale(0.15f);
        shadow = new BitmapFont(Gdx.files.internal("shadow.fnt"));
        shadow.getData().setScale(0.15f);
        
        laser = Gdx.audio.newSound(Gdx.files.internal("las1.wav"));
        music = Gdx.audio.newMusic(Gdx.files.internal("musicloop.mp3"));

        
		
	}
	public static void dispose()
	{
	}
}
