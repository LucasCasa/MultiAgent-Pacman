package ar.edu.itba.multiagent.pacman;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class GameMap {
	private final int WIDTH = 28;
	private final int HEIGHT = 31;
	private boolean[][] blocks = new boolean[HEIGHT][WIDTH];

	public GameMap(TiledMap m){
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
					blocks[j][i] = true;
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
		return !blocks[y][x];
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
		return !blocks[py][px];
	}

	public boolean canWalk(Vector2 position, Vector2 direction){
		int x = (int)((position.x / 16) + direction.x);
		int y = (int)(((position.y - 32)/ 16) + direction.y);
		boolean canMoveToNext = !blocks[y][x];
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
		int y = (int)(((position.y - 32)/ 16) + direction.y);
		return blocks[y][x];
	}
	void prettyPrint(){
		for(int j = 0; j < HEIGHT; j++){
			for(int i = 0; i<WIDTH;i++){
				System.out.print((blocks[j][i]?"1":"0") + " ");
			}
			System.out.println("");
		}
	}
}
