package ar.edu.itba.multiagent.pacman.strategies;

import ar.edu.itba.multiagent.pacman.Direction;
import ar.edu.itba.multiagent.pacman.PacmanSighting;
import ar.edu.itba.multiagent.pacman.agents.Ghost;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class ConvergeState implements State {

	@Override
	public void update(Ghost self, float deltaTime, int turn, Random r) {
		PacmanSighting p = self.getWorld().pollBlackBoard();
		if (turn - p.getTurn() < 500){
			chase(self, p);
		}
	}

	private void chase(Ghost self, PacmanSighting p) {
		Vector2 pacManPosition = p.getPosition();

		//UP, DOWN, LEFT, RIGHT
		float[] forces = {pacManPosition.y - self.getPosition().y, self.getPosition().y - pacManPosition.y, self.getPosition().x - pacManPosition.x, pacManPosition.x - self.getPosition().x};
		boolean[] valid = {
				self.canMove(Direction.UP.directionVector()) && self.getDirection() != Direction.DOWN,
				self.canMove(Direction.DOWN.directionVector()) && self.getDirection() != Direction.UP,
				self.canMove(Direction.LEFT.directionVector()) && self.getDirection() != Direction.RIGHT,
				self.canMove(Direction.RIGHT.directionVector()) && self.getDirection() != Direction.LEFT
		};
		float maxValidForce = Integer.MIN_VALUE;
		int maxValidIndex = -1;
		for(int i = 0; i< 4; i++){
			if(valid[i] && forces[i] > maxValidForce){
				maxValidIndex = i;
				maxValidForce = forces[i];
			}
		}
		self.tryToChangeDirection(Direction.values()[maxValidIndex]);
	}
}
