package ar.edu.itba.multiagent.pacman.environment;

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

	public static Vector2 gridToVector(GridPoint2 p){
		return new Vector2(p.x, p.y);
	}
}
