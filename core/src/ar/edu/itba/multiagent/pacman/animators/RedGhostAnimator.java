package com.behr.animators;

import com.behr.gameobjects.GameObject;

public class RedGhostAnimator extends Animator{

    public RedGhostAnimator(GameObject oIn) {
        super(oIn);
        super.setColumns(2);
        super.setRows(4);
        super.loadTextures("sprites/RedGhost_Anim.png");
    }

}
