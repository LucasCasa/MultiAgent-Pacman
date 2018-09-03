package com.behr.animators;

import com.behr.gameobjects.GameObject;

public class CyanGhostAnimator extends Animator{

    public CyanGhostAnimator(GameObject oIn) {
        super(oIn);
        super.setColumns(2);
        super.setRows(4);
        super.loadTextures("sprites/CyanGhost_Anim.png");
    }

}
