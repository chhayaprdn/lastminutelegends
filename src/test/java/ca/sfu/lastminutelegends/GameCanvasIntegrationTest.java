package ca.sfu.lastminutelegends;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class GameCanvasIntegrationTest {
    @Test
    void rendersGameScreenWhenPlayingState() {
        BufferedImage image = new BufferedImage(1600, 900, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        GameCanvas canvas = new GameCanvas();
        canvas.setSize(1600, 900);
        Game.instance().setCanvas(canvas);
        
        Game.instance().loadBoard();
        Game.instance().loadSystems();
        Game.instance().setState(GameState.Playing);
        
        canvas.paintComponent(g);

        assertNotEquals(Color.RED, new Color(image.getRGB(800, 600)));
        assertNotEquals(Color.LIGHT_GRAY, new Color(image.getRGB(800, 600)));
        assertNotEquals(Color.BLACK, new Color(image.getRGB(800, 600)));

        g.dispose();
    }
}
