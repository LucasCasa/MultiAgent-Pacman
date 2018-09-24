package ar.edu.itba.multiagent.pacman.player;

import ar.edu.itba.multiagent.pacman.Direction;
import ar.edu.itba.multiagent.pacman.agents.SensingAgent;
import ar.edu.itba.multiagent.pacman.environment.EnemySighting;
import ar.edu.itba.multiagent.pacman.environment.GameMap;
import ar.edu.itba.multiagent.pacman.environment.PositionUtils;
import ar.edu.itba.multiagent.pacman.environment.World;
import ar.edu.itba.multiagent.pacman.states.RandomWalkState;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Random;

public class AIPlayer extends Player implements SensingAgent {
	private World w;
	private RandomWalkState state;
	private Random r = new Random(784688759);
	public AIPlayer(GameMap gm, World w) {
		super(gm);
		this.w = w;
		state = new RandomWalkState();
	}

	@Override public void update(float deltaTime){
		EnemySighting enemy = w.sense(this);
		if(enemy == null){
			System.out.println("Random");
			state.update(this, deltaTime, 0, r);
		} else {
			boolean[] valid = {
					canMove(Direction.UP.directionVector()),
					canMove(Direction.DOWN.directionVector()),
					canMove(Direction.LEFT.directionVector()),
					canMove(Direction.RIGHT.directionVector())};
			Direction dir = PositionUtils.getBestDirection(enemy.getPosition(), getPosition(), valid);
			System.out.println("escapee  " +  dir);
			super.tryToChangeDirection(dir);
		}
		super.update(deltaTime);
	}

	@Override
	public List<Boolean> getVisibilityDirections() {
		return ImmutableList.of(true, true, true, true);
	}

	@Override
	public int getVisibility() {
		return 10;
	}
}
