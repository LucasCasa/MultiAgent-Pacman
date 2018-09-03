package com.behr.screens;

import com.behr.pacdan.PacDanGame;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.behr.assets.Assets;

public class SplashScreen implements Screen {

    private Stage stage = new Stage();
    private Table table = new Table();
    private PacDanGame game;

    private Texture splashTexture = new Texture(Gdx.files.internal("behrcorp.png"));
    private Image splashImage = new Image(splashTexture);

    private boolean animationDone = false;

    public SplashScreen(PacDanGame gameIn) {
        this.game = gameIn;
        MenuStyles menustyles = new MenuStyles();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); //sets clear color to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clear the batch
        stage.act(); //update all actors
        stage.draw(); //draw all actors on the Stage.getBatch()

        if (Assets.update()) {
            if (animationDone) {
                game.setScreen(new MainMenuScreen(game));
            }
        }
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void show() {

        splashImage.addAction(
                Actions.sequence(
                        Actions.alpha(0), Actions.fadeIn(0.5f),
                        Actions.delay(2), Actions.fadeOut(0.5f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                animationDone = true;
                            }
                        })));

        Assets.queueLoading();
        table.setFillParent(true);
        table.add(splashImage);
        stage.addActor(table);
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        splashTexture.dispose();

    }

}
