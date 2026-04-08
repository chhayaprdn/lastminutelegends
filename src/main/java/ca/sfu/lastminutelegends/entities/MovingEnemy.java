package ca.sfu.lastminutelegends.entities;

import ca.sfu.lastminutelegends.board.Board;
import ca.sfu.lastminutelegends.ui.TextureLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

/**
 * A simple chasing enemy.
 * Each tick, it moves one tile to reduce Manhattan distance to the player.
 * If multiple moves are equally good, a fixed priority order is used
 * for deterministic behavior (helps debugging and avoids randomness).
 */
public class MovingEnemy extends MovingEntity {
    private static final BufferedImage TEXTURE;
    
    // Tie-break priority order for consistent results
    private static final List<Direction> PRIORITY =
            Arrays.asList(Direction.UP, Direction.LEFT, Direction.DOWN, Direction.RIGHT);

    static {
        TEXTURE = TextureLoader.load("enemy.png");
    }
    
    public MovingEnemy(Position startPos) {
        super(startPos);
    }

    @Override
    public void onTick(Board board, Player player) {
        super.onTick(board, player);

        Direction best = null;
        int bestDist = Integer.MAX_VALUE;

        for (Direction d : PRIORITY) {
            Position next = position.move(d);
            if (!isWalkable(board, next, false)) continue;

            int dist = next.manhattanDistance(player.getPosition());
            if (dist < bestDist) {
                bestDist = dist;
                best = d;
            }
        }

        if (best != null) {
            position = position.move(best);
        }
    }

    @Override
    protected BufferedImage getTexture() {
        return TEXTURE;
    }
}