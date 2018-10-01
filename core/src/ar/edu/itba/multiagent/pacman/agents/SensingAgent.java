package ar.edu.itba.multiagent.pacman.agents;

import ar.edu.itba.multiagent.pacman.Direction;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public interface SensingAgent {

	public List<Boolean> getVisibilityDirections();
	public int getVisibility();
	public Vector2 getPosition();
	public Direction getDirection();
}
