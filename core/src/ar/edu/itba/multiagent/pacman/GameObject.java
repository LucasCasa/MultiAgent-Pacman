package ar.edu.itba.multiagent.pacman;

import ar.edu.itba.multiagent.pacman.environment.GameMap;
import ar.edu.itba.multiagent.pacman.environment.PositionUtils;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;

public class GameObject {
	private Direction direction = Direction.UP;
	private Vector2 position = new Vector2(50, 50);
	private int width = 16;
	private int height = 16;
	protected GameMap gameMap;
	private int speed;
	protected Direction desiredDirection = null;
	private boolean lockToGrid = true;

	public GameObject(GameMap gameMap, int speed, boolean lockToGrid) {
		this.gameMap = gameMap;
		this.speed = speed;
		this.lockToGrid = lockToGrid;
	}

	public boolean canMove(GridPoint2 v){
		return !gameMap.hasWall(getPosition(), v);
	}

	public void update(float deltaTime){
		tryToChangeDirection(desiredDirection);
		if(gameMap.canWalk(getPosition(), getDirection().directionVector())) {
			if(lockToGrid)
				normalizeToCenter(deltaTime);
			Vector2 p = getPosition();
			p.add(PositionUtils.gridToVector(direction.directionVector()).scl(speed * deltaTime));
			walkToPosition(p);
		}
	}

	private void walkToPosition(Vector2 p) {
		if(gameMap.isOutside(p)){
			System.out.println(p.x + " " + p.x % 400);
			if(p.x < 0) {
				setPosition(new Vector2(440, p.y));
			} else {
				setPosition(new Vector2(0, p.y));
			}
		}
	}

	/** When you turn after or before the center of the tile, you became misaligned,
	 * this function slowly sets you to the center of the tile
	 * @param deltaTime time between frames
	 */
	private void normalizeToCenter(float deltaTime){
		Vector2 extra = new Vector2(0, 0);
		if (getDirection().directionVector().x != 0){
			float y = getPosition().y;
			float desiredY = y - (y % getHeight()) + getHeight() / 2f;
			extra.set(0, desiredY - y);
		} else if(getDirection().directionVector().y != 0) {
			float x = getPosition().x;
			float desiredX = x - (x % getWidth()) + getWidth() / 2f;
			extra.set(desiredX - x, 0);
		}
		getPosition().add(extra.scl(20 * deltaTime));
	}

	/**
	 * Tries to change the direction of the object to the desired direction, if it can't then nothing happens
	 * @param d desired direction
	 */
	public boolean tryToChangeDirection(Direction d){
		if(d == null)
			return false;

		if(canMove(d.directionVector())){
			setDirection(d);
			desiredDirection = null;
			return true;
		} else {
			desiredDirection = d;
			return false;
		}
	}

	public Direction getDirection() {
		return direction;
	}

	private void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Direction getDesiredDirection(){
	    return desiredDirection;
    }
}
