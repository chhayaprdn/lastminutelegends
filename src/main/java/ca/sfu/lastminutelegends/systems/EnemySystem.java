package ca.sfu.lastminutelegends.systems;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.entities.Entity;
import ca.sfu.lastminutelegends.entities.MovingEnemy;

import java.awt.*;

/**
 * Moves all enemies once per tick.
 * For Milestone 1, enemies use simple Manhattan-distance chasing.
 */
public class EnemySystem implements GameSystem {
    @Override
    public void tick(int tick) {
        if (tick % 5 != 0)
            return;
        
        for (Entity e : Game.instance().getEntities()) {
            if (e instanceof MovingEnemy) {
                e.onTick(Game.instance().getBoard(), Game.instance().getPlayer());
            }
        }
    }

    @Override
    public void render(Graphics g) {
        // Rendering is handled by EntityRenderer.
    }
}