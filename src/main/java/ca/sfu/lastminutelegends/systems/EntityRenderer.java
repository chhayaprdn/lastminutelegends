package ca.sfu.lastminutelegends.systems;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.entities.Entity;

import java.awt.*;

/**
 * Renders the player and enemies on top of the board.
 * BoardRenderer draws tiles; this draws entities as simple circles for now.
 */
public class EntityRenderer implements GameSystem {
    // Must match BoardRenderer's tile and offsets
    private static final int TILE = 50;
    private static final int OFFSET_X = 50;
    private static final int OFFSET_Y = 50;

    @Override
    public void tick(int tick) {
        // No update logic; purely visual.
    }

    @Override
    public void render(Graphics g) {
        for (Entity e : Game.instance().getEntities()) {
            e.render(g, TILE, OFFSET_X, OFFSET_Y);
        }
    }
}