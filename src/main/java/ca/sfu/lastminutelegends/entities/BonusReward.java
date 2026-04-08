package ca.sfu.lastminutelegends.entities;

import java.awt.*;
import java.awt.image.BufferedImage;

import ca.sfu.lastminutelegends.board.Board;
import ca.sfu.lastminutelegends.render.RenderContext;
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

    @Override
    public void onCollideWithPlayer() {
        if (isExpired()) return;
            
        super.onCollideWithPlayer();
    }

    public boolean isExpired() {
        return timeToLive <= 0 && !collected;
    }
    
    public float getTimeRemainingPercentage() {
        return (float) timeToLive / maxTTL;
    }
    
    @Override
    protected BufferedImage getTexture() {
        return TEXTURE;
    }

    /**
     * Render the entity on the game canvas
     *
     * @param ctx the render context. Contains cell size, offsets
     */
    @Override
    public void render(RenderContext ctx) {
        if (collected || isExpired()) return;

        Graphics2D g2d = (Graphics2D) ctx.g();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getTimeRemainingPercentage()));

        super.render(ctx);
        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
}