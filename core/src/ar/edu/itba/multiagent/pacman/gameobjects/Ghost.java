package com.behr.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.behr.components.ghostcomponents.GhostController;
import com.behr.components.ghostcomponents.TargetingComponent;
import com.behr.components.ghostcomponents.PathfindingComponent;

import com.behr.components.sharedcomponents.PhysicsComponent;
import com.behr.components.sharedcomponents.RenderingComponent;
import map.GameMap;
import com.behr.animators.CyanGhostAnimator;
import com.behr.animators.OrangeGhostAnimator;
import com.behr.animators.PinkGhostAnimator;
import com.behr.animators.RedGhostAnimator;

public class Ghost extends GameObject{    
     public static enum GhostColor{
        RED, ORANGE, CYAN, PINK
    }    
    
    public Player player;
    private Vector2 target = new Vector2();
    public int MOVESPEED = 2;
    public Vector2 startTile = new Vector2(13.5f, 21f);
    
    public PhysicsComponent physics;
    public RenderingComponent renderer;
    public TargetingComponent targeter;
    public PathfindingComponent pathfinder;
   
    private GhostController ghostController;
    

    public Ghost(float xIn, float yIn, GameMap mapIn, GhostColor colorIn, Player playerIn, TargetingComponent AIin) {
        super(xIn, yIn, mapIn);
        
        player = playerIn;
        physics = new PhysicsComponent(mapIn);
        renderer = new RenderingComponent();
        pathfinder = new PathfindingComponent();
        ghostController = new GhostController(playerIn, this);
        targeter = AIin;
        setSuperColor(colorIn);
        
    }

    @Override
    public void update() {
        ghostController.update(this);
    }

    private void setSuperColor(GhostColor colorIn) {
        switch (colorIn) {
            case RED:
                super.setColor(Color.RED);
                super.setAnimator(new RedGhostAnimator(this));
                break;
            case PINK:
                super.setColor(Color.PINK);
                super.setAnimator(new PinkGhostAnimator(this));
                break;
            case ORANGE:
                super.setColor(Color.ORANGE);
                super.setAnimator(new OrangeGhostAnimator(this));
                break;
            case CYAN:
                super.setColor(Color.CYAN);
                super.setAnimator(new CyanGhostAnimator(this));
                break;
        }

    }
    
    public GhostController getController(){
        return ghostController;
    }

    public Vector2 getTarget() {
        return target;
    }

    public void setTarget(Vector2 target) {
        this.target = target;
    }

    public Vector2 getStartTile() {
        return startTile;
    }

    public void setInPlay() {        
        super.setPositionX(13.5f);
        super.setPositionY(21f);
        super.setRectX(13.5f * 16);
        super.setRectY(21f * 16);
    }
}
