package ar.edu.itba.multiagent.pacman.front;

import ar.edu.itba.multiagent.pacman.player.Player;
import com.badlogic.gdx.graphics.Texture;

public class PlayerRenderer extends ObjectRenderer{

	public PlayerRenderer(Player player, Texture sprite){
		super(player, sprite);
		setTotalSteps(3);
		setTimePerFrame(0.1f);
	}
}