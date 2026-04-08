package ca.sfu.lastminutelegends.systems;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.board.*;
import ca.sfu.lastminutelegends.ui.TextureLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BoardRenderer implements GameSystem {
    private static final BufferedImage WALL_TEXTURE;
    private static final BufferedImage FLOOR_TEXTURE;
    private static final BufferedImage DOOR_TEXTURE;
    
    static {
        WALL_TEXTURE = TextureLoader.load("wall.png");
        FLOOR_TEXTURE = TextureLoader.load("floor.png");
        DOOR_TEXTURE = TextureLoader.load("door.png");
    }
    
    @Override
    public void tick(int tick) {
        
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
