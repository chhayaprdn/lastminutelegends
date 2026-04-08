package ca.sfu.lastminutelegends.entities;

import ca.sfu.lastminutelegends.Game;

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

    @Override
    public void onCollideWithPlayer() {
        super.onCollideWithPlayer();
        
        if (collected) return;
        
        collect();
        Game.instance().addScore(pointValue);
        markForDeletion();
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
    
}