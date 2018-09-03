package map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import static map.GameMap.heightInTiles;
import static map.GameMap.tileSizeInPx;
import static map.GameMap.widthInTiles;

public class MapRenderingComponent {

    private OrthogonalTiledMapRenderer tmxRenderer;

    public MapRenderingComponent(TiledMap mapIn) {
        tmxRenderer = new OrthogonalTiledMapRenderer(mapIn, 1 / 16f);
    }

    public void render(OrthographicCamera camIn) {   
        tmxRenderer.setView(camIn);
        tmxRenderer.render();
    }

    public void renderCollisionObjects(ShapeRenderer sr, MapObjects collisionObjectsIn) {
        sr.setColor(Color.GRAY);
        //sr.);
        sr.begin(ShapeRenderer.ShapeType.Filled);

        for (RectangleMapObject rectangleObject
                : collisionObjectsIn.getByType(RectangleMapObject.class)) {
            Rectangle rect = rectangleObject.getRectangle();
            sr.rect(rect.x, rect.y, rect.width, rect.height);
        }
        sr.end();
    }

    public void renderGrid(OrthographicCamera camIn, ShapeRenderer sr) {
        sr.setColor(Color.GRAY);
        sr.begin(ShapeRenderer.ShapeType.Line);
        // draw horizontal lines
        for (int i = 0; i < heightInTiles; i++) {
            //sr.line(x1, y1, x2, y2);
            sr.line(0, tileSizeInPx * i, 800, tileSizeInPx * i);
        }
        // draw vertical
        for (int j = 0; j < widthInTiles; j++) {
            sr.line(tileSizeInPx * j, 0, tileSizeInPx * j, 800);
        }
        sr.end();
    }

    public void renderBlocked(SpriteBatch sb, boolean[][] blocked) {
        //font.setScale(0.02f);
        BitmapFont fnt = new BitmapFont();
        fnt.setScale(0.05f);

        sb.begin();
        for (int i = 0; i < widthInTiles; i++) {
            for (int j = 0; j < heightInTiles; j++) {
                if (blocked[i][j]) {
                    fnt.draw(sb, "T", i, 1 + j);
                } else {
                    fnt.draw(sb, "F", i, 1 + j);
                }
            }
        }
        sb.end();
    }
}
