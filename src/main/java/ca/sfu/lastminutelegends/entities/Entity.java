package ca.sfu.lastminutelegends.entities;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.board.Board;
import ca.sfu.lastminutelegends.render.RenderContext;

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
     * @param ctx the render context. Contains cell size, offsets
     */
    public void render(RenderContext ctx) {
        if (getTexture() == null)
            return;
        
        ctx.g().drawImage(
            getTexture(),
            ctx.offsetX() + position.x * ctx.cellSize(),
            ctx.offsetY() + position.y * ctx.cellSize(),
            ctx.cellSize(),
            ctx.cellSize(),
            null
        );
    }
}