package ar.edu.itba.multiagent.pacman.front;

import ar.edu.itba.multiagent.pacman.agents.Ghost;
import com.badlogic.gdx.graphics.Texture;

public class GhostRenderer extends ObjectRenderer {

	public GhostRenderer(Ghost ghost, Texture sprite, boolean showDirection){
		super(ghost, sprite, showDirection);
		super.setTotalSteps(2);
	}
}
