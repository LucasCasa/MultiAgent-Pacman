package com.behr.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.behr.components.playercomponents.InputComponent;
import com.behr.components.sharedcomponents.PhysicsComponent;
import com.behr.components.playercomponents.PlayerLogicComponent;
import com.behr.components.sharedcomponents.RenderingComponent;
import map.GameMap;
import com.behr.observers.ScoreListener;
import com.behr.animators.PacManAnimator;

public class Player extends GameObject {

    private final Vector2 homeTile = new Vector2(13.5f, 9f);
    private int lives = 3;

    private final InputComponent input;
    private final PhysicsComponent physics;
    private final RenderingComponent graphics;    
    public final PlayerLogicComponent logic;
    
    private ScoreListener scoreKeeper;

    public Player(float xIn, float yIn, GameMap mapIn) {
        super(xIn, yIn, mapIn);
        super.setColor(Color.YELLOW);

        // Initialize components
        input = new InputComponent();
        physics = new PhysicsComponent(mapIn);
        super.setAnimator(new PacManAnimator(this));
        graphics = new RenderingComponent();
        logic = new PlayerLogicComponent(mapIn);        
        
        // Listeners for scoring
        scoreKeeper = new ScoreListener();
        logic.addObserver(scoreKeeper);
        
        // default zero velocity
        super.setVelocityX(0);
        super.setVelocityY(0);                
    }

    @Override
    public void update() { // update all components        
        input.update(this);
        physics.update(this);
        graphics.update(this);
        logic.update(this);        
    }
    public void render(){
        graphics.update(this);
    }
    
    public int getScore(){
        return scoreKeeper.getScore();
    }

    public int getLives() {
        return lives;
    }

    public void loseLife() {
        this.lives--;
    }

    public void reset() { // reset after player death
        super.setPositionX(homeTile.x);
        super.setPositionY(homeTile.y);
        super.setRectX(homeTile.x * 16);
        super.setRectY(homeTile.y * 16);
        super.setVelocityX(0);
        super.setVelocityY(0);
    }

    public ScoreListener getScoreKeeper() {
        return scoreKeeper;
    }

}
