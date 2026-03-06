package ca.sfu.lastminutelegends.entities;

import ca.sfu.lastminutelegends.board.Board;

/**
 * Represents the main character controlled by the player.
 * Movement rules:
 * - At most one tile per tick
 * - Cannot move outside the board
 * - Cannot move into non-passable cells (walls)
 */

public class Player {
    private Position pos;

    public Player(Position startPos) {
        this.pos = startPos;
    }

    public Position getPos() {
        return pos;
    }

    /**
     * Attempt to move the player one tile in the given direction.
     * If the move is invalid (blocked/outside), the player stays put.
     */
    
    public void tryMove(Direction dir, Board board) {
        if (dir == null) return;

        Position next = pos.move(dir);
        if (isWalkable(board, next)) {
            pos = next;
        }
    }

    private boolean isWalkable(Board board, Position p) {
        if (p.x < 0 || p.y < 0 || p.x >= board.getWidth() || p.y >= board.getHeight()) {
            return false;
        }
        return board.getCell(p.x, p.y).isPassable();
    }
}