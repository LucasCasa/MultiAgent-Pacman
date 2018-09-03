package com.behr.screens;

import com.behr.pacdan.PacDanGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class OptionsScreen implements Screen {

    private boolean patEasyMode = false;
    private boolean showGhostTargets = true;

    private PacDanGame game;
    private NinePatchDrawable patch;
    private final Stage stage = new Stage();
    private Table table = new Table();

    private Label label;
    private TextButton btnBack;
    private TextButton btnDifficulty;
    private TextButton btnGhostTargets;

    public OptionsScreen(PacDanGame gameIn) {
        this.game = gameIn;

        TextButtonStyle styleToggle = MenuStyles.style;
        styleToggle.fontColor = Color.WHITE;
        styleToggle.checkedFontColor = Color.DARK_GRAY;

        table.setBackground(MenuStyles.patch);
        label = new Label("OPTIONS", MenuStyles.labelStyle);
        btnBack = new TextButton("BACK", MenuStyles.style);
        btnDifficulty = new TextButton("DIFFICULTY : NORMAL", styleToggle);
        btnGhostTargets = new TextButton("GHOST TARGETS : ON", styleToggle);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void show() {
        btnDifficulty.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (btnDifficulty.isChecked()) {
                    btnDifficulty.setText("DIFFICULTY : PAT");

                } else {
                    btnDifficulty.setText("DIFFICULTY : NORMAL");

                }
            }
        });
        btnGhostTargets.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (btnGhostTargets.isChecked()) {
                    btnGhostTargets.setText("GHOST TARGETS : OFF");

                } else {
                    btnGhostTargets.setText("GHOST TARGETS : ON");

                }
            }
        });
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        table.setFillParent(true);
        table.add(btnDifficulty).size(500, 100).pad(10);
        table.row();
        table.add(btnGhostTargets).size(500, 100).pad(10);
        table.row();
        table.add(btnBack).size(300, 100).pad(10);

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        table.clearChildren();
        btnBack.setChecked(false);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

}
