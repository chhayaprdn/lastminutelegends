package ca.sfu.lastminutelegends.entities;

import ca.sfu.lastminutelegends.board.Board;

import java.awt.*;

/**
 * Abstract base class for all game entities.
 * Player, enemies, and rewards should extend this.
 */
public abstract class Entity {
    protected Position position;
    
    public Entity(Position position) {
        this.position = position;
    }
    
    public Position getPosition() {
        return position;
    }
    
    public void setPosition(Position position) {
        this.position = position;
    }
    
    /**
     * Called every game tick for entity behavior
     */
    public void onTick(Board board, Player player) {
        // Default implementation does nothing
    }
    
    /**
     * Render the entity on the game canvas
     */
    public abstract void render(Graphics g, int cellSize, int offsetX, int offsetY);
}