package ar.edu.itba.multiagent.pacman.environment;

import ar.edu.itba.multiagent.pacman.Direction;
import com.badlogic.gdx.math.Vector2;

public class EnemySighting implements SensingData {

	private Vector2 position;
	private Direction direction;
	private int speed;
	private int turn;

	public EnemySighting(Vector2 p, Direction d, int speed, int turn){
		position = p;
		direction = d;
		this.speed = speed;
		this.turn = turn;
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

	public int getTurn() {
		return turn;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
