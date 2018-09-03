package com.behr.components.ghostcomponents;

import com.badlogic.gdx.utils.Timer;
import com.behr.components.IComponent;
import com.behr.gamecontroller.GameEvents;
import com.behr.gamecontroller.GameEvents.GAME_EVENTS;
import com.behr.gameobjects.GameObject;
import com.behr.gameobjects.Ghost;
import com.behr.gameobjects.Player;
import com.behr.observers.IObserver;
import com.behr.screens.GameScreen;

public class GhostController implements IComponent, IObserver {

   

    public enum GHOST_STATES {

        INACTIVE, SCATTER, CHASE, FRIGHTENED, BOXED
    }

    private final static int MAX_LEVELS = 16;
    private int currentFrightTime = 0;
    private int[] frightTime = {
        6, 5, 4, 3,
        2, 5, 2, 2,
        1, 5, 2, 1,
        1, 3, 1, 1
    };
    private int currentScatterTime = 0;
    private int[] scatterTime = {
        5, 6, 5, 4,
        5, 6, 5, 3,
        4, 4, 4, 4,
        7, 3, 2, 1
    };
    private int currentChaseTime = 0;
    private int[] chaseTime = {
        5, 10, 12, 15,
        17, 19, 20, 25,
        30, 1037, 1037, 1037,
        1037, 1037, 1037, 1037
    };

    private GHOST_STATES currentState;
    private Player player;
    private Timer timer;
    private Ghost g;
    public boolean frightened = false;

    // debug, delete later
    //int i = 0;
    public GhostController(Player playerIn, Ghost gIn) {
        this.player = playerIn;
        this.g = (Ghost)gIn;
        timer = new Timer();
        currentState = GHOST_STATES.INACTIVE;

    }

    @Override
    public void update(GameObject o) {
        // debug, delete later
//        i++;
//        if (i >= 50) {
//            i = 0;
//            System.out.println("Ghost STATE: " + currentState.toString());
//        }

        g = (Ghost) o;

        // GHOST LOOP /////////////////////////////////////////////////////////
        g.renderer.update(o);
        //g.renderer.renderTarget(g);

        switch (currentState) {
            case INACTIVE:
                
                break;
            case SCATTER:
                g.targeter.update(g, currentState);
                g.pathfinder.update(g);
                g.physics.update(o);
                checkIntersects();
                break;
            case CHASE:
                g.targeter.update(g, currentState);
                g.pathfinder.update(g);
                g.physics.update(o);
                checkIntersects();
                break;
            case FRIGHTENED:
                g.targeter.update(g, currentState);
                g.pathfinder.update(g);
                g.physics.update(o);
                checkIntersects();
                break;
            case BOXED:
                if (g.targeter.canLeaveHome(g) && frightened == false) {                    
                    changeState(GHOST_STATES.CHASE);
                }

                break;
        }
    }

    private void entry(GHOST_STATES stateIn) {
        switch (stateIn) {
            ///////////////////////////////////////////////////////////////////
            case INACTIVE:
                timer.clear();
                
                currentScatterTime = currentChaseTime = currentFrightTime = 0;                
                
                break;
            ///////////////////////////////////////////////////////////////////
            case SCATTER:
                timer.clear();
                g.physics.reverse(g);
                timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        changeState(GHOST_STATES.CHASE);
                    }
                }, (float) scatterTime[currentScatterTime]);
                if (currentScatterTime < MAX_LEVELS - 1) {
                    currentScatterTime++;
                }
                
                break;
            ///////////////////////////////////////////////////////////////////
            case CHASE:
                timer.clear();
                g.physics.reverse(g);
                timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        changeState(GHOST_STATES.SCATTER);
                    }
                }, (float) chaseTime[currentChaseTime]);
                if (currentChaseTime < MAX_LEVELS - 1) {
                    currentChaseTime++;
                }
                break;
            ///////////////////////////////////////////////////////////////////
            case FRIGHTENED:
                frightened = true;
                //g.setColor(Color.BLUE);
                timer.clear();
                g.physics.reverse(g);
                g.targeter.setFrightAnimation(g);
                timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        changeState(GHOST_STATES.CHASE);
                        frightened = false;
                    }
                }, (float) frightTime[currentFrightTime]);
                if (currentFrightTime < MAX_LEVELS - 1) {
                    currentFrightTime++;
                }
                break;
            ///////////////////////////////////////////////////////////////////
            case BOXED:   
                currentScatterTime = currentChaseTime = currentFrightTime = 0;
                g.setTarget(g.targeter.getHome()); 
                frightened = false;

                break;
            ///////////////////////////////////////////////////////////////////
        }
    }

    private void exit(GHOST_STATES stateIn) {
        switch (stateIn) {
            case INACTIVE:
                break;
            case SCATTER:
                break;
            case CHASE:
                break;
            case FRIGHTENED:
                g.targeter.setNormalAnimation(g);
                
                
                //g.setColor(g.getColor());                
                break;
            case BOXED:
                g.setInPlay();
                break;
        }
    }

    public void changeState(GHOST_STATES stateIn) {
        exit(currentState);
        entry(stateIn);
        currentState = stateIn;
    }
    public GHOST_STATES getCurrentState(){
        return currentState;
    }
 
 
    private void checkIntersects() {
        if (g.getEntityRect().overlaps(player.getEntityRect())) {
            switch (currentState) {
                case FRIGHTENED:
                    // reset ghost to home
                    g.targeter.goHome(g);
                    changeState(GHOST_STATES.BOXED);
                    player.getScoreKeeper().act(GameEvents.GAME_EVENTS.ATE_GHOST);
                    break;
                default:
                    GameScreen.controller.act(GameEvents.GAME_EVENTS.GHOST_CAUGHT_PLAYER);

            }
        }
    }
     @Override
    public void act(GameEvents.GAME_EVENTS eventIn) {
        if(eventIn == GAME_EVENTS.ATE_BIG_DOT){
            currentState = GHOST_STATES.FRIGHTENED;
        }
    }

    public float getFrightTime() {
        return frightTime[currentFrightTime];
    }
}
