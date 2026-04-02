package ca.sfu.lastminutelegends.entities;

import ca.sfu.lastminutelegends.ui.TextureLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Punishment extends Entity {
    private static final BufferedImage TEXTURE;

    static {
        TEXTURE = TextureLoader.load("punishment.png");
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
