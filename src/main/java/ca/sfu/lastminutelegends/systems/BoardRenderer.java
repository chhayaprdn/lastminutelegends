package ca.sfu.lastminutelegends.systems;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.board.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BoardRenderer implements GameSystem {
    private BufferedImage wallTexture;
    private BufferedImage floorTexture;
    
    public BoardRenderer() {
        try {
            wallTexture = ImageIO.read(getClass().getResourceAsStream("/textures/wall.png"));
            floorTexture = ImageIO.read(getClass().getResourceAsStream("/textures/floor.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void tick(int tick) {
        
    }

    @Override
    public void render(Graphics g) {
        for (int y = 0; y < Game.instance().getBoard().getHeight(); y++) {
            for (int x = 0; x < Game.instance().getBoard().getWidth(); x++) {
                Cell cell = Game.instance().getBoard().getCell(x, y);

                if (cell instanceof Wall) {
                    g.drawImage(wallTexture, 50 + x * 50, 50 + y * 50, 50, 50, null);
                    continue;
                }
                
                if (cell instanceof EmptyCell) {
                    g.drawImage(floorTexture, 50 + x * 50, 50 + y * 50, 50, 50, null);
                    continue;
                }
                
                switch (cell) {
                    case StartPoint _ -> g.setColor(Color.BLUE);
                    case EndPoint _ -> g.setColor(Color.GREEN);
                    case null, default -> g.setColor(Color.LIGHT_GRAY);
                }
                
                g.fillRect(50 + x * 50, 50 + y * 50, 50, 50);
            }
        }
    }
}
