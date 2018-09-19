package ar.edu.itba.multiagent.pacman.environment;

import ar.edu.itba.multiagent.pacman.Direction;
import ar.edu.itba.multiagent.pacman.PacmanSighting;
import ar.edu.itba.multiagent.pacman.player.Player;
import ar.edu.itba.multiagent.pacman.agents.Ghost;
import com.badlogic.gdx.math.GridPoint2;

public class World {
	private GameMap gm;
	private Player player;
	private PacmanSighting blackboard;
	private int turn;

	public World(GameMap gm, Player p){
		this.gm = gm;
		this.player = p;
	}

	public void update(float deltaTime, int turn){
		this.turn = turn;
	}

	public PacmanSighting sense(Ghost agent){
		for (Direction dir: Direction.values()){
			if(agent.getVisibilityDirections().get(dir.ordinal())) {
				GridPoint2 currentPosition = PositionUtils.worldToBoard(agent.getPosition());
				GridPoint2 playerPosition = PositionUtils.worldToBoard(player.getPosition());
				for (int i = 0; i < agent.getVisibility(); i++) {
					if (currentPosition.equals(playerPosition)) {
						return new PacmanSighting(player.getPosition().cpy(), player.getDirection(), turn);
					}
					if (gm.hasWall(currentPosition, dir.directionVector())) {
						break;
					}
					currentPosition.add(dir.directionVector());
				}
			}
		}
		return null;
	}

	public PacmanSighting pollBlackBoard(){
		return blackboard;
	}

	public void writeBlackBoard(PacmanSighting data){
		blackboard = data;
	}
}
