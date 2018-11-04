package ar.edu.itba.multiagent.pacman;

import ar.edu.itba.multiagent.pacman.agents.Ghost;
import ar.edu.itba.multiagent.pacman.environment.GameMap;
import ar.edu.itba.multiagent.pacman.environment.PositionUtils;
import ar.edu.itba.multiagent.pacman.environment.World;
import ar.edu.itba.multiagent.pacman.front.GhostRenderer;
import ar.edu.itba.multiagent.pacman.front.ObjectRenderer;
import ar.edu.itba.multiagent.pacman.front.PlayerRenderer;
import ar.edu.itba.multiagent.pacman.player.AIPlayer;
import ar.edu.itba.multiagent.pacman.player.Player;
import ar.edu.itba.multiagent.pacman.player.PlayerInput;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MultiagentPacman extends ApplicationAdapter {

	private Config config;
	private World w;
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

		gm.update(deltaTime);
		rm.render(deltaTime);
	}
	
	@Override
	public void dispose () {
		rm.dispose();
	}
}
