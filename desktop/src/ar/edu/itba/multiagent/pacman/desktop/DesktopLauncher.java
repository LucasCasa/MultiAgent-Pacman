package ar.edu.itba.multiagent.pacman.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ar.edu.itba.multiagent.pacman.MultiagentPacman;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 600;
		config.height = 800;
		config.foregroundFPS = 60;
		new LwjglApplication(new MultiagentPacman(), config);
	}
}
