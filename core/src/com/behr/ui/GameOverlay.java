package com.behr.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.behr.gameobjects.Player;

public class GameOverlay {
    
    private BitmapFont font;
    
    private Player player;    
    
    private SpriteBatch overlayBatch;
    
    private int points = 0;
    private Integer lastScore=0;
    String scoreText;
    private Texture pacmantxt;

    public GameOverlay(Player playerIn, OrthographicCamera camin) {
        // save params
        player = playerIn;
        // make font
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("font/font1.ttf"));
        FreeTypeFontParameter params = new FreeTypeFontParameter();
        params.size = 16;

        font = gen.generateFont(params);
        font.setColor(Color.WHITE);
        font.setScale(0.2f);
        font.setFixedWidthGlyphs("0123456789");
        
        overlayBatch = new SpriteBatch();
        overlayBatch.setProjectionMatrix(camin.combined);
        
        pacmantxt = new Texture(Gdx.files.internal("sprites/PacManIcon.png"));
    }
    public void drawScore(SpriteBatch sb){
        Integer temp = player.getScore();
        scoreText = temp.toString();               
        
        overlayBatch.begin();
        
        font.draw(overlayBatch, scoreText, 1, 35.5f);
        overlayBatch.end();
        drawLives(sb);
    }
   private void drawLives(SpriteBatch sb) {
        sb.begin();
        for (int i = 0; i < player.getLives(); i++) {
            sb.draw(pacmantxt, (1 + i), 0.5f, 1f, 1f);
        }
        sb.end();
    }
   public void dispose(){       
       pacmantxt.dispose();
   }

}
