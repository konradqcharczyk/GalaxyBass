package my.gdx.helpers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.gameworld.GameRenderer;
import com.mygdx.gameworld.GameWorld;
/**
 * Manages input from user
 * @author Kokos
 *
 */
public class InputHandler implements InputProcessor{


	private GameWorld world;
	/**
	 * Class constructor
	 * @param world GameWorld that input will work on
	 */
	public InputHandler(GameWorld world)
	{
		this.world = world;
	}
	
	
	@Override
	/**
	 * handles all pushed down keys
	 * @param keycode code of key that is pressed
	 * @return if it's managed 
	 */
	public boolean keyDown(int keycode) {
		if(keycode == Keys.SPACE)
		{
			if(world.isReady())
			{
				world.start();
			}
			if(world.isGameOver() || world.isWin())
			{
				world.restart();
			}
		}
		
				
		else if(keycode == Keys.ESCAPE) System.exit(0);
		
		else if(keycode == Keys.H)
		{
			if(GameRenderer.drawHB) {
				GameRenderer.drawHB = false; 
				System.out.println("GameRenderer: HitBox off");
				}
			else {
				GameRenderer.drawHB = true;
				System.out.println("GameRenderer: HitBox on");
				}
		}
		else if(keycode == Keys.M)
		{
			if(GameRenderer.mute)
			{
				GameRenderer.mute = false;
				GameRenderer.playMusic();
				System.out.println("GameRenderer: Sound on");
				}
			else 
				{
				GameRenderer.mute = true;
				GameRenderer.stopMusic();      
				System.out.println("GameRenderer: Sound off");
				}
			
		}
		
		else if(keycode == Keys.O)
		{
			if(GameRenderer.showState)
			{
				GameRenderer.showState = false;
				System.out.println("GameRenderer: ShowState: off");
			}
			else 
				{
				GameRenderer.showState = true;
				System.out.println("GameRenderer: ShowState: on");
				}
			
		}
		
		
		if(world.getPlayer() == null)
			return false;
		
		if(keycode == Keys.D || keycode == Keys.RIGHT) world.getPlayer().moveRight(true);
		else if(keycode == Keys.A || keycode == Keys.LEFT) world.getPlayer().moveLeft(true);
		else if(keycode == Keys.W || keycode == Keys.UP) world.getPlayer().moveUp(true);
		else if(keycode == Keys.S || keycode == Keys.DOWN) world.getPlayer().moveDown(true);

		else if(keycode == Keys.P || keycode == Keys.DOWN) world.getPlayer().addHealth(30);
		
		return true;
	}

	/**
	 * handles all released keys
	 * @param keycode code of key that was released
	 * @return if it's managed 
	 */
	@Override
	public boolean keyUp(int keycode) {
		if(world.getPlayer() == null)
			return false;
		
		if(keycode == Keys.D || keycode == Keys.RIGHT) world.getPlayer().moveRight(false);
		else if(keycode == Keys.A || keycode == Keys.LEFT) world.getPlayer().moveLeft(false);
		else if(keycode == Keys.W || keycode == Keys.UP) world.getPlayer().moveUp(false);
		else if(keycode == Keys.S || keycode == Keys.DOWN) world.getPlayer().moveDown(false);
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {	 
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
