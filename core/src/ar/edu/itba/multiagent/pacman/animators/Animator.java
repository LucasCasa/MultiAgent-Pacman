package com.behr.animators;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.behr.gameobjects.GameObject;

public abstract class Animator {

    private GameObject o;

    private int NUM_COLUMNS = 1;
    private int NUM_ROWS = 1;

    protected Animation animLeft;
    protected Animation animDown;
    protected Animation animRight;
    protected Animation animUp;

    private Animation currentAnimation;
    private TextureRegion currentFrame;

    private float stateTime = 0;
    private float animationSpeed = 0.1f;

    public Animator(GameObject oIn) {
        this.o = oIn;
    }

    public void loadTextures(String pathIn) {
        Texture temp = new Texture(Gdx.files.internal(pathIn));
        TextureRegion[][] tempRegions = TextureRegion.split(
                temp,
                temp.getWidth() / NUM_COLUMNS,
                temp.getHeight() / NUM_ROWS);

        animLeft = new Animation(animationSpeed, tempRegions[0]);
        animDown = new Animation(animationSpeed, tempRegions[1]);
        animRight = new Animation(animationSpeed, tempRegions[2]);
        animUp = new Animation(animationSpeed, tempRegions[3]);
        currentAnimation = animDown;
    }

    public TextureRegion getTextureRegion() {

        chooseAnimationFromVelocity();
        currentFrame = currentAnimation.getKeyFrame(stateTime, true);
        return currentFrame;

    }

    public void setColumns(int columnsIn) {
        NUM_COLUMNS = columnsIn;
    }

    public void setRows(int rowsIn) {
        NUM_ROWS = rowsIn;
    }

    private void chooseAnimationFromVelocity() {
        if (o.getVelocity().x != 0) {
            if (o.getVelocity().x > 0) {
                currentAnimation = animRight;
                stateTime += Gdx.graphics.getDeltaTime();
            } else {
                currentAnimation = animLeft;
                stateTime += Gdx.graphics.getDeltaTime();
            }

        } else {
            if (o.getVelocity().y != 0) {
                if (o.getVelocity().y > 0) {
                    currentAnimation = animUp;
                    stateTime += Gdx.graphics.getDeltaTime();
                } else {
                    currentAnimation = animDown;
                    stateTime += Gdx.graphics.getDeltaTime();
                }

            }
        }
    }
    public void setSpeed(float speedIn){
        animationSpeed = speedIn;
    }

}
