package ar.edu.itba.multiagent.pacman.states;

import ar.edu.itba.multiagent.pacman.GameObject;
import ar.edu.itba.multiagent.pacman.agents.Ghost;
import ar.edu.itba.multiagent.pacman.environment.EnemySighting;
import ar.edu.itba.multiagent.pacman.environment.PositionUtils;
import com.badlogic.gdx.math.Vector2;
import com.typesafe.config.Config;

import java.util.Optional;
import java.util.Random;

public class ConvergeWithForceState extends SeekPacMan implements State{
    private float ghostMultiplier;
    private float pacmanMultiplier;
    private int minDistance;
    private int maxDistance;

    public ConvergeWithForceState(Config c){
        Config force = c.getConfig("force-params");
        minDistance = force.getInt("min-distance");
        minDistance = 16*16*minDistance*minDistance; //Convert to dst2
        maxDistance = force.getInt("max-distance");
        maxDistance = 16*16*maxDistance*maxDistance;
        ghostMultiplier = (float) force.getDouble("ghost-multiplier");
        pacmanMultiplier = (float) force.getDouble("pacman-multiplier");
    }

    @Override
    public void update(GameObject self, float deltaTime, int turn, Random r) {
        update((Ghost) self, deltaTime, turn, r);
    }

    public void update(Ghost self, float deltaTime, int turn, Random r) {
        Optional<EnemySighting> sight = seek(self, turn);
        sight.ifPresent(pacman -> chase(self, pacman));
    }

    protected void chase(Ghost self, EnemySighting pacman) {
        Vector2 force = new Vector2(0,0);
        self.getOtherGhosts().values().forEach(ghost -> {
            if(self.getPosition().dst2(ghost) < maxDistance && self.getPosition().dst2(pacman.getPosition()) > minDistance) {
                force.add(getForce(self.getPosition(), ghost, -1 * ghostMultiplier));
            }
        });
        boolean affected = force.x == 0 && force.y == 0;
        force.add(getForce(self.getPosition(), pacman.getPosition(), pacmanMultiplier));
        self.tryToChangeDirection(PositionUtils.forceVectorToDirection(force, self.getValidDirections()));
    }

    private Vector2 getForce(Vector2 position, Vector2 ghost, float multiplier) {
        Vector2 dist = new Vector2(ghost.x - position.x, ghost.y - position.y);
        return dist.scl(1 / (dist.x * dist.x + dist.y * dist.y)).scl(multiplier);
    }
}
