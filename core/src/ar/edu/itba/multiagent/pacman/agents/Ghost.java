package ar.edu.itba.multiagent.pacman.agents;

import ar.edu.itba.multiagent.pacman.Direction;
import ar.edu.itba.multiagent.pacman.environment.GameMap;
import ar.edu.itba.multiagent.pacman.GameObject;
import ar.edu.itba.multiagent.pacman.PacmanSighting;
import ar.edu.itba.multiagent.pacman.environment.World;
import com.badlogic.gdx.math.Vector2;
import com.typesafe.config.Config;

public class Ghost extends GameObject {
	private int visibility;
	private float timeToChange = 0f;
	private World w;
	public Ghost(GameMap gm, Config c, World w) {
		super(gm, c.getInt("speed"));
		this.visibility = c.getInt("visibility");
		this.w = w;
	}

	public void update(float deltaTime){
		PacmanSighting p = w.sense(this);

		if(p != null) {
			w.writeBlackBoard(new PacmanSighting(p.getPosition().cpy(), p.getDirection()));
		} else {
			p = w.pollBlackBoard();
		}

		if(p != null){
			chase(p);
		}else {
			timeToChange += deltaTime;
			if (!canMove(getDirection().directionVector())) {
				timeToChange = 99;
			}
			if (timeToChange > 2) {
				timeToChange = 0;
				switch ((int) (Math.random() * 4)) {
					case 0:
						tryToChangeDirection(Direction.UP);
						break;
					case 1:
						tryToChangeDirection(Direction.DOWN);
						break;
					case 2:
						tryToChangeDirection(Direction.LEFT);
						break;
					case 3:
						tryToChangeDirection(Direction.RIGHT);
				}
			}
		}
		super.update(deltaTime);
	}

	private void chase(PacmanSighting p) {
		Vector2 pacManPosition = p.getPosition();
		boolean success = false;
		if(pacManPosition.x - getPosition().x > 1f) {
			success = tryToChangeDirection(Direction.RIGHT);
		}
		if(pacManPosition.x - getPosition().x < -1f && !success){
			success = tryToChangeDirection(Direction.LEFT);
		}
		if(pacManPosition.y - getPosition().y > 1f && !success) {
			success = tryToChangeDirection(Direction.UP);
		}
		if(pacManPosition.y - getPosition().y < -1f && !success) {
			tryToChangeDirection(Direction.DOWN);
		}
	}


	public int getVisibility() {
		return visibility;
	}
}
