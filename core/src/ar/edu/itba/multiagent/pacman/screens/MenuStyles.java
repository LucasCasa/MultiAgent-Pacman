package com.behr.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.behr.pacdan.PacDanGame;

public class MenuStyles {

    
    protected static NinePatchDrawable patch;
    protected static NinePatchDrawable bluePatch;
    protected static NinePatchDrawable yellowPatch;
    protected static NinePatchDrawable cyanPatch;
    protected static NinePatchDrawable pinkPatch;
    protected static NinePatchDrawable orangePatch;
    protected static NinePatchDrawable redPatch;

    protected static TextButtonStyle style;
    protected static TextButtonStyle yellowStyle;
    protected static TextButtonStyle cyanStyle;
    protected static TextButtonStyle pinkStyle;
    protected static TextButtonStyle orangeStyle;
    protected static TextButtonStyle redStyle;
    
    protected static LabelStyle labelStyle;

    public MenuStyles() {

        makeNormalStyle();
        makeBluePatch();
        makeYellowPatch();
        makeCyanPatch();
        makePinkPatch();
        makeOrangePatch();
        makeRedPatch();
        makeStyles();
        
        makeLabelStyle();

    }

    private void makeNormalStyle() {
        patch = new NinePatchDrawable(
                new NinePatch(
                        new Texture(Gdx.files.internal("ui/NESMENU.9.png"))));

        style = new TextButton.TextButtonStyle(patch, patch, patch, PacDanGame.smallFont);
        style.downFontColor = Color.DARK_GRAY;
    }

    private void makeBluePatch() {
        bluePatch = new NinePatchDrawable(
                new NinePatch(
                        new Texture(Gdx.files.internal("ui/NESMENUBLUE.9.png"))));
    }

    private void makeYellowPatch() {
        yellowPatch = new NinePatchDrawable(
                new NinePatch(
                        new Texture(Gdx.files.internal("ui/MenuYellow.9.png"))));
    }

    private void makeStyles() {
        yellowStyle = new TextButton.TextButtonStyle(
                yellowPatch, yellowPatch, yellowPatch,
                PacDanGame.smallFont);

        cyanStyle = new TextButton.TextButtonStyle(
                cyanPatch, cyanPatch, cyanPatch,
                PacDanGame.smallFont);

        pinkStyle = new TextButton.TextButtonStyle(
                pinkPatch, pinkPatch, pinkPatch,
                PacDanGame.smallFont);
        orangeStyle = new TextButton.TextButtonStyle(
                orangePatch, orangePatch, orangePatch,
                PacDanGame.smallFont);

        redStyle = new TextButton.TextButtonStyle(
                redPatch, redPatch, redPatch,
                PacDanGame.smallFont);
    }

    private void makeCyanPatch() {
        cyanPatch = new NinePatchDrawable(
                new NinePatch(
                        new Texture(Gdx.files.internal("ui/MenuCyan.9.png"))));
    }

    private void makePinkPatch() {
        pinkPatch = new NinePatchDrawable(
                new NinePatch(
                        new Texture(Gdx.files.internal("ui/MenuPink.9.png"))));
    }

    private void makeOrangePatch() {

        orangePatch = new NinePatchDrawable(
                new NinePatch(
                        new Texture(Gdx.files.internal("ui/MenuOrange.9.png"))));
    }

    private void makeRedPatch() {
        redPatch = new NinePatchDrawable(
                new NinePatch(
                        new Texture(Gdx.files.internal("ui/MenuRed.9.png"))));
    }

    private void makeLabelStyle() {
        labelStyle = new Label.LabelStyle(PacDanGame.smallFont, Color.WHITE);
        labelStyle.background = yellowPatch;
        labelStyle.font = PacDanGame.bigFont;
    }

}
