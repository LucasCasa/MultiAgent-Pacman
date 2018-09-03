package map;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;


public class DotLayer {
    
    TiledMapTileLayer dotLayer;

    public DotLayer(TiledMapTileLayer layerIn) {
        dotLayer = layerIn;
    }


    public int tryToEatDot(int x, int y) {
        // 0 = failed to eat
        // 1 = ate a small dot
        // 2 = ate a large dot
        if (dotLayer.getCell(x, y) != null) {
            if (dotLayer.getCell(x, y).getTile() != null) {

                if (dotLayer.getCell(x, y).getTile().getProperties().containsKey("DOT")) {
                    dotLayer.getCell(x, y).setTile(null);
                    return 1;
                }
                if (dotLayer.getCell(x, y).getTile().getProperties().containsKey("BIGDOT")) {
                    dotLayer.getCell(x, y).setTile(null);
                    return 2;
                }
            }
        }
        return 0;
    }

}
