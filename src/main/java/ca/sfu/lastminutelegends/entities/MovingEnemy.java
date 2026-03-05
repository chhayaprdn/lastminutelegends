package ca.sfu.lastminutelegends.entities;

import ca.sfu.lastminutelegends.board.Board;

import java.util.Arrays;
import java.util.List;

/**
 * A simple chasing enemy.
 * Each tick, it moves one tile to reduce Manhattan distance to the player.
 * If multiple moves are equally good, a fixed priority order is used
 * for deterministic behavior (helps debugging and avoids randomness).
 */
public class MovingEnemy {
    private Position pos;

    // Tie-break priority order for consistent results
    private static final List<Direction> PRIORITY =
            Arrays.asList(Direction.UP, Direction.LEFT, Direction.DOWN, Direction.RIGHT);

    public MovingEnemy(Position startPos) {
        this.pos = startPos;
    }

    public Position getPos() {
        return pos;
    }

    /** Move one tile toward the player if any valid move exists. */
    public void moveOneTick(Position playerPos, Board board) {
        Direction best = null;
        int bestDist = Integer.MAX_VALUE;

        for (Direction d : PRIORITY) {
            Position next = pos.move(d);
            if (!isWalkable(board, next)) continue;

            int dist = next.manhattanDistance(playerPos);
            if (dist < bestDist) {
                bestDist = dist;
                best = d;
            }
        }

        if (best != null) {
            pos = pos.move(best);
        }
        // If no valid move, enemy stays in place (e.g., cornered by walls).
    }

    private boolean isWalkable(Board board, Position p) {
        if (p.x < 0 || p.y < 0 || p.x >= board.getWidth() || p.y >= board.getHeight()) {
            return false;
        }
        return board.getCell(p.x, p.y).isPassable();
    }
}