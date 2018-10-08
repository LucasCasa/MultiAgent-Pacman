package ar.edu.itba.multiagent.pacman.player;

import ar.edu.itba.multiagent.pacman.Direction;
import ar.edu.itba.multiagent.pacman.GameObject;
import ar.edu.itba.multiagent.pacman.environment.GameMap;

public class Player extends GameObject {

	public Player(GameMap gm, boolean lockToGrid){
		super(gm, 100, lockToGrid);

	}

	/** Cancel the desired direction
	 * @param d the direction you no longer want to go
	 */
	void noLongerDesired(Direction d){
		if(desiredDirection == d){
			desiredDirection = null;
		}
	}

	public void update(float deltaTime) {
		super.update(deltaTime);
		gameMap.eatDot(getPosition());
	}
}
