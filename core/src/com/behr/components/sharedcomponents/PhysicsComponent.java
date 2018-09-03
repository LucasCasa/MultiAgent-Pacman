package com.behr.components.sharedcomponents;


import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.behr.components.IComponent;
import com.behr.gameobjects.GameObject;
import com.behr.gameobjects.Ghost;
import map.GameMap;

public class PhysicsComponent implements IComponent {

    GameMap map;
  

    public PhysicsComponent(GameMap mapIn) {
        this.map = mapIn;
    }

    
    public void update(GameObject o) {
        map = o.getCurrentMap();
        
        //default collisions in both directions to false
        boolean collidingX = false, collidingY = false;

        // velX, velY is a plus-or-minus figure, telling which way to move
        // if adding velX to current position results in a collision, stop velX
        if (detectCollision(
                new Rectangle(
                        o.getEntityRect().x + o.getVelocity().x,
                        o.getEntityRect().y,
                        GameObject.WIDTH,
                        GameObject.HEIGHT))) {
            collidingX = true;
            o.setVelocityX(0);
        }
        // if not colliding, move the object
        if (!collidingX) {
            o.setPositionX(o.getPosition().x + (o.getVelocity().x / 16));
            o.setRectX(o.getEntityRect().x + o.getVelocity().x);
        }
        // if adding velY to current position results in a collision, stop velY

        if (detectCollision(
                new Rectangle(
                        o.getEntityRect().x,
                        o.getEntityRect().y + o.getVelocity().y,
                        GameObject.WIDTH,
                        GameObject.HEIGHT))) {
            collidingY = true;
            o.setVelocityY(0);
        }
        // if not colliding, move the object
        if (!collidingY) {
            o.setPositionY(o.getPosition().y + (o.getVelocity().y / 16));
            o.setRectY(o.getEntityRect().y + o.getVelocity().y);
        }

        handleScreenWrapping(o);
    }

    private boolean detectCollision(Rectangle rect1) {
        Rectangle rectIn = rect1;

        for (RectangleMapObject rectangleObject : map.getCollisionObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectObject = rectangleObject.getRectangle();
            if (rectIn.overlaps(rectObject)) {
                return true;
            }
        }
        return false;
    }

    private void handleScreenWrapping(GameObject o) {
        // If entity is at left side of screen

        if (o.getPosition().x < 1 && o.getPosition().y == 18) {
            //wrap to right side
            o.setPositionX(26);
            o.setPositionY(18);
            o.setRectX(26 * 16);
            o.setRectY(18 * 16);
        }
        // If entity is at right side of screen
        if (o.getPosition().x > 26 && o.getPosition().y == 18) {
            //wrap to left side
            o.setPositionX(1);
            o.setPositionY(18);
            o.setRectX(1 * 16);
            o.setRectY(18 * 16);

        }
    }
    
  public void reverse(Ghost g) {      
        if (g.getVelocity().x > 0) {
            g.setVelocityX(-g.MOVESPEED);

        } else {
            if (g.getVelocity().x < 0) {
                g.setVelocityX(+g.MOVESPEED);
            }
        }
        if (g.getVelocity().y > 0) {
            g.setVelocityY(-g.MOVESPEED);            

        } else {
            if (g.getVelocity().y < 0) {
                g.setVelocityY(+g.MOVESPEED);
            }
        }
        update(g);
    }
    
    

}
