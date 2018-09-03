package com.behr.observers;

import com.behr.observers.IObserver;
import com.behr.gamecontroller.GameEvents;

public class ScoreListener implements IObserver {

    public enum SCORE_EVENTS {

        SMALLDOT, BIGDOT, GHOST, FRUIT
    }

    private static final int SMALL_DOT_POINTS = 10;
    private static final int BIG_DOT_POINTS = 50;

    private int score = 0;

    @Override
    public void act(GameEvents.GAME_EVENTS eventIn) {
        // parse incoming events for score-related ones
        switch (eventIn) {
            case ATE_SMALL_DOT:
                score += SMALL_DOT_POINTS;
                break;
            case ATE_BIG_DOT:
                score += BIG_DOT_POINTS;
                break;
            case ATE_GHOST:
                score += 200;
                break;
            case ATE_FRUIT:
                break;
            default:
                break;
        }
    }

    public int getScore() {
        return score;
    }
}
