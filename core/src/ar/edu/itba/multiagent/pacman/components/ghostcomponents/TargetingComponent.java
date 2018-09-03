package com.behr.components.ghostcomponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.behr.components.ghostcomponents.GhostController.GHOST_STATES;
import com.behr.gameobjects.Ghost;
import java.util.Random;

abstract public class TargetingComponent {
    private float gdxTimeCounter = 0;
    

    public void update(Ghost g, GHOST_STATES stateIn) {        
        switch (stateIn) {
            case INACTIVE:                
                break;
            case SCATTER:
                findScatterTarget(g);                                
                break;
            case CHASE:
                findChaseTarget(g);                                
                break;
            case FRIGHTENED:
                findRandomTarget(g);                
                break;
            case BOXED:                
                break;
        }
    }
    private void findRandomTarget(Ghost g) {

        gdxTimeCounter += Gdx.graphics.getDeltaTime();
        if (gdxTimeCounter > 0.5f) {
            gdxTimeCounter = 0;
            Random rand = new Random();
            int randomX, randomY;
            randomX = rand.nextInt(20);
            randomY = rand.nextInt(30);
            g.setTarget(new Vector2(randomX, randomY));            
        }

    }
    
    public abstract void findChaseTarget(Ghost g);

    public abstract void findScatterTarget(Ghost g);
    
    public abstract void goHome(Ghost g);
    
    public abstract Vector2 getHome();
    
    public abstract boolean canLeaveHome(Ghost g);
    
    public abstract void increaseTimers(float timeIn);
    
    public abstract void setFrightAnimation(Ghost g);
    
    public abstract void setNormalAnimation(Ghost g);
    
  
}
