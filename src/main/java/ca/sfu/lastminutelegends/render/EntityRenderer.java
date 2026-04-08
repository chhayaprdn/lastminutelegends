package ca.sfu.lastminutelegends.render;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.entities.Entity;

import java.awt.*;

/**
 * Renders the player and enemies on top of the board.
 * BoardRenderer draws tiles; this draws entities as simple circles for now.
 */
public class EntityRenderer implements GameRenderer {
    @Override
    public void render(Graphics g) {
        for (Entity e : Game.instance().getEntities()) {
            e.render(new RenderContext(
                g,
                Game.instance().getCanvas().getCellSize(),
                Game.instance().getCanvas().getBoardOffsetX(),
                Game.instance().getCanvas().getBoardOffsetY()
            ));
        }
    }
}