package com.behr.components.ghostcomponents;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.behr.components.IComponent;
import com.behr.gameobjects.GameObject;
import com.behr.gameobjects.Ghost;

public class PathfindingComponent implements IComponent {

    private boolean up, down, left, right;

    private float lastVelX = 0, lastVelY = 0;

    private Ghost g;

    public PathfindingComponent() {

    }

    @Override
    public void update(GameObject o) {
        g = (Ghost) o;
        calculatePossibleMovements();
        setVelocities();
    }

    private void calculatePossibleMovements() {
        lastVelX = g.getVelocity().x;
        lastVelY = g.getVelocity().y;
        up = down = left = right = true;
        // not possible to reverse direction
        if (lastVelX != 0) {
            if (lastVelX > 0) {
                left = false;
            } else {
                right = false;
            }
        }
        if (lastVelY != 0) {
            if (lastVelY > 0) {
                down = false;
            } else {
                up = false;
            }
        }

        if (detectCollision(new Rectangle(
                g.getEntityRect().x,
                g.getEntityRect().y + g.MOVESPEED,
                g.getEntityRect().width,
                g.getEntityRect().height))) {
            up = false;
        }
        if (detectCollision(new Rectangle(
                g.getEntityRect().x,
                g.getEntityRect().y - g.MOVESPEED,
                g.getEntityRect().width,
                g.getEntityRect().height))) {
            down = false;
        }
        if (detectCollision(new Rectangle(
                g.getEntityRect().x - g.MOVESPEED,
                (float) g.getEntityRect().y,
                g.getEntityRect().width,
                g.getEntityRect().height))) {
            left = false;
        }
        if (detectCollision(new Rectangle(
                g.getEntityRect().x + g.MOVESPEED,
                g.getEntityRect().y,
                g.getEntityRect().width,
                g.getEntityRect().height))) {
            right = false;
        }
    }

    private void setVelocities() {
        Vector2 newPos1 = new Vector2();
        Vector2 newPos2 = new Vector2();
        Vector2 newPos3 = new Vector2();
        float dist1, dist2, dist3;

        // 4 cases: ghost can only move in one direction
        if (up && !(down || left || right)) {
            g.setVelocityY(+g.MOVESPEED);
            return;
        }
        if (down && !(up || left || right)) {
            g.setVelocityY(-g.MOVESPEED);
            return;
        }
        if (left && !(up || down || right)) {
            g.setVelocityX(-g.MOVESPEED);
            return;
        }
        if (right && !(left || up || down)) {
            g.setVelocityX(+g.MOVESPEED);
            return;
        }

        // 6 cases: ghost can move in 2 directions, ur, ul, ud ,rd, lr, ld
        //1
        if (up && right && !(left || down)) {
            // UP position
            newPos1.set(g.getPosition().x, g.getPosition().y + 1);
            // RIGHT position
            newPos2.set(g.getPosition().x + 1, g.getPosition().y);
            // calculate 2 distances to pos1 and pos2
            dist1 = newPos1.dst(g.getTarget());
            dist2 = newPos2.dst(g.getTarget());
            if (dist1 < dist2) {
                g.setVelocityY(+g.MOVESPEED); 
                g.setVelocityX(0); 
            } else {
                g.setVelocityX(+g.MOVESPEED);
                g.setVelocityY(0);
            }
            return;
        }
        //2
        if (up && left && ! (down || right)) {
            // UP position
            newPos1.set(g.getPosition().x, g.getPosition().y + 1);
            // LEFT position
            newPos2.set(g.getPosition().x - 1, g.getPosition().y);
            // calculate 2 distances to pos1 and pos2
            dist1 = newPos1.dst(g.getTarget());
            dist2 = newPos2.dst(g.getTarget());
            if (dist1 < dist2) {
                g.setVelocityY(+g.MOVESPEED); 
                g.setVelocityX(0); 
            } else {
                g.setVelocityX(-g.MOVESPEED);
                g.setVelocityY(0); 
            }
            return;
        }

        //3
        if (up && down && !(left || right)) {
            // UP position
            newPos1.set(g.getPosition().x, g.getPosition().y + 1);
            // DOWN position
            newPos2.set(g.getPosition().x, g.getPosition().y - 1);
            // calculate 2 distances to pos1 and pos2
            dist1 = newPos1.dst(g.getTarget());
            dist2 = newPos2.dst(g.getTarget());
            if (dist1 < dist2) {
                g.setVelocityY(+g.MOVESPEED);
                g.setVelocityX(0); 
            } else {
                g.setVelocityY(-g.MOVESPEED);
                g.setVelocityX(0); 
            }
            return;
        }
        //4
        if (down && right && !(left || up)) {
            // DOWN position
            newPos1.set(g.getPosition().x, g.getPosition().y - 1);
            // RIGHT position
            newPos2.set(g.getPosition().x + 1, g.getPosition().y);
            // calculate 2 distances to pos1 and pos2
            dist1 = newPos1.dst(g.getTarget());
            dist2 = newPos2.dst(g.getTarget());
            if (dist1 < dist2) {
                g.setVelocityY(-g.MOVESPEED); 
                g.setVelocityX(0);
            } else {
                g.setVelocityX(+g.MOVESPEED); 
                g.setVelocityY(0); 
            }
            return;
        }
        //5
        if (left && right && !(up || down)) {
            // LEFT position
            newPos1.set(g.getPosition().x - 1, g.getPosition().y);
            // RIGHT position
            newPos2.set(g.getPosition().x + 1, g.getPosition().y);
            // calculate 2 distances to pos1 and pos2
            dist1 = newPos1.dst(g.getTarget());
            dist2 = newPos2.dst(g.getTarget());
            if (dist1 < dist2) {
                g.setVelocityX(-g.MOVESPEED); 
                g.setVelocityY(0); 
            } else {
                g.setVelocityX(+g.MOVESPEED); 
                g.setVelocityY(0); 
            }
            return;
        }
        //6
        if (left && down && !(up || right)) {
            // LEFT position
            newPos1.set(g.getPosition().x - 1, g.getPosition().y);
            // DOWN position
            newPos2.set(g.getPosition().x, g.getPosition().y - 1);
            // calculate 2 distances to pos1 and pos2
            dist1 = newPos1.dst(g.getTarget());
            dist2 = newPos2.dst(g.getTarget());
            if (dist1 < dist2) {
                g.setVelocityX(-g.MOVESPEED); 
                g.setVelocityY(0); 
            } else {
                g.setVelocityY(-g.MOVESPEED);
                g.setVelocityX(0); 
            }
            return;
        }

        // 4 cases: ghost can move in 3 directions, ldr, dru, rul, uld
        if (left && down && right && !up) {
            newPos1.set(g.getPosition().x - 1, g.getPosition().y); // LEFT
            newPos2.set(g.getPosition().x, g.getPosition().y - 1); // DOWN
            newPos3.set(g.getPosition().x + 1, g.getPosition().y); // RIGHT
            dist1 = newPos1.dst(g.getTarget());
            dist2 = newPos2.dst(g.getTarget());
            dist3 = newPos3.dst(g.getTarget());
            if ((dist1 < dist2) && (dist1 < dist3)) {
                g.setVelocityX(-g.MOVESPEED);// LEFT
                g.setVelocityY(0); 
                return;
            } else {
                if ((dist2 < dist3) && (dist2 < dist1)) {
                    g.setVelocityY(-g.MOVESPEED); // DOWN
                    g.setVelocityX(0);
                    return;
                } else {
                    g.setVelocityX(+g.MOVESPEED);  // RIGHT
                    g.setVelocityY(0);
                    return;
                }
            }

        }
        if (down && right && up && !left) {
            newPos1.set(g.getPosition().x, g.getPosition().y - 1); // DOWN
            newPos2.set(g.getPosition().x + 1, g.getPosition().y); // RIGHT
            newPos3.set(g.getPosition().x, g.getPosition().y + 1); // UP
            dist1 = newPos1.dst(g.getTarget());
            dist2 = newPos2.dst(g.getTarget());
            dist3 = newPos3.dst(g.getTarget());
            if ((dist1 < dist2) && (dist1 < dist3)) {
                g.setVelocityY(-g.MOVESPEED); // DOWN
                g.setVelocityX(0); 
                return;
            } else {
                if ((dist2 < dist3) && (dist2 < dist1)) {
                    g.setVelocityX(+g.MOVESPEED); // RIGHT
                    g.setVelocityY(0);
                    return;
                } else {
                    g.setVelocityY(+g.MOVESPEED);  // UP
                    g.setVelocityX(0); 
                    return;
                }
            }
        }

        if (right && up && left && !down) {
            newPos1.set(g.getPosition().x + 1, g.getPosition().y); // RIGHT
            newPos2.set(g.getPosition().x, g.getPosition().y + 1); // UP
            newPos3.set(g.getPosition().x - 1, g.getPosition().y); // LEFT
            dist1 = newPos1.dst(g.getTarget());
            dist2 = newPos2.dst(g.getTarget());
            dist3 = newPos3.dst(g.getTarget());
            if ((dist1 < dist2) && (dist1 < dist3)) {
                g.setVelocityX(+g.MOVESPEED);// RIGHT
                g.setVelocityY(0); 
                return;
            } else {
                if ((dist2 < dist3) && (dist2 < dist1)) {
                    g.setVelocityY(+g.MOVESPEED);  // UP
                    g.setVelocityX(0);
                    return;
                } else {
                    g.setVelocityX(-g.MOVESPEED);  // LEFT
                    g.setVelocityY(0);
                    return;
                }
            }
        }
        if (up && left && down && !right) {
            newPos1.set(g.getPosition().x, g.getPosition().y + 1); // UP
            newPos2.set(g.getPosition().x - 1, g.getPosition().y); // LEFT
            newPos3.set(g.getPosition().x, g.getPosition().y - 1); // DOWN
            dist1 = newPos1.dst(g.getTarget());
            dist2 = newPos2.dst(g.getTarget());
            dist3 = newPos3.dst(g.getTarget());
            if ((dist1 < dist2) && (dist1 < dist3)) {
                g.setVelocityY(+g.MOVESPEED);// UP
                g.setVelocityX(0);
                return;
            } else {
                if ((dist2 < dist3) && (dist2 < dist1)) {
                    g.setVelocityX(-g.MOVESPEED);// LEFT
                    g.setVelocityY(0);
                    return;
                } else {
                    g.setVelocityY(-g.MOVESPEED); // DOWN
                    g.setVelocityX(0);
                    return;
                }
            }
        }
    }

    public boolean detectCollision(Rectangle rect1) {
        Rectangle rectIn = rect1;

        for (RectangleMapObject rectangleObject : g.currentMap.getCollisionObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectObject = rectangleObject.getRectangle();
            if (rectIn.overlaps(rectObject)) {
                return true;
            }
        }
        return false;
    }
}
