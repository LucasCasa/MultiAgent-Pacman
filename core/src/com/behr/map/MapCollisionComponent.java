package map;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import static map.GameMap.heightInTiles;
import static map.GameMap.tileSizeInPx;
import static map.GameMap.widthInTiles;

public class MapCollisionComponent {

    private MapObjects mapCollideObjects;
    private boolean blocked[][];

    public MapCollisionComponent(TiledMap mapIn) {
        mapCollideObjects
                = mapIn.getLayers().get(GameMap.COLLISIONLAYERNAME).getObjects();
        loadCollisions();
    }

    private void loadCollisions() {
        //fill the blocked array
        blocked = new boolean[widthInTiles][heightInTiles];
        for (int i = 0; i < widthInTiles; i++) {
            for (int j = 0; j < heightInTiles; j++) {
                Rectangle temp = new Rectangle(i * tileSizeInPx, j * tileSizeInPx, tileSizeInPx, tileSizeInPx);
                blocked[i][j] = detectCollision(temp);
            }
        }
    }

    public boolean detectCollision(Rectangle rect1) {
        Rectangle rect1Temp = rect1;

        for (RectangleMapObject rectangleObject : mapCollideObjects.getByType(RectangleMapObject.class)) {
            Rectangle rectObject = rectangleObject.getRectangle();
            if (rect1Temp.overlaps(rectObject)) {
                return true;
            }
        }
        return false;
    }

    public MapObjects getMapObjects() {
        return mapCollideObjects;
    }

    public boolean[][] getBlockedTiles() {
        return blocked;
    }

}
