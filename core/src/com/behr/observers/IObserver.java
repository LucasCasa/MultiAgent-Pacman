package com.behr.observers;

import com.behr.gamecontroller.GameEvents;


public interface IObserver {

    public void act(GameEvents.GAME_EVENTS eventIn);
}
