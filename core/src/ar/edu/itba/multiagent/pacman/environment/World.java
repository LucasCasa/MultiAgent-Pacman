package ar.edu.itba.multiagent.pacman.environment;

import ar.edu.itba.multiagent.pacman.Direction;
import ar.edu.itba.multiagent.pacman.agents.SensingAgent;
import ar.edu.itba.multiagent.pacman.player.AIPlayer;
import ar.edu.itba.multiagent.pacman.player.Player;
import ar.edu.itba.multiagent.pacman.agents.Ghost;
import com.badlogic.gdx.math.GridPoint2;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.stream.Collectors;

public class World {
	private GameMap gm;
	private Player player;
	private List<Ghost> agents;
	private EnemySighting blackboard;
	private int turn;

	public World(GameMap gm, Player p, List<Ghost> agents){
		this.gm = gm;
		this.player = p;
		this.agents = agents;
	}

	public void update(int turn){
		this.turn = turn;
	}

	public EnemySighting sense(SensingAgent agent){
		List<GridPoint2> enemiesPosition;
		if(agent instanceof Ghost){
			enemiesPosition = ImmutableList.of(PositionUtils.worldToBoard(player.getPosition()));
		} else if( agent instanceof AIPlayer){
			enemiesPosition = agents.stream().map(a -> PositionUtils.worldToBoard(a.getPosition())).collect(Collectors.toList());
		} else {
			throw new IllegalStateException("Sensing Agent isn't a ghost or a player");
		}
		for (Direction dir : Direction.values()) {
			if (agent.getVisibilityDirections().get(dir.ordinal())) {
				GridPoint2 currentPosition = PositionUtils.worldToBoard(agent.getPosition());
				boolean wall = false;
				for (int i = 0; i < agent.getVisibility() && !wall; i++) {
					for(GridPoint2 enemy: enemiesPosition) {
						if (currentPosition.equals(enemy)) {
							Direction d = getDirectionOfEnemy(enemy);
							return new EnemySighting(PositionUtils.boardToWorld(enemy), d, turn);
						}
						if (gm.hasWall(currentPosition, dir.directionVector())) {
							wall = true;
						}
					}
					currentPosition.add(dir.directionVector());
				}
			}
		}
		return null;
	}

	/**
	 * Given a position, then we get the gameobject in that position and return its direction
	 * @param enemy desired position
	 * @return direction of the desired GameObject
	 */
	private Direction getDirectionOfEnemy(GridPoint2 enemy) {
		if(enemy.equals(PositionUtils.worldToBoard(player.getPosition()))){
			return player.getDirection();
		} else {
			for(Ghost g : agents){
				if(enemy.equals(PositionUtils.worldToBoard(g.getPosition()))){
					return g.getDirection();
				}
			}
		}
		return null;
	}

	public EnemySighting pollBlackBoard(){
		return blackboard;
	}

	public void writeBlackBoard(EnemySighting data){
		blackboard = data;
	}

	public void setPlayer(Player p) {
		this.player = p;
	}
}