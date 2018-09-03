package ar.edu.itba.multiagent.pacman.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ar.edu.itba.multiagent.pacman.MultiagentPacman;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 448;
		config.height = 496 + 48 - 16;
		new LwjglApplication(new MultiagentPacman(), config);
	}
}
