package ar.edu.itba.multiagent.pacman.communication;

import ar.edu.itba.multiagent.pacman.Direction;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;

public class GhostPosition {
	private int id;
	private Vector2 position;
	private Direction direction;

	public GhostPosition(int id, Vector2 position, Direction direction){
		this.id = id;
		this.position = position;
		this.direction = direction;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
