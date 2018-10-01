package ar.edu.itba.multiagent.pacman.front;

import ar.edu.itba.multiagent.pacman.agents.Ghost;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GhostRenderer extends ObjectRenderer {
	private Texture sight;
	private boolean showSight;
	private Pixmap pixmap;
	Ghost ghost;
	public GhostRenderer(Ghost ghost, String name, boolean showDirection, boolean showSight){
		super(ghost, new Texture("sprites/" + name + "Ghost_Anim.png"), showDirection);
		super.setTotalSteps(2);
		this.ghost = ghost;
		this.showSight = showSight;
		//sight = new Texture("sprites/seeField.png");
		int range = ghost.getVisibility()* 16;
		pixmap = new Pixmap(range * 2 + 1,range * 2 + 1, Pixmap.Format.RGBA8888);
		Color c = getGhostColor(name);
		pixmap.setColor(c);
		pixmap.drawCircle(range, range, range);
		sight = new Texture(pixmap);

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
		super.render(batch, deltaTime);
	}
}
