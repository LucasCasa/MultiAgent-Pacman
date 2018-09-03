package com.behr.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.behr.components.ghostcomponents.ghosttargetingcomponents.CyanIntelligence;
import com.behr.components.ghostcomponents.ghosttargetingcomponents.OrangeIntelligence;
import com.behr.components.ghostcomponents.ghosttargetingcomponents.PinkIntelligence;
import com.behr.components.ghostcomponents.ghosttargetingcomponents.RedIntelligence;
import com.behr.gamecontroller.GameController;
import com.behr.gamecontroller.GameController.GAMESTATE;
import com.behr.gameobjects.Ghost;
import com.behr.gameobjects.Ghost.GhostColor;
import com.behr.gameobjects.Player;
import map.GameMap;
import com.behr.pacdan.PacDanGame;
import com.behr.ui.GameOverlay;

public class GameScreen implements Screen {

    public PacDanGame game;
    public GameMap map;
    public Player player;
    public Ghost[] ghosts = new Ghost[4];
    public static GameController controller;
    public GameOverlay ui;

    public GameScreen(PacDanGame gameIn) {        
        
        this.game = gameIn;

        // Making the map and the player
        map = new GameMap("map/PACMAP.tmx");
        player = new Player(13.5f, 9, map);

        // Setup ghosts
        ghosts[0] = new Ghost(13.5f, 21f, map, GhostColor.RED, player, new RedIntelligence());       
        ghosts[1] = new Ghost(11.5f, 18f, map, GhostColor.CYAN, player, new CyanIntelligence(ghosts[0]));
        ghosts[2] = new Ghost(15.5f, 18f, map, GhostColor.ORANGE, player, new OrangeIntelligence());
        ghosts[3] = new Ghost(13.5f, 18f, map, GhostColor.PINK, player, new PinkIntelligence());

        // camera setup from map info
        game.camera.setToOrtho(false, map.getWidth(), map.getHeight());
        game.camera.update();

        // shape renderer and spritebatch setup from cam setup
        game.sr.setProjectionMatrix(game.camera.combined);
        game.sr.scale(1 / 16f, 1 / 16f, 0);
        game.sb.setProjectionMatrix(game.sr.getProjectionMatrix());

        // setup UI
        ui = new GameOverlay(player, game.camera);

        controller = new GameController(this);

        // START GAME CONTROLLER
        controller.entry(GAMESTATE.RESUME); 
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        controller.update(game.camera, game.sr);
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
    
   
      

}
