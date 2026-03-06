package ca.sfu.lastminutelegends.entities;

import java.awt.*;

/**
 * Abstract base class for all game entities.
 * Player, enemies, and rewards should extend this.
 */
public abstract class Entity {
    protected Position position;
    protected String symbol;
    
    public Entity(Position position, String symbol) {
        this.position = position;
        this.symbol = symbol;
    }
    
    public Position getPosition() {
        return position;
    }
    
    public void setPosition(Position position) {
        this.position = position;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    /**
     * Called every game tick for entity behavior
     */
    public void onTick() {
        // Default implementation does nothing
    }
    
    /**
     * Render the entity on the game canvas
     */
    public abstract void render(Graphics g, int cellSize, int offsetX, int offsetY);
    
    /**
     * Check if this entity can be collected/removed
     */
    public boolean isCollectible() {
        return false;
    }
}