package ar.edu.itba.multiagent.pacman.environment;

import ar.edu.itba.multiagent.pacman.Direction;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;

public class PositionUtils {

	public static GridPoint2 worldToBoard(Vector2 position) {
		int px = (int)(position.x / 16);
		int py = (int)((position.y - 32) / 16);
		return new GridPoint2(px, py);
	}

	public static Vector2 boardToWorld(GridPoint2 position) {
		float px = position.x * 16;
		float py = position.y * 16 + 32;
		return new Vector2(px, py);
	}

	//UP, DOWN, LEFT, RIGHT
	public static Direction getBestDirection(Vector2 from, Vector2 to, boolean[] valid){
		float[] forces = {to.y - from.y, from.y - to.y, from.x - to.x, to.x - from.x};

		float maxValidForce = Integer.MIN_VALUE;
		int maxValidIndex = -1;
		for(int i = 0; i< 4; i++){
			if(valid[i] && forces[i] > maxValidForce){
				maxValidIndex = i;
				maxValidForce = forces[i];
			}
		}
		return Direction.values()[maxValidIndex];
	}

	//Also need valid
	public static Direction forceVectorToDirection(Vector2 force, boolean[] valid){
		float[] forces = {force.y, -force.y, -force.x, force.x};
		float maxValidForce = Integer.MIN_VALUE;
		int maxValidIndex = -1;
		for(int i = 0; i< 4; i++){
			if(valid[i] && forces[i] > maxValidForce){
				maxValidIndex = i;
				maxValidForce = forces[i];
			}
		}
		return Direction.values()[maxValidIndex];
	}
	public static Vector2 gridToVector(GridPoint2 p){
		return new Vector2(p.x, p.y);
	}

	public static Direction getInverseDirection(Direction direction) {
		switch (direction){
			case DOWN:
				return Direction.UP;
			case UP:
				return Direction.DOWN;
			case LEFT:
				return Direction.RIGHT;
			case RIGHT:
				return Direction.LEFT;
		}
		return null;
	}
}
