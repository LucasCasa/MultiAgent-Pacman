package ar.edu.itba.multiagent.pacman.front;

import ar.edu.itba.multiagent.pacman.player.Player;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayerRenderer extends ObjectRenderer{
	int range = 3 * 16;
	Player player;
	Texture sight;
	private Pixmap pixmap;

	public PlayerRenderer(Player player, Texture sprite){
		super(player, sprite);
		this.player = player;
		setTotalSteps(3);
		setTimePerFrame(0.1f);
		pixmap = new Pixmap(range * 2 + 1,range * 2 + 1, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.YELLOW);
		pixmap.drawCircle(range, range, range);
		sight = new Texture(pixmap);
	}

	@Override
	public void render(SpriteBatch batch, float deltaTime) {
		batch.draw(sight, player.getPosition().x - range, player.getPosition().y - range, range * 2, range * 2);
		super.render(batch, deltaTime);
	}
}
