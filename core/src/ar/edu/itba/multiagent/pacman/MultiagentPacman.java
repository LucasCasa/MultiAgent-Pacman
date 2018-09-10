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
	Ghost g1;
	Ghost g2;
	Ghost g3;
	Ghost g4;
	GhostRenderer gr1;
	GhostRenderer gr2;
	GhostRenderer gr3;
	GhostRenderer gr4;
	TiledMap map;
	TiledMapRenderer m;
	OrthographicCamera camera;
	@Override
	public void create () {
		batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");
		map = new TmxMapLoader().load("map/PACMAP.tmx");
		GameMap gm = new GameMap(map);
		g1 = new Ghost(gm);
		g1.setPosition(new Vector2(50,50));
		g2 = new Ghost(gm);
		g1.setPosition(new Vector2(220,222));
		g3 = new Ghost(gm);
		g1.setPosition(new Vector2(50,220));
		g4 = new Ghost(gm);
		g1.setPosition(new Vector2(220,50));

		m = new OrthogonalTiledMapRenderer(map);
		p = new Player(gm);
		p.setPosition(new Vector2(100, 100));
		Texture pt = new Texture("sprites/Pacman_Anim.png");
		Texture t1 = new Texture("sprites/CyanGhost_Anim.png");
		Texture t2 = new Texture("sprites/OrangeGhost_Anim.png");
		Texture t3 = new Texture("sprites/PinkGhost_Anim.png");
		Texture t4 = new Texture("sprites/RedGhost_Anim.png");
		gr1 = new GhostRenderer(g1, t1);
		gr2 = new GhostRenderer(g2, t2);
		gr3 = new GhostRenderer(g3, t3);
		gr4 = new GhostRenderer(g4, t4);
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
		g1.update(deltaTime);
		g2.update(deltaTime);
		g3.update(deltaTime);
		g4.update(deltaTime);

		camera.update();
		batch.setProjectionMatrix(camera.combined);
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		m.render();
		batch.begin();
		m.setView(camera);
		//batch.draw(img, 0, 0);
		gr1.render(batch, deltaTime);
		gr2.render(batch, deltaTime);
		gr3.render(batch, deltaTime);
		gr4.render(batch, deltaTime);
		pr.render(batch, deltaTime);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
