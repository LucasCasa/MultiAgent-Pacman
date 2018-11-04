package ar.edu.itba.multiagent.pacman;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;

public class MultiagentPacman extends ApplicationAdapter {

	private Config config;
	public boolean pause = false;
	private GameManager gm;
	private RenderManager rm;

	@Override
	public void create () {
		config = ConfigFactory.parseFile(new File("application.conf")).resolve();
		gm = new GameManager();
		rm = new RenderManager(gm);
	}

	@Override
	public void render () {
		float deltaTime;
		if(config.getBoolean("pacman-agent")) {
			deltaTime = 1 / 60f;
		} else {
			deltaTime = Gdx.graphics.getDeltaTime();
		}
        if(!pause) {
            gm.update(deltaTime);
            rm.render(deltaTime);
        } else {
            rm.render(0);
        }

	}
	
	@Override
	public void dispose () {
		rm.dispose();
	}
}
