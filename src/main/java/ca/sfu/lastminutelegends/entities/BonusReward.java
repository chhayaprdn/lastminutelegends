package ca.sfu.lastminutelegends.entities;

import java.awt.*;
import java.awt.image.BufferedImage;

import ca.sfu.lastminutelegends.board.Board;
import ca.sfu.lastminutelegends.ui.TextureLoader;

/**
* A bonus reward (coffee) that spawns randomly and disappears after a fixed number
 * of ticks if not collected. Worth 50 points by default.
 */
public class BonusReward extends Reward {
    private static final BufferedImage TEXTURE;

    static {
        TEXTURE = TextureLoader.load("bonusReward.png");
    }
    
    private int timeToLive;
    private final int maxTTL;
    
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
    
    public float getTimeRemainingPercentage() {
        return (float) timeToLive / maxTTL;
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

        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getTimeRemainingPercentage()));

        g2d.drawImage(
            TEXTURE,
            offsetX + position.x * cellSize,
            offsetY + position.y * cellSize,
            cellSize,
            cellSize,
            null
        );

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
}