package ca.sfu.lastminutelegends.entities;

import java.util.Objects;

/**
 * Immutable coordinate on the board grid.
 * We use x for column and y for row to match Board.getCell(x, y).
 */

public final class Position {
    public final int x; // column
    public final int y; // row

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** Returns a new position moved by exactly one tile in the given direction. */
    public Position move(Direction d) {
        return new Position(this.x + d.dx, this.y + d.dy);
    }

    /** Manhattan distance is perfect for grid chasing logic (no diagonals). */
    public int manhattanDistance(Position other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position p)) return false;
        return this.x == p.x && this.y == p.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    public int getPixelX(int cellSize, int offsetX) {
        return offsetX + x * cellSize;
    }

    public int getPixelY(int cellSize, int offsetY) {
        return offsetY + y * cellSize;
    }
}