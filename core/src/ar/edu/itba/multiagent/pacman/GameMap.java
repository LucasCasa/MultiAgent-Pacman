package ar.edu.itba.multiagent.pacman;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class GameMap {
	private final int WIDTH = 28;
	private final int HEIGHT = 31;
	private boolean[][] blocks = new boolean[HEIGHT][WIDTH];
	private boolean[][] smallDots = new boolean[HEIGHT][WIDTH];
	private boolean[][] bigDots = new boolean[HEIGHT][WIDTH];
	TiledMapTileLayer map;

	public GameMap(TiledMap m){
		map = (TiledMapTileLayer) m.getLayers().get("dots");
		for(MapObject mo : m.getLayers().get("collide").getObjects()){
			int fromX = (int) (Float.parseFloat(mo.getProperties().get("x").toString())) / 16;
			int fromY = (int) (Float.parseFloat(mo.getProperties().get("y").toString()) - 32) / 16;
			int toX = fromX + (int) (Float.parseFloat(mo.getProperties().get("width").toString())) / 16;
			int toY = fromY + (int) (Float.parseFloat(mo.getProperties().get("height").toString())) / 16;
			for(int i = fromX; i<toX;i++){
				for(int j = fromY; j<toY;j++){
					if(j == -1){
						System.out.println("sss");
					}
					blocks[HEIGHT - j - 1][i] = true;
				}
			}
		}
		TiledMapTileLayer dotLayer = (TiledMapTileLayer) m.getLayers().get("dots");
		for(int i = 0; i < WIDTH; i++){
			for(int j = 0; j < HEIGHT + 2; j++){
				TiledMapTileLayer.Cell c = dotLayer.getCell(i, j);
				if(c != null){
					if(c.getTile().getId() == 23){
						bigDots[HEIGHT - j + 1][i] = true;
					} else {
						smallDots[HEIGHT - j + 1][i] = true;
					}
				}
			}
		}
		prettyPrint();
	}

	/**
	 * Relative tile number
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean canWalkLocal(int x, int y){
		return !blocks[HEIGHT - y - 1][x];
	}

	/**
	 * Global position on the world
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean canWalkGlobal(float x, float y){
		int px = (int)(x / 16);
		int py = (int)((y - 32) / 16);
		return !blocks[HEIGHT - py - 1][px];
	}

	public boolean canWalk(Vector2 position, Vector2 direction){
		int x = (int)((position.x / 16) + direction.x);
		int y = (int)(((position.y - 32)/ 16) + direction.y);
		if(x < 0 || x > WIDTH - 1){
			return true;
		}
		boolean canMoveToNext = !blocks[HEIGHT - y - 1][x];
		boolean canMoveToCenterLine = true;
		if(direction.x > 0){
			canMoveToCenterLine = x * 16 - 8 - position.x > 0;
		} else if( direction.x < 0){
			canMoveToCenterLine = x * 16 + 16 + 8 - position.x < 0;
		} else if (direction.y > 0) {
			canMoveToCenterLine = y * 16 + 32 - 8 - position.y > 0;
		} else if (direction.y < 0) {
			canMoveToCenterLine = y * 16 + 32 + 16 + 8 - position.y < 0;
		}
		return canMoveToNext || canMoveToCenterLine;
	}
	public boolean hasWall(Vector2 position, Vector2 direction){
		int x = (int)((position.x / 16) + direction.x);

		if(x < 0 || x > WIDTH - 1){
			return false;
		}

		int y = (int)(((position.y - 32)/ 16) + direction.y);
		return blocks[HEIGHT - y - 1][x];
	}

	public void eatDot(float x, float y){
		int px = (int)(x / 16);
		int py = (int)((y - 32) / 16);
		smallDots[HEIGHT - py][px] = false;
		System.out.println(px + " " + py);
		map.setCell(px , py + 2, null);
	}
	void prettyPrint(){
		for(int j = 0; j < HEIGHT; j++){
			for(int i = 0; i<WIDTH;i++){
				System.out.print((blocks[j][i]?"1":(smallDots[j][i]?"2":(bigDots[j][i]?"3":"0"))) + " ");
			}
			System.out.println("");
		}
	}

	public boolean isOutside(Vector2 position) {
		int x = ((int)(position.x / 16));
		return x < 0 || x >= WIDTH ;
	}
}
