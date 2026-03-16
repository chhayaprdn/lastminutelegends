package ca.sfu.lastminutelegends.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import ca.sfu.lastminutelegends.board.Board;

/**
* A bonus reward (coffee) that spawns randomly and disappears after a fixed number
 * of ticks if not collected. Worth 50 points by default.
 */
public class BonusReward extends Reward {
    private int timeToLive;
    private final int maxTTL;
    
    /**
     * Creates a bonus reward with default values (50 points, 10 tick TTL).
     *
     * @param position the grid cell where this reward appears
     */
    public BonusReward(Position position) {
        super(position, 50); // 50 points for bonus rewards
        this.maxTTL = 10; // 10 ticks before disappearing
        this.timeToLive = this.maxTTL;
    }
    
    /**
     * @param position   the grid cell where this reward appears
     * @param pointValue points awarded on collection
     * @param ttl        ticks before the reward expires
     */
    public BonusReward(Position position, int pointValue, int ttl) {
        super(position, pointValue);
        this.maxTTL = ttl;
        this.timeToLive = ttl;
    }
    
    /**
     * Decrements the TTL counter by one tick. No effect if already collected.
     *
     * @param board  unused; present for interface compatibility
     * @param player unused; present for interface compatibility
     */
    @Override
    public void onTick(Board board, Player player) {
        if (!collected) {
            timeToLive--;
        }
    }
    
    public boolean isExpired() {
        return timeToLive <= 0 && !collected;
    }
    
    public int getTimeToLive() {
        return timeToLive;
    }
    
    public int getMaxTTL() {
        return maxTTL;
    }
    
    public double getTimeRemainingPercentage() {
        return (double) timeToLive / maxTTL;
    }
    /**
     * Renders the reward as a fading coffee-brown square with a countdown.
     * Nothing is drawn if collected or expired.
     *
     * @param g        the graphics context
     * @param cellSize pixel size of one board cell
     * @param offsetX  horizontal pixel offset of the board origin
     * @param offsetY  vertical pixel offset of the board origin
     */
    @Override
    public void render(Graphics g, int cellSize, int offsetX, int offsetY) {
        if (collected || isExpired()) return;
        
        // Use position.x and position.y (not getX()/getY())
        int x = offsetX + position.x * cellSize + cellSize / 4;
        int y = offsetY + position.y * cellSize + cellSize / 4;
        int size = cellSize / 2;
        
        // Coffee brown with transparency based on time left
        int alpha = (int) (255 * getTimeRemainingPercentage());
        if (alpha < 50) alpha = 50;
        
        // Rich coffee brown
        g.setColor(new Color(101, 67, 33, alpha));
        g.fillRect(x, y, size, size);
        
        // Draw coffee symbol
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.drawString("☕", x + size/4, y + size/2);
        
        // Draw countdown
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(timeToLive), x + size/2, y - 2);
    }
}