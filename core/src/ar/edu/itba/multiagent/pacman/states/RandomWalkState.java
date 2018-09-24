package ar.edu.itba.multiagent.pacman.states;

import ar.edu.itba.multiagent.pacman.Direction;
import ar.edu.itba.multiagent.pacman.GameObject;

import java.util.Random;

public class RandomWalkState implements State{
	private float timeToChange = 0f;

	@Override
	public void update(GameObject self, float deltaTime, int turn, Random random) {
		timeToChange += deltaTime;

		if (!self.canMove(self.getDirection().directionVector())) {
			timeToChange = 99; // Force a change
		}

		if (timeToChange > 2) {
			timeToChange = 0;
			randomDirection(self, random);
		}
	}

	private void randomDirection(GameObject self, Random random){
		boolean success = false;
		while(!success) {
			switch (random.nextInt(4)) {
				case 0:
					if (self.getDirection() != Direction.DOWN) {
						success = self.tryToChangeDirection(Direction.UP);
						break;
					}
				case 1:
					if (self.getDirection() != Direction.UP) {
						success = self.tryToChangeDirection(Direction.DOWN);
						break;
					}
				case 2:
					if (self.getDirection() != Direction.RIGHT) {
						success = self.tryToChangeDirection(Direction.LEFT);
						break;
					}
				case 3:
					if (self.getDirection() != Direction.LEFT) {
						success = self.tryToChangeDirection(Direction.RIGHT);
					}
			}
		}
	}
}
