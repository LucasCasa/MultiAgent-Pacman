package ar.edu.itba.multiagent.pacman.states;

import ar.edu.itba.multiagent.pacman.agents.Ghost;
import ar.edu.itba.multiagent.pacman.environment.EnemySighting;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;

import java.util.Optional;
import java.util.Random;

public class ConvergePredictWithForceState extends ConvergeWithForceState {

    @Override
    public void update(Ghost self, float deltaTime, int turn, Random r) {
        Optional<EnemySighting> sight = seek(self, turn);
        Optional<EnemySighting> newPos = sight.map(s -> predictPosition(s, self));
        newPos.ifPresent(pacman -> self.setTarget(pacman.getPosition().cpy()));
        newPos.ifPresent(pacman -> chase(self, pacman));
    }

    private EnemySighting predictPosition(EnemySighting original, Ghost self) {
        if(original.getPosition().dst2(self.getPosition()) > 16*16*3*3) {
            Vector2 newTarget = intercept(original, self);
            if(newTarget == null){
                float timeToArrive = original.getPosition().cpy().sub(self.getPosition()).len() / self.getSpeed();
                newTarget = original.getPosition().cpy().add(multiply(original.getDirection().directionVector(), timeToArrive * original.getSpeed()));
            }
            return new EnemySighting(newTarget, original.getDirection(), original.getSpeed(), original.getTurn());
        }
        return original;
    }

    private Vector2 multiply(GridPoint2 p, float value) {
        return new Vector2(p.x * value, p.y * value);
    }

    private Vector2 intercept(EnemySighting pacman, Ghost self) {

        float tx = pacman.getPosition().x - self.getPosition().x;
        float ty = pacman.getPosition().y - self.getPosition().y;
        float tvx = pacman.getDirection().directionVector().x * pacman.getSpeed();
        float tvy = pacman.getDirection().directionVector().y * pacman.getSpeed();

        // Get quadratic equation components
        float a = tvx*tvx + tvy*tvy - self.getSpeed()*self.getSpeed();
        float b = 2 * (tvx * tx + tvy * ty);
        float c = tx*tx + ty*ty;

        // Solve quadratic
        Vector2 ts = quad(a, b, c); // See quad(), below

        // Find smallest positive solution
        Vector2 sol = null;
        if (ts != null) {
            float t0 = ts.x;
            float t1 = ts.y;
            float t = Math.min(t0, t1);
            if (t < 0)
                t = Math.max(t0, t1);
            else if (t > 0) {
                sol = new Vector2(pacman.getPosition().x + tvx*t, pacman.getPosition().y + tvy*t);
            }
        }
        return sol;
    }


    /**
     * Return solutions for quadratic
     */
    private Vector2 quad(float a, float b, float c) {
        Vector2 sol = null;
        if (Math.abs(a) < 1e-6) {
            if (Math.abs(b) < 1e-6) {
                sol = Math.abs(c) < 1e-6 ? new Vector2(0,0) : null;
            } else {
                sol = new Vector2(-c/b, -c/b);
            }
        } else {
            float disc = b*b - 4*a*c;
            if (disc >= 0) {
                disc = (float) Math.sqrt(disc);
                a = 2*a;
                sol = new Vector2((-b-disc)/a, (-b+disc)/a);
            }
        }
        return sol;
    }
}
