package ca.sfu.lastminutelegends.systems;

import ca.sfu.lastminutelegends.entities.MovingEnemy;
import ca.sfu.lastminutelegends.entities.Player;

import java.awt.*;
import java.util.List;

/**
 * Renders the player and enemies on top of the board.
 * BoardRenderer draws tiles; this draws entities as simple circles for now.
 */
public class EntityRenderer implements GameSystem {

    private final Player player;
    private final List<MovingEnemy> enemies;

    // Must match BoardRenderer's tile and offsets
    private static final int TILE = 50;
    private static final int OFFSET_X = 50;
    private static final int OFFSET_Y = 50;

    public EntityRenderer(Player player, List<MovingEnemy> enemies) {
        this.player = player;
        this.enemies = enemies;
    }

    @Override
    public void tick(int tick) {
        // No update logic; purely visual.
    }

    @Override
    public void render(Graphics g) {
        // Player (red)
        g.setColor(Color.RED);
        g.fillOval(
                OFFSET_X + player.getPos().x * TILE + 10,
                OFFSET_Y + player.getPos().y * TILE + 10,
                TILE - 20,
                TILE - 20
        );

        // Enemies (orange)
        g.setColor(Color.ORANGE);
        for (MovingEnemy e : enemies) {
            g.fillOval(
                    OFFSET_X + e.getPos().x * TILE + 10,
                    OFFSET_Y + e.getPos().y * TILE + 10,
                    TILE - 20,
                    TILE - 20
            );
        }
    }
}