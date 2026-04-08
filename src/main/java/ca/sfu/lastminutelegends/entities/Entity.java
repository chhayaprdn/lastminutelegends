package ca.sfu.lastminutelegends.entities;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.board.Board;

import java.awt.*;
import java.awt.image.BufferedImage;

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
    
    public void onCollideWithPlayer() {}

    /**
     * Deletes this entity at the end of the tick
     */
    public void markForDeletion() {
        Game.instance().getMarkedEntities().add(this);
    }
    
    protected abstract BufferedImage getTexture();
    
    /**
     * Render the entity on the game canvas
     * 
     * @param g        the graphics context
     * @param cellSize pixel size of one board cell
     * @param offsetX  horizontal pixel offset of the board origin
     * @param offsetY  vertical pixel offset of the board origin
     */
    public void render(Graphics g, int cellSize, int offsetX, int offsetY) {
        if (getTexture() == null)
            return;
        
        g.drawImage(
            getTexture(),
            offsetX + position.x * cellSize,
            offsetY + position.y * cellSize,
            cellSize,
            cellSize,
            null
        );
    }
}