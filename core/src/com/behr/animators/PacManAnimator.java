package com.behr.animators;

import com.behr.gameobjects.GameObject;

public class PacManAnimator extends Animator{

    public PacManAnimator(GameObject oIn) {
        super(oIn);
        super.setColumns(3);
        super.setRows(4);
        super.loadTextures("sprites/Pacman_Anim.png");
    }

}
