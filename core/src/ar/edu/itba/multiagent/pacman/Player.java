package ar.edu.itba.multiagent.pacman;

import com.badlogic.gdx.math.Vector2;

public class Player extends GameObject {
	GameMap gameMap;
	Direction desiredDirection = null;
	public Player(GameMap gm){
		gameMap = gm;
	}

	public void tryToChangeDirection(Direction d){
		if(d == null)
			return;

		if(canMove(d.directionVector())){
			setDirection(d);
			desiredDirection = null;
		} else {
			desiredDirection = d;
		}
	}

	public void noLongerDesired(Direction d){
		if(desiredDirection == d){
			desiredDirection = null;
		}
	}
	public boolean canMove(Vector2 v){
		return !gameMap.hasWall(getPosition(), v);
	}

	public void update(float deltaTime){
		tryToChangeDirection(desiredDirection);
		if(gameMap.canWalk(getPosition(), getDirection().directionVector())) {
			normalizeToCenter(deltaTime);
			Vector2 p = getPosition();
			p.add(getDirection().directionVector().cpy().scl(100 * deltaTime));
			setPosition(p);
		}
	}
	public void normalizeToCenter(float deltaTime){
		Vector2 extra = new Vector2(0, 0);
		if (getDirection().directionVector().x != 0){
			float y = getPosition().y;
			float desiredY = y - (y % getHeight()) + getHeight() / 2;
			extra.set(0, desiredY - y);
		} else if(getDirection().directionVector().y != 0) {
			float x = getPosition().x;
			float desiredX = x - (x % getWidth()) + getWidth() / 2;
			extra.set(desiredX - x, 0);
		}
		getPosition().add(extra.scl(20 * deltaTime));
	}
}
