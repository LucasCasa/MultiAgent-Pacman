package ar.edu.itba.multiagent.pacman.states;

import ar.edu.itba.multiagent.pacman.GameObject;

import java.util.Random;

public interface State {
	void update(GameObject self, float deltaTime, int turn, Random r);
}
