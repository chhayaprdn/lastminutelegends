package ca.sfu.lastminutelegends.entities;

import ca.sfu.lastminutelegends.ui.TextureLoader;

import java.awt.image.BufferedImage;

/**
 * Regular rewards (School Supplies)
 * Must be collected to win the game
 */
public class RegularReward extends Reward {
    private static final BufferedImage TEXTURE;

    static {
        TEXTURE = TextureLoader.load("regularReward.png");
    }

    public RegularReward(Position position) {
        super(position, 10);
    }

    @Override
    protected BufferedImage getTexture() {
        if (isCollected()) {
            return null;
        }
        return TEXTURE;
    }
}
