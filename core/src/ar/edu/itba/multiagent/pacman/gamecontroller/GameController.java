package com.behr.gamecontroller;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Timer;
import com.behr.components.ghostcomponents.GhostController;
import com.behr.components.ghostcomponents.GhostController.GHOST_STATES;
import com.behr.gamecontroller.GameEvents.GAME_EVENTS;
import com.behr.gameobjects.Ghost;
import java.util.Collections;

import com.behr.observers.IObserver;
import com.behr.pacdan.PacDanGame;
import com.behr.screens.GameScreen;
import com.behr.screens.ScoreScreen;

public class GameController implements IObserver {

    public enum GAMESTATE {

        RUN, PAUSE, RESUME, RESTART, RESET
    }

    private GAMESTATE currentState;
    private GameScreen screen;
    private Timer timer;
    int i = 0;

    public GameController(GameScreen screenIn) {

        screen = screenIn;
        timer = new Timer();

        currentState = GAMESTATE.RESUME;
    }

    public void update(OrthographicCamera cam, ShapeRenderer sr) {

//        i++;
//        if (i >= 10) {
//            i = 0;
//            System.out.println("GameState: " + currentState.toString());
//        }
        screen.ui.drawScore(PacDanGame.sb);
        switch (currentState) {
            ///////////////////////////////////////////////////////////////////
            case RUN: // draw and update everything
                screen.map.render(cam);
                //screen.map.renderGrid(cam, sr);
                screen.player.update();
                screen.player.render();
                for (Ghost currentGhost : screen.ghosts) {
                    currentGhost.update();
                }
                break;
            ///////////////////////////////////////////////////////////////////    
            case PAUSE: // just draw things
                screen.map.render(cam);
                //screen.map.renderGrid(cam, sr);
                screen.player.render();
                for (Ghost currentGhost : screen.ghosts) {
                    currentGhost.update();
                }

                break;
            ///////////////////////////////////////////////////////////////////    
            case RESUME: // just draw
                screen.map.render(cam);
                //screen.map.renderGrid(cam, sr);
                screen.player.render();
                for (Ghost currentGhost : screen.ghosts) {
                    currentGhost.update();
                }

                break;
            ///////////////////////////////////////////////////////////////////    
            case RESTART: // just draw
                screen.map.render(cam);
                //screen.map.renderGrid(cam, sr);
                screen.player.render();

                break;
            ///////////////////////////////////////////////////////////////////    
            case RESET: // just draw
                screen.map.render(cam);
                //screen.map.renderGrid(cam, sr);
                screen.player.render();

                break;
            ///////////////////////////////////////////////////////////////////
        }
    }

    private void exit(GAMESTATE stateIn) {
        switch (stateIn) {
            case RUN:

                break;
            case PAUSE:

                break;
            case RESUME:
                timer.clear();

                break;
            case RESTART:

                break;
            case RESET:

                break;
        }

    }

    public void entry(GAMESTATE stateIn) {
        switch (stateIn) {
            case RUN:
                timer.clear();
                for (Ghost currentGhost : screen.ghosts) {
                    currentGhost.getController().changeState(GHOST_STATES.BOXED);
                }

                break;
            case PAUSE:

                break;
            case RESUME:
                timer.clear();
                

                timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        changeState(GAMESTATE.RUN);
                    }
                }, 2f);

                break;
            case RESTART:
                //timer.clear();
                //changeState(GAMESTATE.RESUME);

                break;
            case RESET:

                break;
        }
    }

    public void changeState(GAMESTATE stateIn) {
        exit(stateIn);
        entry(stateIn);
        currentState = stateIn;
    }
  

    @Override
    public void act(GameEvents.GAME_EVENTS eventIn) {
        switch (eventIn) {
            case ATE_BIG_DOT:
                // make all ghosts scared
                for (Ghost thisGhost : screen.ghosts) {
                    if (thisGhost.getController()
                            .getCurrentState() != GHOST_STATES.INACTIVE
                            && thisGhost.getController()
                            .getCurrentState() != GHOST_STATES.BOXED) {
                        thisGhost.getController()
                                .changeState(GHOST_STATES.FRIGHTENED);
                        
                    }else{
                        thisGhost.targeter.increaseTimers(thisGhost.getController().getFrightTime());                        
                        thisGhost.getController().frightened = true;
                    }
                }
                break;
            case GHOST_CAUGHT_PLAYER:
                // remove a life, reset game
                if (screen.player.getLives() == 0) {
                    act(GAME_EVENTS.PLAYER_ZERO_LIVES);
                } else {
                    screen.player.loseLife();
                    screen.player.reset();
                    // LOAD NEW MAP
                    screen.map.resetMapDots();
                    changeState(GAMESTATE.RESUME);
                    for (Ghost currentGhost : screen.ghosts) {
                        currentGhost.getController().changeState(GhostController.GHOST_STATES.INACTIVE);
                        currentGhost.targeter.goHome(currentGhost);
                    }
                }

                break;
           
            case PLAYER_ZERO_LIVES:
                // save highscores
                PacDanGame.highScores.add(screen.player.getScore());
                Collections.sort(PacDanGame.highScores);
                Collections.reverse(PacDanGame.highScores);

                // return to game menu
                screen.game.setScreen(new ScoreScreen(screen.game));  
                
                break;

            case ALL_DOTS_EATEN:
                // reset the map, increase difficulty, reset game
                screen.player.reset();
                // LOAD NEW MAP
                screen.map.reloadMap();
                screen.map.resetMapDots();
                
                changeState(GAMESTATE.RESUME);
                for (Ghost currentGhost : screen.ghosts) {
                    currentGhost.getController().changeState(GhostController.GHOST_STATES.INACTIVE);
                    currentGhost.targeter.goHome(currentGhost);
                }
                break;
            default:
                break;

        }
    }

}
