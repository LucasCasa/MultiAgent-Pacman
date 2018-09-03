package ar.edu.itba.multiagent.pacman;

import com.badlogic.gdx.math.Vector2;

public class GameObject {
	private Direction direction = Direction.UP;
	private Vector2 position = new Vector2(0, 0);
	private int width = 16;
	private int height = 16;

	public GameObject() {

	}

	public GameObject(Direction d, Vector2 p){
		direction = d;
		position = p;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
