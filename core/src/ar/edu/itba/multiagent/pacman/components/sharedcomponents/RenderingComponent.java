package com.behr.components.sharedcomponents;


import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.behr.components.IComponent;
import com.behr.gameobjects.GameObject;
import com.behr.gameobjects.Ghost;
import com.behr.pacdan.PacDanGame;

public class RenderingComponent implements IComponent {

    @Override
    public void update(GameObject o) {
        
            renderSprite(o);       
            //renderRectangle(o);       
    }

    private void renderSprite(GameObject o) {
        
        PacDanGame.sb.begin();
        PacDanGame.sb.draw(
                o.getAnimator().getTextureRegion(),
                o.getPosition().x - 0.15f,
                o.getPosition().y - 0.15f,
                1.3f,
                1.3f);
        PacDanGame.sb.end();
        
    }

    private void renderRectangle(GameObject o){
        PacDanGame.sr.setColor(o.getColor());
        
        PacDanGame.sr.begin(ShapeRenderer.ShapeType.Filled);
        PacDanGame.sr.rect(
                o.getEntityRect().x,
                o.getEntityRect().y,
                GameObject.WIDTH,
                GameObject.HEIGHT);
        PacDanGame.sr.end();            
    }
    
    public void renderTarget(Ghost g){
        PacDanGame.sr.setColor(g.getColor());
        
        PacDanGame.sr.begin(ShapeRenderer.ShapeType.Line);
        PacDanGame.sr.rect(
                g.getTarget().x*16 ,
                g.getTarget().y*16 ,
                GameObject.WIDTH,
                GameObject.HEIGHT);
        PacDanGame.sr.line(
                g.getPosition().x * 16 + 8,
                g.getPosition().y * 16 + 8,
                g.getTarget().x * 16 + 8,
                g.getTarget().y * 16 + 8);
        PacDanGame.sr.end();

    }
    
    public void setColor(){
        
    }

}
