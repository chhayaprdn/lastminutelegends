package ca.sfu.lastminutelegends.entities;

import ca.sfu.lastminutelegends.board.Board;

import java.awt.*;

public abstract class MovingEntity extends Entity {
    public MovingEntity(Position startPos) {
        super(startPos);
    }

    protected static boolean isWalkable(Board board, Position p) {
        if (p.x < 0 || p.y < 0 || p.x >= board.getWidth() || p.y >= board.getHeight()) {
            return false;
        }

        return board.getCell(p.x, p.y).isPassable();
    }
}
