package ca.sfu.lastminutelegends.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Regular rewards (School Supplies)
 * Must be collected to win the game
 */
public class RegularReward extends Reward {
    private static final int DEFAULT_POINT_VALUE = 10;
    private static final BufferedImage TEXTURE = TextureLoader.loadTexture("/textures/regularReward.png");

    public RegularReward(Position position) {
        super(position, DEFAULT_POINT_VALUE);
    }

    @Override
    public void render(Graphics g, int cellSize, int offsetX, int offsetY) {
        if (isCollected() || TEXTURE == null) return;
        drawTexture(g, TEXTURE, cellSize, offsetX, offsetY);
    }
    
}