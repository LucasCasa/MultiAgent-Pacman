package com.behr.screens;

import com.behr.pacdan.PacDanGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class ScoreScreen implements Screen {

    private PacDanGame game;
    
    private final Stage stage = new Stage();
    private Table table = new Table();
       
    private Label title;
    private TextButton btnMenu;
    private TextButton btnRetry;
    private TextButton btnQuit;
    
    private Label score1;
    private Label score2;
    private Label score3;
    private LabelStyle labelStyle;

    public ScoreScreen(PacDanGame gameIn) {
        this.game = gameIn;
        
        labelStyle = new Label.LabelStyle(game.bigFont, Color.WHITE);
               
        table.setBackground(MenuStyles.bluePatch);
        title = new Label("HIGH SCORES", labelStyle);
        btnMenu = new TextButton("MENU", MenuStyles.cyanStyle);
        btnRetry = new TextButton("RETRY", MenuStyles.yellowStyle);
        btnQuit = new TextButton("QUIT", MenuStyles.redStyle);

        // set scores
        score1 = new Label(PacDanGame.highScores.get(0).toString(), labelStyle);
        score2 = new Label(PacDanGame.highScores.get(1).toString(), labelStyle);
        score3 = new Label(PacDanGame.highScores.get(2).toString(), labelStyle);

    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void show() {
        btnMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        btnRetry.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });
        btnQuit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        table.setFillParent(true);
        title.setAlignment(Align.top);
        table.add();
        table.add(title).width(200).row().padTop(50);
        table.add(new Label("1.", labelStyle)).right();

        table.add(score1).width(100).right().row().padTop(20);
        table.add(new Label("2.", labelStyle)).right();

        table.add(score2).width(100).right().row().padTop(20);
        table.add(new Label("3.", labelStyle)).right();

        table.add(score3).width(100).right().row().padTop(20);

        table.add(btnMenu).width(200).padTop(150);
        table.add(btnRetry).width(200).padTop(150);
        table.add(btnQuit).width(200).padTop(150);
       
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        table.clearChildren();        
    }

    @Override
    public void dispose() {
    }

}
