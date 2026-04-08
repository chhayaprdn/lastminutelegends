package ca.sfu.lastminutelegends.entities;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import ca.sfu.lastminutelegends.board.Board;
import ca.sfu.lastminutelegends.render.RenderContext;
import ca.sfu.lastminutelegends.ui.TextureLoader;

/**
 * A bonus reward (coffee) that spawns randomly and disappears after a fixed number
 * of ticks if not collected.
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
        if (ttl <= 0) {
            throw new IllegalArgumentException("ttl must be positive");
        }
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
        if (!isCollected() && timeToLive > 0) {
            timeToLive--;
        }
    }

    @Override
    public void onCollideWithPlayer() {
        if (isExpired()) return;
            
        super.onCollideWithPlayer();
    }

    public boolean isExpired() {
        return timeToLive <= 0 && !isCollected();
    }
    
    public float getTimeRemainingPercentage() {
        return (float) timeToLive / maxTTL;
    }
    
    @Override
    protected BufferedImage getTexture() {
        return TEXTURE;
    }

    /**
     * Renders the reward texture with alpha tied to remaining time.
     * Nothing is drawn if collected, expired, or the texture failed to load.
     *
     * @param ctx the render context. Contains cell size, offsets
     */
    @Override
    public void render(RenderContext ctx) {
        if (isCollected() || isExpired() || TEXTURE == null) return;

        Graphics g = ctx.g();
        if (!(g instanceof Graphics2D g2d)) return;

        Composite saved = g2d.getComposite();
        try {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getTimeRemainingPercentage()));
            super.render(ctx);
        } finally {
            g2d.setComposite(saved);
        }
    }
}
