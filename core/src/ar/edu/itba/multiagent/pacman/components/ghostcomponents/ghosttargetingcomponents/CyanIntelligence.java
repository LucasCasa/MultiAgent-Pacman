package com.behr.components.ghostcomponents.ghosttargetingcomponents;

import com.badlogic.gdx.math.Vector2;
import com.behr.components.ghostcomponents.TargetingComponent;
import com.behr.gameobjects.GameObject;
import com.behr.gameobjects.Ghost;
import com.behr.animators.Animator;
import com.behr.animators.FrightGhostAnimator;

public class CyanIntelligence extends TargetingComponent {

    private final Vector2 scatterCorner = new Vector2(26, 3);
    private final Vector2 homePosition = new Vector2(11.5f, 18f);
    private Ghost redGhost;

    private int tilesX, tilesY;
    private Vector2 playerTempTarget;
    private float timer = 0;
    private Animator oldAnimator;

    public CyanIntelligence(Ghost redGhostIn) {
        redGhost = redGhostIn;
    }

    @Override
    public void findChaseTarget(Ghost g) {
        findTileTwoInFrontOfPacman(g);
        findFinalTarget(g);
    }

    @Override
    public void findScatterTarget(Ghost g) {
        g.setTarget(scatterCorner);
    }

    private void findTileTwoInFrontOfPacman(Ghost g) {
        tilesX = tilesY = 0;
        Vector2 tempVel = g.player.getVelocity();
        if (tempVel.x == 0 && tempVel.y == 0) {
            playerTempTarget = new Vector2(g.player.getPosition().x + tilesX, g.player.getPosition().y + tilesY);
            return;
        } else {
            if (tempVel.x > 0) {
                tilesX = +2;
                tilesY = 0;
                playerTempTarget = new Vector2(g.player.getPosition().x + tilesX, g.player.getPosition().y);
                return;
            }
            if (tempVel.x < 0) {
                tilesX = -2;
                tilesY = 0;
                playerTempTarget = new Vector2(g.player.getPosition().x + tilesX, g.player.getPosition().y);
                return;
            }
            if (tempVel.y > 0) {
                tilesY = +2;
                tilesX = 0;
                playerTempTarget = new Vector2(g.player.getPosition().x, g.player.getPosition().y + tilesY);
                return;
            }
            if (tempVel.y < 0) {
                tilesY = -2;
                tilesX = 0;
                playerTempTarget = new Vector2(g.player.getPosition().x, g.player.getPosition().y + tilesY);
                return;
            }
        }
    }

    private void findFinalTarget(Ghost g) {
        //get red ghost vector
        Vector2 redPosition = redGhost.getPosition().cpy();
        Vector2 finalPosition = new Vector2();

        if (playerTempTarget.x >= redPosition.x) {
            finalPosition.x = playerTempTarget.x + (playerTempTarget.x - redPosition.x);
        } else {
            finalPosition.x = playerTempTarget.x - (redPosition.x - playerTempTarget.x);
        }
        if (playerTempTarget.y >= redPosition.y) {
            finalPosition.y = playerTempTarget.y + (playerTempTarget.y - redPosition.y);
        } else {
            finalPosition.y = playerTempTarget.y - (redPosition.y - playerTempTarget.y);
        }

        g.setTarget(finalPosition);

    }

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
        if (g.player.logic.getDotsEaten() >= 30 && timer > 120) {
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
        GameObject o = (GameObject) g;
        oldAnimator = o.getAnimator();
        o.setAnimator(new FrightGhostAnimator(g));
    }

    @Override
    public void setNormalAnimation(Ghost g) {
        GameObject o = (GameObject) g;
        o.setAnimator(oldAnimator);
    }

}
