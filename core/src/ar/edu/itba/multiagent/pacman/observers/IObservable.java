package com.behr.observers;

import com.behr.gamecontroller.GameEvents;


public interface IObservable {

    public void notifyObservers(GameEvents.GAME_EVENTS eventIn);

    public void addObserver(IObserver o);

    public void removeObserver(IObserver o);

}
