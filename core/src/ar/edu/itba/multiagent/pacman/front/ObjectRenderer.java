package ar.edu.itba.multiagent.pacman.front;

import ar.edu.itba.multiagent.pacman.GameObject;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class ObjectRenderer {
	private GameObject object;
	private Texture sprite;

	private int step = 0;
	private int totalSteps = 2;
	private float timePerFrame = 0.2f;
	private float timeSinceLastChange = 0f;

	ObjectRenderer(GameObject o, Texture t){
		object = o;
		sprite = t;
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
		if (timePerFrame < timeSinceLastChange) {
			step = (step + 1) % totalSteps;
			timeSinceLastChange = 0;
		}
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
