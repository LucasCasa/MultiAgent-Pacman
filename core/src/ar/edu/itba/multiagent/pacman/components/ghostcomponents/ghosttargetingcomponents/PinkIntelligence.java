package com.behr.components.ghostcomponents.ghosttargetingcomponents;

import com.badlogic.gdx.math.Vector2;
import com.behr.components.ghostcomponents.TargetingComponent;
import com.behr.gameobjects.GameObject;
import com.behr.gameobjects.Ghost;
import com.behr.animators.Animator;
import com.behr.animators.FrightGhostAnimator;

public class PinkIntelligence extends TargetingComponent {

    private int tilesX, tilesY;

    private final Vector2 scatterCorner = new Vector2(1, 31);
    private final Vector2 homePosition = new Vector2(13.5f, 18f);
    private float timer = 0;
    private Animator oldAnimator;

    @Override
    public void findChaseTarget(Ghost g) {
        tilesX = tilesY = 0;
        Vector2 tempVel = g.player.getVelocity();
        if (tempVel.x == 0 && tempVel.y == 0) {
            g.setTarget(new Vector2(g.player.getPosition().x + tilesX, g.player.getPosition().y + tilesY));
            return;
        } else {
            if (tempVel.x > 0) {
                tilesX = +4;
                tilesY = 0;
                g.setTarget(new Vector2(g.player.getPosition().x + tilesX, g.player.getPosition().y));
                return;
            }
            if (tempVel.x < 0) {
                tilesX = -4;
                tilesY = 0;
                g.setTarget(new Vector2(g.player.getPosition().x + tilesX, g.player.getPosition().y));
                return;
            }
            if (tempVel.y > 0) {
                tilesY = +4;
                tilesX = 0;
                g.setTarget(new Vector2(g.player.getPosition().x, g.player.getPosition().y + tilesY));
                return;
            }
            if (tempVel.y < 0) {
                tilesY = -4;
                tilesX = 0;
                g.setTarget(new Vector2(g.player.getPosition().x, g.player.getPosition().y + tilesY));
                return;
            }
        }
    }

    @Override
    public void findScatterTarget(Ghost g) {
        g.setTarget(scatterCorner);
    }

    @Override
    public void goHome(Ghost g) {
        g.setPositionX(homePosition.x);
        g.setPositionY(homePosition.y);
        g.setRectX(homePosition.x * 16);
        g.setRectY(homePosition.y * 16);
    }

    @Override
    public Vector2 getHome() {
        return homePosition;
    }

    @Override
    public boolean canLeaveHome(Ghost g) {
        timer++;
        if (g.player.logic.getDotsEaten() >= 5 && timer > 60) {
            timer = 0;
            return true;
        }
        return false;
    }

    @Override
    public void increaseTimers(float timeIn) {
        timer -= timeIn * 160;
    }
        @Override
    public void setFrightAnimation(Ghost g) {
        GameObject o = (GameObject)g;
        oldAnimator = o.getAnimator();
        o.setAnimator(new FrightGhostAnimator(g));
    }

    @Override
    public void setNormalAnimation(Ghost g) {
        GameObject o = (GameObject)g;
        o.setAnimator(oldAnimator);
    }

}
