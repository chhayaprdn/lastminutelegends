package ca.sfu.lastminutelegends.entities;

/**
 * A simple direction enum for grid movement.
 * Each direction has a delta (dx, dy) representing one-tile movement.
 */

public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    public final int dx;
    public final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }
}