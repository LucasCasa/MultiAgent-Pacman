package ar.edu.itba.multiagent.pacman.states;

import ar.edu.itba.multiagent.pacman.agents.Ghost;
import ar.edu.itba.multiagent.pacman.environment.EnemySighting;

import java.util.Optional;

public abstract class SeekPacMan {

    public Optional<EnemySighting> seek(Ghost self, int turn) {
        EnemySighting p = self.getWorld().pollBlackBoard();
        if (turn - p.getTurn() < 1000) {
            if (p.getPosition().dst(self.getPosition()) < 10) {
                self.getWorld().writeBlackBoard(null);
                self.tryToChangeDirection(p.getDirection());
                return Optional.empty();
            }
            return Optional.of(p);
        }
        return Optional.empty();
    }
}
