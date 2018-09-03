package ar.edu.itba.multiagent.pacman;

import ar.edu.itba.multiagent.pacman.agents.Ghost;
import ar.edu.itba.multiagent.pacman.front.GhostRenderer;
import ar.edu.itba.multiagent.pacman.front.PlayerRenderer;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class MultiagentPacman extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	GhostRenderer gr;
	PlayerRenderer pr;
	Player p;
	TiledMap map;
	TiledMapRenderer m;
	OrthographicCamera camera;
	@Override
	public void create () {
		batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");
		Ghost g = new Ghost();
		map = new TmxMapLoader().load("map/PACMAP.tmx");
		GameMap gm = new GameMap(map);
		m = new OrthogonalTiledMapRenderer(map);
		p = new Player(gm);
		p.setPosition(new Vector2(100, 100));
		Texture pt = new Texture("sprites/Pacman_Anim.png");
		Texture t = new Texture("sprites/CyanGhost_Anim.png");
		gr = new GhostRenderer(g, t);
		pr = new PlayerRenderer(p, pt);
		Gdx.input.setInputProcessor(new PlayerInput(p));
		camera = new OrthographicCamera();
		camera.setToOrtho(false,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();
		//camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

		m.setView(camera);
	}

	@Override
	public void render () {
		float deltaTime = Gdx.graphics.getDeltaTime();
		p.update(deltaTime);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		m.render();
		batch.begin();
		m.setView(camera);
		//batch.draw(img, 0, 0);
		gr.render(batch, deltaTime);
		pr.render(batch, deltaTime);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
