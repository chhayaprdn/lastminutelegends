package ca.sfu.lastminutelegends.board;

/**
 * A cell that contains a regular reward (School Supplies)
 */
public class RewardCell implements Cell {
    private boolean collected;
    private final int pointValue;
    
    public RewardCell() {
        this.collected = false;
        this.pointValue = 10; // 10 points for regular rewards
    }
    
    @Override
    public boolean isPassable() {
        return true; // Can walk onto reward cells
    }
    
    public boolean isCollected() {
        return collected;
    }
    
    public void setCollected(boolean collected) {
        this.collected = collected;
    }
    
    public int getPointValue() {
        return pointValue;
    }
}
