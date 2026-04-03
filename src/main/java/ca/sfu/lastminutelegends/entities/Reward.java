package ca.sfu.lastminutelegends.entities;

import java.awt.Graphics;

/**
 * Abstract base class for all rewards in the game
 * Subclasses define point values, collection rules and appearances
 */
public abstract class Reward extends Entity {
    protected int pointValue;
    protected boolean collected;
    
    /**
     * @param position   the grid cell where this reward is placed
     * @param pointValue the number of points awarded on collection
     */
    public Reward(Position position, int pointValue) {
        super(position);
        this.pointValue = pointValue;
        this.collected = false;
    }
    
    public int getPointValue() {
        return pointValue;
    }
    
    public boolean isCollected() {
        return collected;
    }
    
    public void collect() {
        this.collected = true;
    }
    
     /**
     * @param g        the graphics context
     * @param cellSize pixel size of one board cell
     * @param offsetX  horizontal pixel offset of the board origin
     * @param offsetY  vertical pixel offset of the board origin
     */
    @Override
    public abstract void render(Graphics g, int cellSize, int offsetX, int offsetY);
}