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
		if (turn - p.getTurn() < 1000) {
			if (p.getPosition().dst(self.getPosition()) < 10) {
				self.getWorld().writeBlackBoard(null);
				self.tryToChangeDirection(p.getDirection());
			} else if (self.getClosestGhost() != self.getPosition() && self.getClosestGhost().dst(self.getPosition()) < 60) {
				if(self.getPosition().dst(p.getPosition())< 40) {
					chase(self, p);
				}else{
					reject(self,self.getClosestGhost());
				}
			} else {
//				System.out.println("HERE");
				chase(self, p);
			}
//			chase(self,p);
		}

	}

	private void reject(Ghost self, Vector2 closest ) {
		System.out.println("REJECTING");
		float dx = closest.x - self.getPosition().x;
		float dy = closest.y - self.getPosition().y;
		Vector2 dir = new Vector2(self.getPosition().x - dx ,self.getPosition().y - dy);
		self.tryToChangeDirection(PositionUtils.getBestDirection(self.getPosition(), dir, self.getValidDirections()));
	}

	private void chase(Ghost self, EnemySighting p) {
		Vector2 pacManPosition = p.getPosition();
		boolean[] valid = self.getValidDirections();
		self.tryToChangeDirection(PositionUtils.getBestDirection(self.getPosition(), pacManPosition, valid));
	}
}
