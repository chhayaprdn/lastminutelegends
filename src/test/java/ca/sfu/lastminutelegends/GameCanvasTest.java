package ca.sfu.lastminutelegends;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class GameCanvasTest {
    
    private BufferedImage image;
    private Graphics g;
    private GameCanvas canvas;
    
    @BeforeEach
    void setup() {
        image = new BufferedImage(1600, 900, BufferedImage.TYPE_INT_RGB);
        g = image.getGraphics();
        canvas = new GameCanvas();
        canvas.setSize(1600, 900);
        Game.instance().setCanvas(canvas);
    }
    
    @AfterEach 
    void takedown() {
        g.dispose();
    }

    @Test
    void rendersMainMenuScreenWhenMenuState() {
        Game.instance().setState(GameState.Menu);

        canvas.paintComponent(g);

        assertEquals(Color.RED, new Color(image.getRGB(800, 600)));
    }
    
    @Test
    void rendersWinScreenWhenWonState() {
        Game.instance().setState(GameState.Won);
        
        canvas.paintComponent(g);
        
        assertEquals(Color.LIGHT_GRAY, new Color(image.getRGB(800, 600)));
    }

    @Test
    void rendersLossScreenWhenLostState() {
        Game.instance().setState(GameState.Lost);

        canvas.paintComponent(g);

        assertEquals(Color.BLACK, new Color(image.getRGB(800, 600)));
    }
    
}
