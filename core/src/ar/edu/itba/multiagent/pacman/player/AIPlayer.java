package ar.edu.itba.multiagent.pacman.player;

import ar.edu.itba.multiagent.pacman.Direction;
import ar.edu.itba.multiagent.pacman.agents.SensingAgent;
import ar.edu.itba.multiagent.pacman.environment.EnemySighting;
import ar.edu.itba.multiagent.pacman.environment.GameMap;
import ar.edu.itba.multiagent.pacman.environment.PositionUtils;
import ar.edu.itba.multiagent.pacman.environment.World;
import ar.edu.itba.multiagent.pacman.states.RandomWalkState;
import com.google.common.collect.ImmutableList;
import javafx.geometry.Pos;

import java.util.*;

public class AIPlayer extends Player implements SensingAgent {
	private World w;
	private RandomWalkState state;
	private Random r;
	private List<Boolean> visibility = ImmutableList.of(true, true, true, true);

	public AIPlayer(GameMap gm, World w, boolean lockToGrid) {
		super(gm, lockToGrid);
		r = new Random(4503);
		this.w = w;
		state = new RandomWalkState();
	}

	@Override public void update(float deltaTime){
		List<EnemySighting> enemies = w.sense(this);
		if(enemies.isEmpty()){
			state.update(this, deltaTime, 0, r);
		} else {
			boolean[] valid = {
					canMove(Direction.UP.directionVector()),
					canMove(Direction.DOWN.directionVector()),
					canMove(Direction.LEFT.directionVector()),
					canMove(Direction.RIGHT.directionVector())};
			Set<Direction> desiredDirections = new LinkedHashSet<>();
			for(EnemySighting enemy : enemies){
				desiredDirections.add(PositionUtils.getBestDirection(enemy.getPosition(), getPosition(), valid));
			}
			Set<Direction> badDirections = new LinkedHashSet<>();
			boolean[] desired = new boolean[4];
			for(Direction d: desiredDirections){
				if(desiredDirections.contains(PositionUtils.getInverseDirection(d))){
					badDirections.add(d);
					badDirections.add(PositionUtils.getInverseDirection(d));
					desired[d.ordinal()] = false;
					desired[PositionUtils.getInverseDirection(d).ordinal()] = false;
				}
			}
			boolean changed = false;
			for(Direction d: desiredDirections){
				if(!badDirections.contains(d)){
					super.tryToChangeDirection(d);
					changed=true;
					break;
				}
			}
			for (int i = 0; i < desired.length && !changed; i++) {
				if(desired[i]){
					if(super.tryToChangeDirection(Direction.values()[i]))
						changed = true;
				}
			}
		}
		super.update(deltaTime);
	}

	@Override
	public List<Boolean> getVisibilityDirections() {
		return visibility;
	}

	@Override
	public int getVisibility() {
		return 7;
	}
}
