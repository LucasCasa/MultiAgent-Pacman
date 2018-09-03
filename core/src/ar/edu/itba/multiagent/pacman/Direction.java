package ar.edu.itba.multiagent.pacman;

import com.badlogic.gdx.math.Vector2;

public enum Direction {
	UP(new Vector2(0, 1)), DOWN(new Vector2(0, -1)), LEFT(new Vector2(-1, 0)), RIGHT(new Vector2(1, 0));

	private Vector2 vector;

	Direction(Vector2 d){
		vector = d;
	}

	public Vector2 directionVector() {
		return vector;
	}
}
