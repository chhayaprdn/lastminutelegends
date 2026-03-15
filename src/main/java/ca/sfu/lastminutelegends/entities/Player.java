package ca.sfu.lastminutelegends.entities;

import ca.sfu.lastminutelegends.board.Board;

import java.awt.*;

/**
 * Represents the main character controlled by the player.
 * Movement rules:
 * - At most one tile per tick
 * - Cannot move outside the board
 * - Cannot move into non-passable cells (walls)
 */

public class Player extends MovingEntity {
    public Player(Position startPos) {
        super(startPos);
    }

    /**
     * Attempt to move the player one tile in the given direction.
     * If the move is invalid (blocked/outside), the player stays put.
     */
    public void tryMove(Direction dir, Board board) {
        if (dir == null) return;

        Position next = position.move(dir);
        if (isWalkable(board, next)) {
            position = next;
        }
    }

    @Override
    public void render(Graphics g, int cellSize, int offsetX, int offsetY) {
        g.setColor(Color.RED);
        g.fillOval(
                offsetX + position.x * cellSize + 10,
                offsetY + position.y * cellSize + 10,
                cellSize - 20,
                cellSize - 20
        );
    }
}