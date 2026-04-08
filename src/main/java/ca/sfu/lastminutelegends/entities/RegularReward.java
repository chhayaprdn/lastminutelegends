package ca.sfu.lastminutelegends.entities;

import ca.sfu.lastminutelegends.ui.TextureLoader;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

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
        super(position, 10); //10 points for regular rewards
    }

    @Override
    protected BufferedImage getTexture() {
        if (collected)
            return null;
        
        return TEXTURE;
    }
}