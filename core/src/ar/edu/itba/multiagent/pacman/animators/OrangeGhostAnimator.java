package com.behr.animators;

import com.behr.gameobjects.GameObject;

public class OrangeGhostAnimator extends Animator{

    public OrangeGhostAnimator(GameObject oIn) {
        super(oIn);
        super.setColumns(2);
        super.setRows(4);
        super.loadTextures("sprites/OrangeGhost_Anim.png");
    }

}
