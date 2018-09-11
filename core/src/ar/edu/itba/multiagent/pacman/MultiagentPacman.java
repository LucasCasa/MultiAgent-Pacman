package ar.edu.itba.multiagent.pacman;

import ar.edu.itba.multiagent.pacman.agents.Ghost;
import ar.edu.itba.multiagent.pacman.environment.GameMap;
import ar.edu.itba.multiagent.pacman.environment.PositionUtils;
import ar.edu.itba.multiagent.pacman.environment.World;
import ar.edu.itba.multiagent.pacman.front.GhostRenderer;
import ar.edu.itba.multiagent.pacman.front.ObjectRenderer;
import ar.edu.itba.multiagent.pacman.front.PlayerRenderer;
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

public class MultiagentPacman extends ApplicationAdapter {
	private SpriteBatch batch;
	private Player p;
	private List<Ghost> agents = new ArrayList<>();
	private List<ObjectRenderer> renderers = new ArrayList<>();
	private TiledMap map;
	private TiledMapRenderer m;
	private OrthographicCamera camera;
	private Config config;
	private World w;
	private boolean gameOver = false;

	@Override
	public void create () {
		config = ConfigFactory.parseFile(new File("application.conf"));
		batch = new SpriteBatch();
		map = new TmxMapLoader().load("map/PACMAP.tmx");
		GameMap gm = new GameMap(map);
		p = new Player(gm);
		w = new World(gm, p);
		loadGhost(gm, w);
		m = new OrthogonalTiledMapRenderer(map);
		p.setPosition(new Vector2(100, 100));
		Texture pt = new Texture("sprites/Pacman_Anim.png");
		renderers.add(new PlayerRenderer(p, pt));
		Gdx.input.setInputProcessor(new PlayerInput(p));
		camera = new OrthographicCamera();
		camera.setToOrtho(false,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();
		m.setView(camera);
	}

	private void loadGhost(GameMap gm, World w) {
		List<String> names = config.getStringList("ghost-names");
		for(String name : names){
			Ghost ghost = new Ghost(gm, config.getConfig(name), w);
			ghost.setPosition(new Vector2(16 * 14 ,16 * 20));
			GhostRenderer ghostRenderer = new GhostRenderer(ghost, new Texture("sprites/" + name + "Ghost_Anim.png"));
			agents.add(ghost);
			renderers.add(ghostRenderer);
		}
	}

	@Override
	public void render () {
		float deltaTime = Gdx.graphics.getDeltaTime();
		if(!gameOver) {
			p.update(deltaTime);

			Collections.shuffle(agents);
			agents.forEach(agent -> agent.update(deltaTime));
			agents.forEach(agent -> {
				if (PositionUtils.worldToBoard(agent.getPosition()).equals(PositionUtils.worldToBoard(p.getPosition()))) {
					gameOver = true;
				}
			});
		}
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		m.render();
		batch.begin();
		m.setView(camera);
		//batch.draw(img, 0, 0);
		renderers.forEach(rend -> rend.render(batch, deltaTime));
		batch.end();
		w.writeBlackBoard(null);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		renderers.forEach(ObjectRenderer::dispose);
	}
}
