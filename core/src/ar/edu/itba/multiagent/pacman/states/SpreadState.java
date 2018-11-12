package ar.edu.itba.multiagent.pacman.states;

import ar.edu.itba.multiagent.pacman.GameObject;
import ar.edu.itba.multiagent.pacman.agents.Ghost;
import ar.edu.itba.multiagent.pacman.environment.PositionUtils;
import com.badlogic.gdx.math.Vector2;
import com.typesafe.config.Config;

import java.util.Map;
import java.util.Random;

public class SpreadState implements State {

	private RandomWalkState randomState;
	private int radius;

	public SpreadState(Config c) {
		this.radius = c.getInt("spread-params.min-distance");
		randomState = new RandomWalkState();
	}

	@Override
	public void update(GameObject self, float deltaTime, int turn, Random r) {
		Ghost ghost = (Ghost) self;
		float dist = Integer.MAX_VALUE;
		int id = -1;
		for(Map.Entry<Integer, Vector2> p: ghost.getOtherGhosts().entrySet()){
			float distance = p.getValue().dst(self.getPosition());
			if (distance < dist){
				id = p.getKey();
				dist = distance;
			}
		}
		if(dist < 16 * radius){
			self.tryToChangeDirection(PositionUtils.getBestDirection(ghost.getOtherGhosts().get(id), self.getPosition(), ghost.getValidDirections()));
		} else {
			randomState.update(self, deltaTime, turn, r);
		}
	}
}
