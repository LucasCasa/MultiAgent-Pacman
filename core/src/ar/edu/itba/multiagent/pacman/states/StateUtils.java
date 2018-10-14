package ar.edu.itba.multiagent.pacman.states;

public class StateUtils {

	public static State StringToState(String s){
		switch (s){
			case "Random":
				return new RandomWalkState();
			case "Converge":
				return new ConvergeState();
			case "Spread":
				return new SpreadState();
			case "Force":
				return new ConvergeWithForceState();
			default:
				return null;
		}
	}
}
