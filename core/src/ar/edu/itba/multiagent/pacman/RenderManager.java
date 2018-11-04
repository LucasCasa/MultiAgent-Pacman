package ar.edu.itba.multiagent.pacman;

import ar.edu.itba.multiagent.pacman.agents.Ghost;
import ar.edu.itba.multiagent.pacman.front.GhostRenderer;
import ar.edu.itba.multiagent.pacman.front.ObjectRenderer;
import ar.edu.itba.multiagent.pacman.front.PlayerRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RenderManager {

    private final SpriteBatch batch;
    private GameManager gm;
    private TiledMapRenderer m;
    private OrthographicCamera camera;
    private List<ObjectRenderer> renderers = new ArrayList<>();

    public RenderManager(GameManager gm){
        Config config = ConfigFactory.parseFile(new File("application.conf")).resolve();
        int width = 448;
        int height = 496 + 48 - 16;
        this.gm = gm;
        batch = new SpriteBatch();
        m = new OrthogonalTiledMapRenderer(gm.getMap());
        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);
        camera.update();
        m.setView(camera);
        Texture pt = new Texture("sprites/Pacman_Anim.png");
        renderers.add(new PlayerRenderer(gm.getPlayer(), pt));
        loadGhosts(config);
    }

    private void loadGhosts(Config config){
        List<Ghost> ghosts = gm.getAgents();
        List<String> names = config.getStringList("ghost-names");
        for (int i = 0; i < names.size(); i++) {
            GhostRenderer ghostRenderer = new GhostRenderer(ghosts.get(i), names.get(i),
                    config.getBoolean("show-desired-direction"), config.getBoolean("show-visibility-range"), config.getBoolean("show-target"));
            renderers.add(ghostRenderer);
        }
    }

    public void render(float deltaTime) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        m.render();
        batch.begin();
        m.setView(camera);
        //batch.draw(img, 0, 0);
        renderers.forEach(rend -> rend.render(batch, deltaTime));
        batch.end();
    }

    public void dispose() {
        batch.dispose();
        renderers.forEach(ObjectRenderer::dispose);
    }
}
