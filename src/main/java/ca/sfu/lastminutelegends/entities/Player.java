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
        if (board.isWalkable(next)) {
            pos = next;
        }
    }

}