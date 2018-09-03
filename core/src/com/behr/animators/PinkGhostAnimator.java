package com.behr.animators;

import com.behr.gameobjects.GameObject;

public class PinkGhostAnimator extends Animator{

    public PinkGhostAnimator(GameObject oIn) {
        super(oIn);
        super.setColumns(2);
        super.setRows(4);
        super.loadTextures("sprites/PinkGhost_Anim.png");
    }

}
