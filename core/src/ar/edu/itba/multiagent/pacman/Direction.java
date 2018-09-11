package ar.edu.itba.multiagent.pacman;

import com.badlogic.gdx.math.GridPoint2;

public enum Direction {
	UP(new GridPoint2(0, 1)), DOWN(new GridPoint2(0, -1)), LEFT(new GridPoint2(-1, 0)), RIGHT(new GridPoint2(1, 0));

	private GridPoint2 vector;

	Direction(GridPoint2 d){
		vector = d;
	}

	public GridPoint2 directionVector() {
		return vector;
	}
}
