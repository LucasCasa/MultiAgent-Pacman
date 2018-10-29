package ar.edu.itba.multiagent.pacman.states;

import com.typesafe.config.Config;

public class StateUtils {

	public static State StringToState(String s, Config c){
		switch (s){
			case "Random":
				return new RandomWalkState();
			case "Converge":
				return new ConvergeState();
			case "Spread":
				return new SpreadState();
			case "Force":
				return new ConvergeWithForceState(c);
			case "ForcePredict":
				return new ConvergePredictWithForceState(c);
			default:
				return null;
		}
	}
}
