package ar.edu.itba.multiagent.pacman.agents;

import ar.edu.itba.multiagent.pacman.Direction;
import ar.edu.itba.multiagent.pacman.GameMap;
import ar.edu.itba.multiagent.pacman.GameObject;

public class Ghost extends GameObject {
	float timeToChange = 0f;
	public Ghost(GameMap gm) {
		super(gm);
	}

	public void update(float deltaTime){
		timeToChange += deltaTime;
		if(!canMove(getDirection().directionVector())){
			timeToChange = 99;
		}
		if(timeToChange > 2) {
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
		super.update(deltaTime);
	}
}
