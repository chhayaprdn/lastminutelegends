package ca.sfu.lastminutelegends.entities;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.board.Board;


public abstract class MovingEntity extends Entity {
    public MovingEntity(Position startPos) {
        super(startPos);
    }

    protected static boolean isWalkable(Board board, Position p, boolean isPlayer) {
        if (p.x < 0 || p.y < 0 || p.x >= board.getWidth() || p.y >= board.getHeight()) {
            return false;
        }
        
        if (!isPlayer) {
            for (Entity e : Game.instance().getEntities()) {
                if (e instanceof MovingEnemy || e instanceof Punishment) {
                    if (e.getPosition().equals(p)) {
                        return false;
                    }
                }
            }
        }

        return board.getCell(p.x, p.y).isPassable();
    }
}
