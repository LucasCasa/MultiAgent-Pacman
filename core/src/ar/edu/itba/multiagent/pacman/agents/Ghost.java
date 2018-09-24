package ar.edu.itba.multiagent.pacman.agents;

import ar.edu.itba.multiagent.pacman.environment.GameMap;
import ar.edu.itba.multiagent.pacman.GameObject;
import ar.edu.itba.multiagent.pacman.environment.EnemySighting;
import ar.edu.itba.multiagent.pacman.environment.World;
import ar.edu.itba.multiagent.pacman.states.State;
import ar.edu.itba.multiagent.pacman.states.StateUtils;
import com.typesafe.config.Config;

import java.util.List;
import java.util.Random;

public class Ghost extends GameObject implements SensingAgent {
	private int visibility;
	private List<Boolean> visiblityDirections;
	private State searchState;
	private State pursuitState;

	private World w;
	private Random random;
	public Ghost(GameMap gm, Config c, World w) {
		super(gm, c.getInt("speed"));
		this.visibility = c.getInt("visibility");
		this.visiblityDirections = c.getBooleanList("visibility-directions");
		this.w = w;
		this.random = new Random(c.getInt("seed"));
		searchState = StateUtils.StringToState(c.getString("search-strategy"));
		pursuitState = StateUtils.StringToState(c.getString("pursuit-strategy"));
	}

	public void update(float deltaTime, int turn){
		EnemySighting p = w.sense(this);
		if(p != null) {
			w.writeBlackBoard(p);
		} else {
			p = w.pollBlackBoard();
			if(p != null && (turn - p.getTurn()) > 5 / deltaTime)
				p = null;
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

	public int getVisibility() {
		return visibility;
	}
	public World getWorld() {
		return w;
	}
	public List<Boolean> getVisibilityDirections() {
		return visiblityDirections;
	}
}
