package ca.sfu.lastminutelegends.entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Punishment extends Entity {
    private static final BufferedImage TEXTURE;

    static {
        try {
            TEXTURE = ImageIO.read(RegularReward.class.getResourceAsStream("/textures/punishment.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Punishment(Position position) {
        super(position);
    }
    
    @Override
    public void render(Graphics g, int cellSize, int offsetX, int offsetY) {
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
