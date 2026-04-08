package ca.sfu.lastminutelegends.render;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.board.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BoardRenderer implements GameRenderer {
    private static final BufferedImage WALL_TEXTURE;
    private static final BufferedImage FLOOR_TEXTURE;
    private static final BufferedImage DOOR_TEXTURE;
    
    static {
        try {
            WALL_TEXTURE = ImageIO.read(BoardRenderer.class.getResourceAsStream("/textures/wall.png"));
            FLOOR_TEXTURE = ImageIO.read(BoardRenderer.class.getResourceAsStream("/textures/floor.png"));
            DOOR_TEXTURE = ImageIO.read(BoardRenderer.class.getResourceAsStream("/textures/door.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void render(Graphics g) {
        for (int y = 0; y < Game.instance().getBoard().getHeight(); y++) {
            for (int x = 0; x < Game.instance().getBoard().getWidth(); x++) {
                Cell cell = Game.instance().getBoard().getCell(x, y);

                int xPos = Game.instance().getCanvas().getBoardOffsetX() + x * Game.instance().getCanvas().getCellSize();
                int yPos = Game.instance().getCanvas().getBoardOffsetY() + y * Game.instance().getCanvas().getCellSize();
                int size = Game.instance().getCanvas().getCellSize();

                BufferedImage texture;
                
                switch (cell) {
                    case Wall _ -> texture = WALL_TEXTURE;
                    case EmptyCell _, StartPoint _ -> texture = FLOOR_TEXTURE;
                    case EndPoint _ -> texture = DOOR_TEXTURE;
                    case null, default -> texture = null;
                }

                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(xPos, yPos, size, size);
                if (texture != null)
                    g.drawImage(texture, xPos, yPos, size, size, null);

            }
        }
    }
}
