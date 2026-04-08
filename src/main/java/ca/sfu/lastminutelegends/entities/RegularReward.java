package ca.sfu.lastminutelegends.entities;

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
        try {
            TEXTURE = ImageIO.read(RegularReward.class.getResourceAsStream("/textures/regularReward.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public RegularReward(Position position) {
        super(position, 10); //10 points for regular rewards
    }
    
    @Override
    public void render(Graphics g, int cellSize, int offsetX, int offsetY) {
        if (collected) return;

        g.drawImage(
            TEXTURE,
            offsetX + position.x * cellSize,
            offsetY + position.y * cellSize,
            cellSize,
            cellSize,
            null
        );
    }
    
}