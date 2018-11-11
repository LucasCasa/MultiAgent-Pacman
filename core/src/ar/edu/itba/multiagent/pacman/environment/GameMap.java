package ar.edu.itba.multiagent.pacman.environment;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;

public class GameMap {
	private final int WIDTH = 28;
	private final int HEIGHT = 31;
	private boolean[][] blocks = new boolean[HEIGHT][WIDTH];
	private boolean[][] smallDots = new boolean[HEIGHT][WIDTH];
	private TiledMapTileLayer map;

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
						System.out.println("shouldnt happen");
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
	 */
	public boolean canWalkLocal(GridPoint2 position){
		return !blocks[HEIGHT - position.y - 1][position.x];
	}

	/**
	 * Global position on the world
	 */
	public boolean canWalkGlobal(Vector2 position){
		GridPoint2 boardPosition = PositionUtils.worldToBoard(position);
		return !blocks[HEIGHT - boardPosition.y - 1][boardPosition.x];
	}

	public boolean canWalk(Vector2 position, GridPoint2 direction){
		GridPoint2 boardPosition = PositionUtils.worldToBoard(position);
		int x = boardPosition.x + direction.x;
		int y = boardPosition.y + direction.y;
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

	public boolean hasWall(Vector2 position, GridPoint2 direction){
		return hasWall(PositionUtils.worldToBoard(position), direction);
	}

	public boolean hasWall(GridPoint2 position, GridPoint2 direction){
		int x = position.x  + direction.x;
		int y = position.y + direction.y;

		if(x < 0 || x > WIDTH - 1){
			return false;
		}
		return blocks[HEIGHT - y - 1][x];
	}

	public void eatDot(Vector2 position){
		GridPoint2 boardPosition = PositionUtils.worldToBoard(position);
		smallDots[HEIGHT - boardPosition.y][boardPosition.x] = false;
		map.setCell(boardPosition.x , boardPosition.y + 2, null);
	}

	private void prettyPrint(){
		for(int j = 0; j < HEIGHT; j++){
			for(int i = 0; i<WIDTH;i++){
				System.out.print(blocks[j][i]?"1":"2");
			}
			System.out.println();
		}
	}

	public boolean isOutside(Vector2 position) {
		int x = PositionUtils.worldToBoard(position).x;
		return x < 0 || x >= WIDTH;
	}

	public int getWIDTH() {
		return WIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	public boolean[][] getBlocks(){ return blocks; }
}
