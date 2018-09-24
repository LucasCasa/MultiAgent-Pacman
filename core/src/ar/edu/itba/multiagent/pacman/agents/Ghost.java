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

	public Ghost(int id, GameMap gm, Config c, World w) {
		super(gm, c.getInt("speed"));
		this.id = id;
		this.visibility = c.getInt("visibility");
		this.visiblityDirections = c.getBooleanList("visibility-directions");
		this.w = w;
		this.random = new Random(c.getInt("seed"));
		searchState = StateUtils.StringToState(c.getString("search-strategy"));
		pursuitState = StateUtils.StringToState(c.getString("pursuit-strategy"));
		messages = new LinkedList<>();
		otherGhosts = new HashMap<>();
	}

	public void update(float deltaTime, int turn){
		EnemySighting p = w.sense(this);
		broadcastPosition();
		if(p != null) {
			w.writeBlackBoard(p);
		} else {
			p = w.pollBlackBoard();
			if(p != null && (turn - p.getTurn()) > 5 / deltaTime)
				p = null;
		}
		while(!messages.isEmpty()){
			System.out.println("Polling Messages");
			Message m = messages.poll();
			readMessage(m);
		}
		if(p != null){
			pursuitState.update(this, deltaTime, turn, random);
			if(p.getPosition().dst2(getPosition()) < 10){
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
				updateGhostPosition(m.getDataAsPosition());
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
		System.out.println("Putting Messages");
		messages.add(message);
	}

	//UP, DOWN, LEFT, RIGHT
	public boolean[] getValidDirections() {
		return new boolean[]{
				canMove(Direction.UP.directionVector()) && getDirection() != Direction.DOWN,
				canMove(Direction.DOWN.directionVector()) && getDirection() != Direction.UP,
				canMove(Direction.LEFT.directionVector()) && getDirection() != Direction.RIGHT,
				canMove(Direction.RIGHT.directionVector()) && getDirection() != Direction.LEFT
		};
	}
}
