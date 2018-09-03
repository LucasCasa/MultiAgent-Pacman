package com.behr.animators;

import com.behr.gameobjects.GameObject;

public class FrightGhostAnimator extends Animator{

    public FrightGhostAnimator(GameObject oIn) {
        super(oIn);
        super.setColumns(2);
        super.setRows(4);
        super.setSpeed(1f);
        super.loadTextures("sprites/FrightGhost_Anim.png");
    }

}
