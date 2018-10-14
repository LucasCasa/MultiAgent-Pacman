package ar.edu.itba.multiagent.pacman.front;

import ar.edu.itba.multiagent.pacman.GameObject;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class ObjectRenderer {
	private GameObject object;
	private Texture sprite;
	private Texture arrow;
	private boolean showDirection = false;
	private int step = 0;
	private int totalSteps = 2;
	private float timePerFrame = 0.2f;
	private float timeSinceLastChange = 0f;

	ObjectRenderer(GameObject o, Texture t){
		object = o;
		sprite = t;
		this.showDirection = false;
		arrow = new Texture("sprites/arrow.png");
	}

	ObjectRenderer(GameObject o, Texture t, boolean showDirection){
		object = o;
		sprite = t;
		this.showDirection = showDirection;
		arrow = new Texture("sprites/arrow.png");
	}

	public void render(SpriteBatch batch, float deltaTime) {
		int spriteX, spriteY = 0;
		timeSinceLastChange += deltaTime;
		spriteX = step * (sprite.getWidth() / totalSteps);
		int unit = sprite.getHeight() / 4;
		switch (object.getDirection()) {
			case LEFT:
				break;
			case DOWN:
				spriteY = unit;
				break;
			case RIGHT:
				spriteY = 2 * unit;
				break;
			case UP:
				spriteY = 3 * unit;
				break;
		}
		batch.draw(sprite, object.getPosition().x - object.getWidth() / 2f,object.getPosition().y - object.getHeight() / 2f, object.getWidth(), object.getHeight(), spriteX, spriteY, sprite.getWidth() / totalSteps, unit, false, false);
		if(showDirection && object.getDirection() != null){
			batch.draw(arrow,object.getPosition().x - object.getWidth() / 2f,object.getPosition().y - object.getHeight() / 2f,16,8,32,16,1,1,getRotation(),0,0,32,16,false, false);
		}
		if (timePerFrame < timeSinceLastChange) {
			step = (step + 1) % totalSteps;
			timeSinceLastChange = 0;
		}
	}

	private float getRotation() {
		switch (object.getDirection()){
			case UP:
				return 270;
			case DOWN:
				return 90;
			case LEFT:
				return 0;
			case RIGHT:
				return 180;
		}
		return 0;
	}

	public int getTotalSteps() {
		return totalSteps;
	}

	void setTotalSteps(int totalSteps) {
		this.totalSteps = totalSteps;
	}
	void setTimePerFrame(float time){
		timePerFrame = time;
	}

	public void dispose() {
		sprite.dispose();
	}
}
