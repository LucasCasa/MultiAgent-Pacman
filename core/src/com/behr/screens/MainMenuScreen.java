package com.behr.screens;

import com.behr.pacdan.PacDanGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class MainMenuScreen implements Screen {

    private final PacDanGame game;

    // BUTTONS
    private final TextButton buttonPlay;
    private final TextButton buttonOptions;
    private final TextButton buttonQuit;
    private final TextButton buttonScores;
    private final Label title;

    // GDX STUFF    
    private final Stage stage = new Stage();
    private Table tableMenu = new Table();

    public MainMenuScreen(PacDanGame gameIn) {
        this.game = gameIn;

        // make buttons
        buttonPlay = new TextButton("START", MenuStyles.cyanStyle);
        buttonOptions = new TextButton("OPTIONS", MenuStyles.pinkStyle);
        buttonScores = new TextButton("SCORES", MenuStyles.redStyle);
        buttonQuit = new TextButton("QUIT", MenuStyles.orangeStyle);

        // set background
        tableMenu.setBackground(MenuStyles.bluePatch);

        // make title
        title = new Label(PacDanGame.TITLE, MenuStyles.labelStyle);
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
        stage.getViewport().update(i, i1);
    }

    @Override
    public void show() {
        // BUTTON ACTIONS
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });
        buttonOptions.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new OptionsScreen(game));
            }
        });
        buttonScores.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ScoreScreen(game));
            }
        });

        buttonQuit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        title.setAlignment(Align.center);

        //ADD BUTTONS TO TABLE  
        // first row
        tableMenu.setFillParent(true);
        tableMenu.add();// col1        
        tableMenu.add(title).size(650, 250).padBottom(100).colspan(3).center(); // col  2 3 4       
        tableMenu.add(); // col5     

        tableMenu.row();

        // second row
        tableMenu.add(buttonPlay).colspan(2).width(200).padLeft(80).left().padBottom(20); // col 1 2   
        tableMenu.add();                        // col 3
        tableMenu.add(buttonOptions).colspan(2).width(200).padRight(80).right().padBottom(20); // col 4 5    
        tableMenu.row();

        // third row
        tableMenu.add(buttonQuit).colspan(2).width(200).padLeft(80).left(); // col  1 2     
        tableMenu.add();                            // col 3
        tableMenu.add(buttonScores).colspan(2).width(200).padRight(80).right(); // col 4 5
        // done
        stage.addActor(tableMenu);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
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
