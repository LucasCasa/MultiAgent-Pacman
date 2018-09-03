package com.behr.components.playercomponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.behr.components.IComponent;
import com.behr.gameobjects.GameObject;

public class InputComponent implements IComponent{

    private float MOVESPEED = 4;

    // HANDLE INPUT
    public void update(GameObject object) {

        // UP
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            object.setVelocityY(+MOVESPEED);
        }
        // DOWN
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            object.setVelocityY(-MOVESPEED);
        }
        // LEFT
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            object.setVelocityX(-MOVESPEED);
        }
        // RIGHT
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            object.setVelocityX(+MOVESPEED);

        }

    }
    
}
