package ar.edu.itba.multiagent.pacman.states;

import ar.edu.itba.multiagent.pacman.Direction;
import ar.edu.itba.multiagent.pacman.GameObject;
import ar.edu.itba.multiagent.pacman.environment.EnemySighting;
import ar.edu.itba.multiagent.pacman.agents.Ghost;
import ar.edu.itba.multiagent.pacman.environment.PositionUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class ConvergeState implements State {

	public void update(GameObject self, float deltaTime, int turn, Random r) {
		update((Ghost) self, deltaTime, turn, r);
	}

	public void update(Ghost self, float deltaTime, int turn, Random r) {
		EnemySighting p = self.getWorld().pollBlackBoard();
		if (turn - p.getTurn() < 500){
			if(p.getPosition().dst(self.getPosition()) < 16){
				self.getWorld().writeBlackBoard(null);
				self.tryToChangeDirection(p.getDirection());
			} else {
				chase(self, p);
			}
		}
	}

	private void chase(Ghost self, EnemySighting p) {
		Vector2 pacManPosition = p.getPosition();
		boolean[] valid = self.getValidDirections();
		self.tryToChangeDirection(PositionUtils.getBestDirection(self.getPosition(), pacManPosition, valid));
	}
}
