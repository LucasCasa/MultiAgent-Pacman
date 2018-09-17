package ar.edu.itba.multiagent.pacman.agents;

import ar.edu.itba.multiagent.pacman.Direction;
import ar.edu.itba.multiagent.pacman.environment.GameMap;
import ar.edu.itba.multiagent.pacman.GameObject;
import ar.edu.itba.multiagent.pacman.PacmanSighting;
import ar.edu.itba.multiagent.pacman.environment.World;
import com.badlogic.gdx.math.Vector2;
import com.typesafe.config.Config;

import java.util.List;
import java.util.Random;

public class Ghost extends GameObject {
	private int visibility;
	private List<Boolean> visiblityDirections;
	private float timeToChange = 0f;
	private World w;
	private Random random;
	public Ghost(GameMap gm, Config c, World w) {
		super(gm, c.getInt("speed"));
		this.visibility = c.getInt("visibility");
		this.visiblityDirections = c.getBooleanList("visibility-directions");
		this.w = w;
		this.random = new Random(c.getInt("seed"));
	}

	public void update(float deltaTime, int turn){
		PacmanSighting p = w.sense(this);

		if(p != null) {
			w.writeBlackBoard(p);
		} else {
			p = w.pollBlackBoard();
			if(p != null && (turn - p.getTurn()) > 5 / deltaTime)
				p = null;
		}

		if(p != null){
			if(p.getPosition().dst2(getPosition()) < 10){
				w.writeBlackBoard(null);
			}
			chase(p);
		}else {
			timeToChange += deltaTime;
			if (!canMove(getDirection().directionVector())) {
				timeToChange = 99;
			}
			if (timeToChange > 2) {
				timeToChange = 0;
				randomDirection();
			}
		}
		super.update(deltaTime);
	}

	private void chase(PacmanSighting p) {
		Vector2 pacManPosition = p.getPosition();
		//boolean success = false;
		//boolean isTrapped = canMove(getDirection().directionVector());
		//UP, DOWN, LEFT, RIGHT
		float[] forces = {pacManPosition.y - getPosition().y, getPosition().y - pacManPosition.y, getPosition().x - pacManPosition.x, pacManPosition.x - getPosition().x};
		boolean[] valid = {
				canMove(Direction.UP.directionVector()) && getDirection() != Direction.DOWN,
				canMove(Direction.DOWN.directionVector()) && getDirection() != Direction.UP,
				canMove(Direction.LEFT.directionVector()) && getDirection() != Direction.RIGHT,
				canMove(Direction.RIGHT.directionVector()) && getDirection() != Direction.LEFT
		};
		float maxValidForce = Integer.MIN_VALUE;
		int maxValidIndex = -1;
		for(int i = 0; i< 4; i++){
			if(valid[i] && forces[i] > maxValidForce){
				maxValidIndex = i;
				maxValidForce = forces[i];
			}
		}
		tryToChangeDirection(Direction.values()[maxValidIndex]);
	}

	private void randomDirection(){
		boolean success = false;
		while(!success) {
			switch (random.nextInt(4)) {
				case 0:
					if (getDirection() != Direction.DOWN) {
						success = tryToChangeDirection(Direction.UP);
						break;
					}
				case 1:
					if (getDirection() != Direction.UP) {
						success = tryToChangeDirection(Direction.DOWN);
						break;
					}
				case 2:
					if (getDirection() != Direction.RIGHT) {
						success = tryToChangeDirection(Direction.LEFT);
						break;
					}
				case 3:
					if (getDirection() != Direction.LEFT) {
						success = tryToChangeDirection(Direction.RIGHT);
					}
			}
		}
	}

	public int getVisibility() {
		return visibility;
	}

	public List<Boolean> getVisiblityDirections() {
		return visiblityDirections;
	}
}
