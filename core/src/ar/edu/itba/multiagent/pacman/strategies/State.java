package ar.edu.itba.multiagent.pacman.strategies;

import ar.edu.itba.multiagent.pacman.agents.Ghost;

import java.util.Random;

public interface State {
	void update(Ghost self, float deltaTime, int turn,  Random r);
}
