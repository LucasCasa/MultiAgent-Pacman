package ar.edu.itba.multiagent.pacman;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class PlayerInput implements InputProcessor {

	private Player player;

	public PlayerInput(Player p){
		player = p;
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode){
			case Input.Keys.UP:
				player.tryToChangeDirection(Direction.UP);
				System.out.println("UP");
				break;
			case Input.Keys.DOWN:
				player.tryToChangeDirection(Direction.DOWN);
				System.out.println("Down");
				break;
			case Input.Keys.LEFT:
				player.tryToChangeDirection(Direction.LEFT);
				System.out.println("left");
				break;
			case Input.Keys.RIGHT:
				player.tryToChangeDirection(Direction.RIGHT);
				System.out.println("right");
				break;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode){
			case Input.Keys.UP:
				player.noLongerDesired(Direction.UP);
				System.out.println("UP");
				break;
			case Input.Keys.DOWN:
				player.noLongerDesired(Direction.DOWN);
				System.out.println("Down");
				break;
			case Input.Keys.LEFT:
				player.noLongerDesired(Direction.LEFT);
				System.out.println("left");
				break;
			case Input.Keys.RIGHT:
				player.noLongerDesired(Direction.RIGHT);
				System.out.println("right");
				break;
		}
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
