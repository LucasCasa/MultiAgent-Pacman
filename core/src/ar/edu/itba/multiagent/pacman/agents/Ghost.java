package ar.edu.itba.multiagent.pacman.agents;

import ar.edu.itba.multiagent.pacman.Direction;
import ar.edu.itba.multiagent.pacman.communication.GhostPosition;
import ar.edu.itba.multiagent.pacman.communication.Message;
import ar.edu.itba.multiagent.pacman.communication.MessageType;
import ar.edu.itba.multiagent.pacman.environment.GameMap;
import ar.edu.itba.multiagent.pacman.GameObject;
import ar.edu.itba.multiagent.pacman.environment.EnemySighting;
import ar.edu.itba.multiagent.pacman.environment.World;
import ar.edu.itba.multiagent.pacman.states.State;
import ar.edu.itba.multiagent.pacman.states.StateUtils;
import com.badlogic.gdx.math.Vector2;
import com.typesafe.config.Config;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

public class Ghost extends GameObject implements SensingAgent {
	private int id;
	private int visibility;
	private List<Boolean> visiblityDirections;
	private State searchState;
	private State pursuitState;
	private Map<Integer, Vector2> otherGhosts;
	private World w;
	private Random random;
	private Queue<Message> messages;
	private boolean smell = true;
	private Vector2 closestGhost;
	private boolean canMoveBack;

	public Ghost(int id, GameMap gm, Config c, World w, boolean lockToGrid) {
		super(gm, c.getInt("speed"), lockToGrid);
		this.id = id;
		this.visibility = c.getInt("visibility");
		this.visiblityDirections = c.getBooleanList("visibility-directions");
		this.w = w;
		this.random = new Random(c.getInt("seed"));
		searchState = StateUtils.StringToState(c.getString("search-strategy"));
		pursuitState = StateUtils.StringToState(c.getString("pursuit-strategy"));
		messages = new LinkedList<>();
		otherGhosts = new HashMap<>();
		canMoveBack = c.getBoolean("can-move-back");
	}

	public void update(float deltaTime, int turn){
		List<EnemySighting> p = w.sense(this);
		EnemySighting pc;
		broadcastPosition();
		if(p.size() > 0) {
			pc = p.get(0);
			w.writeBlackBoard(p.get(0));
		} else {
			 pc = w.pollBlackBoard();
			if(pc != null && (turn - pc.getTurn()) > 5 / deltaTime)
				pc = null;
		}

		if(pc != null){
			closestGhost = this.getPosition();
		}
		while(!messages.isEmpty()){
			Message m = messages.poll();
			readMessage(m);
		}
		if(pc != null){
			pursuitState.update(this, deltaTime, turn, random);
			if(pc.getPosition().dst2(getPosition()) < 7){
				w.writeBlackBoard(null);
			}
		} else {
			searchState.update(this, deltaTime, turn, random);
		}
		super.update(deltaTime);
	}

	private void broadcastPosition() {
		Message m = new Message(MessageType.POSITION, id,  new GhostPosition(id, getPosition(), getDirection()));
		w.broadcast(m);
	}

	public void readMessage(Message m){
		switch (m.getType()) {
			case POSITION:
//				updateGhostPosition(m.getDataAsPosition());
				GhostPosition pos = m.getDataAsPosition();
				updateGhostPosition(pos);
				EnemySighting pc = w.pollBlackBoard();
				if(pc!=null){
					if(pos.getPosition().dst2(pc.getPosition()) < closestGhost.dst2(pc.getPosition())){
						closestGhost = pos.getPosition();
					}
				}
		}
	}

	private void updateGhostPosition(GhostPosition other) {
		otherGhosts.put(other.getId(), other.getPosition());
	}

	public Map<Integer, Vector2> getOtherGhosts(){
		return otherGhosts;
	}
	public int getVisibility() {
		return visibility;
	}
	public World getWorld() {
		return w;
	}
	public List<Boolean> getVisibilityDirections() {
		return visiblityDirections;
	}

	public int getId() {
		return id;
	}

	public void addMessage(Message message) {
		messages.add(message);
	}

	//UP, DOWN, LEFT, RIGHT
	public boolean[] getValidDirections() {
		if (canMoveBack) {
			return new boolean[]{
					canMove(Direction.UP.directionVector()),
					canMove(Direction.DOWN.directionVector()),
					canMove(Direction.LEFT.directionVector()),
					canMove(Direction.RIGHT.directionVector())
			};
		} else {
			return new boolean[]{
					canMove(Direction.UP.directionVector()) && getDirection() != Direction.DOWN,
					canMove(Direction.DOWN.directionVector()) && getDirection() != Direction.UP,
					canMove(Direction.LEFT.directionVector()) && getDirection() != Direction.RIGHT,
					canMove(Direction.RIGHT.directionVector()) && getDirection() != Direction.LEFT
			};
		}
	}

	public boolean isSmell() {
		return smell;
	}

	public Vector2 getClosestGhost() {
		return closestGhost;
	}

	/*public boolean[] getValidDirections() {
		return new boolean[]{
				canMove(Direction.UP.directionVector()),
				canMove(Direction.DOWN.directionVector()),
				canMove(Direction.LEFT.directionVector()),
				canMove(Direction.RIGHT.directionVector())
		};
	}*/
}
