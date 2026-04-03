package ca.sfu.lastminutelegends;

import org.junit.jupiter.api.Test;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class BoardPanelTest {

    @Test
    void testPanelCreationAndRender() {
        GameBoard board = new GameBoard();

        BoardPanel panel = new BoardPanel(board);

        assertNotNull(panel);

        Graphics g = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB).getGraphics();
        panel.paint(g);

        assertTrue(true);
    }
}