package com.behr.components.playercomponents;

import com.behr.components.IComponent;
import com.behr.gamecontroller.GameEvents;
import com.behr.gameobjects.GameObject;
import java.util.ArrayList;
import java.util.List;
import map.GameMap;
import com.behr.observers.IObservable;
import com.behr.observers.IObserver;
import com.behr.observers.ScoreListener.SCORE_EVENTS;
import com.behr.screens.GameScreen;

public class PlayerLogicComponent implements IComponent, IObservable {
    
    private GameMap map;
    
    private int smallDotsEaten = 0;
    private int bigDotsEaten = 0;

    private List<IObserver> observers;

    public PlayerLogicComponent(GameMap mapIn) {
        map = mapIn;
        this.observers = new ArrayList();
    }

    @Override
    public void update(GameObject objectIn) {             
        handleDots(objectIn);        
    }

    private void handleDots(GameObject objectIn) {
        int dotID;
        float objectX, objectY;
        objectX = objectIn.getPosition().x;
        objectY = objectIn.getPosition().y;

        dotID = map.getDotLayer()
                .tryToEatDot(
                        Math.round(objectX),
                        Math.round(objectY));

        if (dotID == 0) {
            // no dot eaten
        }
        if (dotID == 1) {
            // small dot eaten
            smallDotsEaten++;
            notifyObservers(GameEvents.GAME_EVENTS.ATE_SMALL_DOT);
        }
        if (dotID == 2) {
            // large dot eaten            
            bigDotsEaten++;
            GameScreen.controller.act(GameEvents.GAME_EVENTS.ATE_BIG_DOT);
            notifyObservers(GameEvents.GAME_EVENTS.ATE_BIG_DOT);
        }

        if (smallDotsEaten >= 240 && bigDotsEaten >= 4) {
            smallDotsEaten = bigDotsEaten = 0;
            GameScreen.controller.act(GameEvents.GAME_EVENTS.ALL_DOTS_EATEN);
        }

    } 

    @Override
    public void notifyObservers(GameEvents.GAME_EVENTS e) {
        for (IObserver obs : observers) {
            obs.act(e);
        }
    }

    @Override
    public void addObserver(IObserver o) {
        this.observers.add(o);
    }

    @Override
    public void removeObserver(IObserver o) {
        this.observers.remove(o);
    }
    
    public int getDotsEaten(){
        return smallDotsEaten;
    }

   

}
