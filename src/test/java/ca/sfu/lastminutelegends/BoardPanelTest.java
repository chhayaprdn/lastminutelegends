package ca.sfu.lastminutelegends;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class BoardPanelTest {

    @Test
    void testPanelCreationAndRender() {
        GameBoard board = new GameBoard();

        BoardPanel panel = new BoardPanel(board);

        assertNotNull(panel);

        BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB); 
        Graphics g = image.getGraphics();
        panel.paintComponent(g);

        assertEquals(Color.YELLOW, new Color(image.getRGB(2 * 50 + 10 + 15, 10 + 15)));
    }
}