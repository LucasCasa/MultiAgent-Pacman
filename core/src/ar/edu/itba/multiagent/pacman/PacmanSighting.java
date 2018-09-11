package ar.edu.itba.multiagent.pacman;

import com.badlogic.gdx.math.Vector2;

public class PacmanSighting {

	private Vector2 position;
	private Direction direction;

	public PacmanSighting(Vector2 p, Direction d){
		position = p;
		direction = d;
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

}
