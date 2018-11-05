package ar.edu.itba.multiagent.pacman;

import ar.edu.itba.multiagent.pacman.agents.Ghost;
import ar.edu.itba.multiagent.pacman.environment.GameMap;
import ar.edu.itba.multiagent.pacman.environment.PositionUtils;
import ar.edu.itba.multiagent.pacman.environment.World;
import ar.edu.itba.multiagent.pacman.front.GhostRenderer;
import ar.edu.itba.multiagent.pacman.player.AIPlayer;
import ar.edu.itba.multiagent.pacman.player.Player;
import ar.edu.itba.multiagent.pacman.player.PlayerInput;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameManager {

	private Player p;
	private List<Ghost> agents = new ArrayList<>();
	private World w;
	private Random shuffleRandom;
	private TiledMap map;
	private int turn = 0;
	private boolean gameOver = false;

	private StatisticManager stats;

	public GameManager(){
		Config config = ConfigFactory.parseFile(new File("application.conf")).resolve();
		map = new TmxMapLoader().load("map/"+ config.getString("map-name") +".tmx");
		GameMap gm = new GameMap(map);
		w = new World(gm, agents);
		if(config.getBoolean("pacman-agent")){
			p = new AIPlayer(gm, w, config.getBoolean("lock-to-grid"));
		} else {
			p = new Player(gm, config.getBoolean("lock-to-grid"));
		}
		w.setPlayer(p);
		shuffleRandom = new Random(1);
		loadGhost(gm, w, config);

		stats = new StatisticManager(this, config);
	}

	private void loadGhost(GameMap gm, World w, Config config) {
		List<String> names = config.getStringList("ghost-names");
		int id = 0;
		for(String name : names){
			Ghost ghost = new Ghost(id++, gm, config.getConfig(name), w, config.getBoolean("lock-to-grid"), config);
			ghost.setPosition(new Vector2(16 * 14 ,16 * 21));
			agents.add(ghost);
		}
	}

	public void update(float deltaTime){
        turn++;
        w.update(turn);
        if(!gameOver) {
            p.update(deltaTime);

            Collections.shuffle(agents, shuffleRandom);
            agents.forEach(agent -> agent.update(deltaTime, turn));
            agents.forEach(agent -> {
                if (PositionUtils.worldToBoard(agent.getPosition()).equals(PositionUtils.worldToBoard(p.getPosition()))) {
                    gameOver = true;
                    stats.show();
                }
            });
            stats.update(deltaTime);
        }
    }

    public List<Ghost> getAgents() {
        return agents;
    }

    public TiledMap getMap() {
        return map;
    }

    public Player getPlayer() {
        return p;
    }

    public World getWorld() { return w; }
}
