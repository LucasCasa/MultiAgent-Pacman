package ar.edu.itba.multiagent.pacman.strategies;

public class StateUtils {

	public static State StringToState(String s){
		switch (s){
			case "Random":
				return new RandomWalkState();
			case "Converge":
				return new ConvergeState();
			default:
				return null;
		}
	}
}
