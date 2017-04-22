package my.gdx.helpers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.gameobjects.Player;
import com.mygdx.gameworld.GameRenderer;

public class InputHandler implements InputProcessor{

	private Player player;
	public InputHandler(Player player)
	{
		this.player = player;
	}
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.D || keycode == Keys.RIGHT) player.moveRight(true);
		else if(keycode == Keys.A || keycode == Keys.LEFT) player.moveLeft(true);
		else if(keycode == Keys.W || keycode == Keys.UP) player.moveUp(true);
		else if(keycode == Keys.S || keycode == Keys.DOWN) player.moveDown(true);
		else if(keycode == Keys.ESCAPE) System.exit(0);
		else if(keycode == Keys.SPACE) player.shoot();
		
		if(keycode == Keys.H)
		{
			if(GameRenderer.drawHB) {GameRenderer.drawHB = false; System.out.println("GameRenderer: HitBox off");}
			else 
				{GameRenderer.drawHB = true; System.out.println("GameRenderer: HitBox on");}
		}
		if(keycode == Keys.M)
		{
			if(GameRenderer.mute)
			{
				GameRenderer.mute = false;
				GameRenderer.playMusic();
				System.out.println("GameRenderer: Sound on");}
			else 
				{
				GameRenderer.mute = true;
				GameRenderer.stopMusic();      
				System.out.println("GameRenderer: Sound off");
				}
			
		}
		
		if(keycode == Keys.T)
		{
			if(player.autoShoot) {
				player.autoShoot = false;
				System.out.println("Player: AutoShot off");
			}
			else{
				player.autoShoot = true;
				System.out.println("Player: AutoShoot on");
			}
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.D || keycode == Keys.RIGHT) player.moveRight(false);
		else if(keycode == Keys.A || keycode == Keys.LEFT) player.moveLeft(false);
		else if(keycode == Keys.W || keycode == Keys.UP) player.moveUp(false);
		else if(keycode == Keys.S || keycode == Keys.DOWN) player.moveDown(false);
		return false;
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
