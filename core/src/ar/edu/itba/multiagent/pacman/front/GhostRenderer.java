package ar.edu.itba.multiagent.pacman.front;

import ar.edu.itba.multiagent.pacman.agents.Ghost;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GhostRenderer extends ObjectRenderer {
	private Texture sight;
	private Texture target;
	private boolean showSight;
	private boolean showTarget;
	private Pixmap senseArea;
	private Pixmap targetMap;
	Ghost ghost;

	public GhostRenderer(Ghost ghost, String name, boolean showDirection, boolean showSight, boolean showTarget){
		super(ghost, new Texture("sprites/" + name + "Ghost_Anim.png"), showDirection);
		super.setTotalSteps(2);
		this.ghost = ghost;
		this.showSight = showSight;
		this.showTarget = showTarget;
		int range = ghost.getVisibility()* 16;
		senseArea = new Pixmap(range * 2 + 1,range * 2 + 1, Pixmap.Format.RGBA8888);
		Color c = getGhostColor(name);
		senseArea.setColor(c);
		senseArea.drawCircle(range, range, range);
		targetMap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
		targetMap.setColor(c);
		targetMap.drawLine(0,0,16,16);
		targetMap.drawLine(0,16,16,0);
		sight = new Texture(senseArea);
		target = new Texture(targetMap);

	}

	private Color getGhostColor(String name) {
		switch (name){
			case "red":
				return Color.RED;
			case "pink":
				return Color.PINK;
			case "purple":
				return Color.PURPLE;
			case "orange":
				return Color.ORANGE;
			case "cyan":
				return Color.CYAN;
			case "brown":
				return Color.BROWN;
			case "green":
				return Color.GREEN;
			case "gold":
				return Color.GOLD;
			case "grey":
				return Color.GRAY;
			case "lightGreen":
				return Color.valueOf("0x00ddaa");
		}
		return Color.WHITE;
	}

	@Override
	public void render(SpriteBatch batch, float deltaTime) {
		if(showSight){
			int range = ghost.getVisibility()* 16;
			batch.draw(sight, ghost.getPosition().x - range,ghost.getPosition().y - range, range * 2, range * 2);
		}
		if(showTarget){
			batch.draw(target,ghost.getTarget().x - 8, ghost.getTarget().y - 8, 16, 16);
		}
		super.render(batch, deltaTime);
	}
}
