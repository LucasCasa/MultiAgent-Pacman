package ar.edu.itba.multiagent.pacman.player;

import ar.edu.itba.multiagent.pacman.Direction;
import ar.edu.itba.multiagent.pacman.MultiagentPacman;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class PlayerInput implements InputProcessor {

	private Player player;
	private MultiagentPacman pause;
	public PlayerInput(Player p, MultiagentPacman pause){
		player = p;
		this.pause = pause;
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode){
			case Input.Keys.UP:
				player.tryToChangeDirection(Direction.UP);
				break;
			case Input.Keys.DOWN:
				player.tryToChangeDirection(Direction.DOWN);
				break;
			case Input.Keys.LEFT:
				player.tryToChangeDirection(Direction.LEFT);
				break;
			case Input.Keys.RIGHT:
				player.tryToChangeDirection(Direction.RIGHT);
				break;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode){
			case Input.Keys.UP:
				player.noLongerDesired(Direction.UP);
				break;
			case Input.Keys.DOWN:
				player.noLongerDesired(Direction.DOWN);
				break;
			case Input.Keys.LEFT:
				player.noLongerDesired(Direction.LEFT);
				break;
			case Input.Keys.RIGHT:
				player.noLongerDesired(Direction.RIGHT);
				break;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		if(character == ' '){
			pause.pause = !pause.pause;
		}
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
