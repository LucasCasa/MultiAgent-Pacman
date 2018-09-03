package com.behr.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import map.GameMap;
import com.behr.animators.Animator;

public abstract class GameObject {

    public final static int WIDTH = 16;
    public final static int HEIGHT = 16;

    private Vector2 position = new Vector2();
    private Vector2 velocity = new Vector2();
    private Rectangle entityRect;
    
    private Animator animator;

    public GameMap currentMap;
    
    private Color color = Color.GRAY;    

    public GameObject(float xIn, float yIn, GameMap mapIn) {
        position.set(xIn, yIn);
        entityRect = new Rectangle(xIn * 16, yIn * 16, WIDTH, HEIGHT);
        velocity.set(0, 0);        
        currentMap = mapIn;
    }

    abstract public void update();

    public Vector2 getPosition() {
        return position;
    }

    public void setPositionX(float xIn) {
        this.position.x = xIn;
        //entityRect.x = xIn *16 ;
    }

    public void setPositionY(float yIn) {
        this.position.y = yIn;
        //entityRect.y = yIn *16;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocityX(float velocity) {
        this.velocity.x = velocity;
    }

    public void setVelocityY(float velocity) {
        this.velocity.y = velocity;
    }

    public GameMap getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(GameMap currentMap) {
        this.currentMap = currentMap;
    }

    public Rectangle getEntityRect() {
        return entityRect;
    }

    public void setRectX(float xIn) {
        this.entityRect.x = xIn;
        //this.position.x = xIn;
    }
     public void setRectY(float yIn) {
        this.entityRect.y = yIn;
        //this.position.y = yIn;
    }

    public void setColor(Color colorIn) {        
        this.color = colorIn;
    }

    public Color getColor() {
        return color;
    }

    public Animator getAnimator() {
        return animator;
    }

    public void setAnimator(Animator animator) {
        this.animator = animator;
    }


}
