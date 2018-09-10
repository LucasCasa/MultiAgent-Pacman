package ar.edu.itba.multiagent.pacman;

public class Player extends GameObject {

	public Player(GameMap gm){
		super(gm);
	}

	/** Cancel the desired direction
	 * @param d the direction you no longer want to go
	 */
	public void noLongerDesired(Direction d){
		if(desiredDirection == d){
			desiredDirection = null;
		}
	}

	public void update(float deltaTime) {
		super.update(deltaTime);
		gameMap.eatDot(getPosition().x, getPosition().y);
	}
}
