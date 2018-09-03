package map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class GameMap {

    // name of collision object layer in Tiled editor
    protected static final String COLLISIONLAYERNAME = "collide";
    
    private final String mapPath;

    // Map Size: a property read from TMX file
    protected static int widthInTiles, heightInTiles, tileSizeInPx;

    // The TMX map
    private TiledMap map;

    // collision data
    private MapCollisionComponent collisionHandler;

    // Rendering    
    public MapRenderingComponent renderer;
    
    // holds information about dots
    private DotLayer dotLayer;

    // Constructor recieves file path to the TMX map file
    public GameMap(String mapPathIn) {
        // load the map and its component bits
        mapPath = mapPathIn;
        if (loadMap(mapPathIn)) {
            Gdx.app.log("SUCCESS", "Map loaded");
        }

        // set up the renderer
        collisionHandler = new MapCollisionComponent(map);
        renderer = new MapRenderingComponent(map);        
        dotLayer = new DotLayer((TiledMapTileLayer) map.getLayers().get("dots"));
    }

    /*
     ** PUBLIC METHODS   
     ** ------------------------------------------------------------------------
     */
    // render all map layers
    public void render(OrthographicCamera cam) {
        renderer.render(cam);
    }

    //render only the shapes of objects in the collisionlayer
    public void renderCollisionObjects(OrthographicCamera camIn, ShapeRenderer sr) {
        renderer.renderCollisionObjects(sr, getCollisionObjects());
    }

    public void renderGrid(OrthographicCamera camIn, ShapeRenderer sr) {
        renderer.renderGrid(camIn, sr);
    }

    public void renderBlocked(SpriteBatch sb) {
        renderer.renderBlocked(sb, getBlocked());
    }

    // method for detecting collisions between rectangle and map object rectangles 
    public MapObjects getCollisionObjects() {
        return collisionHandler.getMapObjects();
    }

    public boolean[][] getBlocked() {
        return collisionHandler.getBlockedTiles();
    }

    public int getWidth() {
        return widthInTiles;
    }

    public int getHeight() {
        return heightInTiles;
    }

    public int getTileSize() {
        return tileSizeInPx;
    }

    public DotLayer getDotLayer() {
        return dotLayer;
    }

    /*
     ** PRIVATE METHODS
     **--------------------------------------------------------------------------
     */
    // Loads the map, makes sure it loaded, then gets properties from the map   
    private boolean loadMap(String path) {
        map = new TmxMapLoader().load(path);
        if (map == null) {
            System.out.println("ERROR: MAP DIDNT LOAD CORRECTLY. CHECK PATH");
            return false;
        }
        MapProperties prop = map.getProperties();
        widthInTiles = prop.get("width", Integer.class);
        heightInTiles = prop.get("height", Integer.class);
        tileSizeInPx = prop.get("tilewidth", Integer.class);
        return true;
    }

    public void resetMapDots(){
        dotLayer = new DotLayer((TiledMapTileLayer) map.getLayers().get("dots"));
    }

    public void reloadMap() {
        if (loadMap(mapPath)) {
            Gdx.app.log("SUCCESS", "Map loaded");
        }

        // set up the renderer
        collisionHandler = new MapCollisionComponent(map);
        renderer = new MapRenderingComponent(map);
        dotLayer = new DotLayer((TiledMapTileLayer) map.getLayers().get("dots"));
    }
}
