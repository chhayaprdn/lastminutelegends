package ca.sfu.lastminutelegends.entities;

import java.awt.*;

/**
 * Base class for all rewards in the game
 */
public abstract class Reward extends Entity {
    protected int pointValue;
    protected boolean collected;
    
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
    
    @Override
    public abstract void render(Graphics g, int cellSize, int offsetX, int offsetY);
}