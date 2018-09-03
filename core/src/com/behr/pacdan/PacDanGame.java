package com.behr.pacdan;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.ArrayList;
import java.util.List;
import com.behr.screens.SplashScreen;

public class PacDanGame extends Game {

    // GLOBAL CONSTANTS
    public static final String TITLE = " PAC-DAN ";
    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;

    // global resources  
    public OrthographicCamera camera;
    public Viewport viewPort;
    public static ShapeRenderer sr;
    public static SpriteBatch sb;
    
    // fonts
    public static BitmapFont smallFont;
    public static BitmapFont bigFont;
    
    // high scores storage
    public static List<Integer> highScores = new ArrayList<Integer>();

    @Override
    public void create() {
        // Camera setup
        camera = new OrthographicCamera();    
        viewPort = new FitViewport(600,600, camera);
        
        // ShapeRenderer setup
        sr = new ShapeRenderer();
        sb = new SpriteBatch();
        
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("font/font1.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 30;        
        smallFont = gen.generateFont(params);
        smallFont.setColor(Color.WHITE);
        params.size = 48;
        bigFont = gen.generateFont(params);
        bigFont.setColor(Color.WHITE);
        smallFont.setFixedWidthGlyphs("0123456789");
        
        
        // put in nulkl high scores
        highScores.add(0);
        highScores.add(0);
        highScores.add(0);
        
        
        // set scteen to playing mode
        setScreen(new SplashScreen(this));
    }

    @Override
    public void resize(int width, int height){
        //viewPort.update(width, height);        
    }
    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
    }

}
