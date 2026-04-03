package ca.sfu.lastminutelegends.systems;

import ca.sfu.lastminutelegends.entities.Direction;
import org.junit.jupiter.api.Test;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

public class InputSystemTest {

    @Test
    void testKeyPressUp() {
        JPanel panel = new JPanel();
        InputSystem input = new InputSystem(panel);

        KeyEvent event = new KeyEvent(panel, KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, KeyEvent.VK_UP, ' ');

        for (var listener : panel.getKeyListeners()) {
            listener.keyPressed(event);
        }

        assertEquals(Direction.UP, input.consumeDirection());
    }

    @Test
    void testDownKey() {
        JPanel panel = new JPanel();
        InputSystem input = new InputSystem(panel);

        KeyEvent e = new KeyEvent(panel, KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, ' ');

        for (var l : panel.getKeyListeners()) {
            l.keyPressed(e);
        }

        assertEquals(Direction.DOWN, input.consumeDirection());
    }

    @Test
    void testLeftKey() {
        JPanel panel = new JPanel();
        InputSystem input = new InputSystem(panel);

        KeyEvent e = new KeyEvent(panel, KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, ' ');

        for (var l : panel.getKeyListeners()) {
            l.keyPressed(e);
        }

        assertEquals(Direction.LEFT, input.consumeDirection());
    }

    @Test
    void testRightKey() {
        JPanel panel = new JPanel();
        InputSystem input = new InputSystem(panel);

        KeyEvent e = new KeyEvent(panel, KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, ' ');

        for (var l : panel.getKeyListeners()) {
            l.keyPressed(e);
        }

        assertEquals(Direction.RIGHT, input.consumeDirection());
    }

    @Test
    void testWKey() {
        JPanel panel = new JPanel();
        InputSystem input = new InputSystem(panel);

        KeyEvent e = new KeyEvent(panel, KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, KeyEvent.VK_W, ' ');

        for (var l : panel.getKeyListeners()) {
            l.keyPressed(e);
        }

        assertEquals(Direction.UP, input.consumeDirection());
    }

    @Test
    void testAKey() {
        JPanel panel = new JPanel();
        InputSystem input = new InputSystem(panel);

        KeyEvent e = new KeyEvent(panel, KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, KeyEvent.VK_A, ' ');

        for (var l : panel.getKeyListeners()) {
            l.keyPressed(e);
        }

        assertEquals(Direction.LEFT, input.consumeDirection());
    }

    @Test
    void testSKey() {
        JPanel panel = new JPanel();
        InputSystem input = new InputSystem(panel);

        KeyEvent e = new KeyEvent(panel, KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, KeyEvent.VK_S, ' ');

        for (var l : panel.getKeyListeners()) {
            l.keyPressed(e);
        }

        assertEquals(Direction.DOWN, input.consumeDirection());
    }

    @Test
    void testDKey() {
        JPanel panel = new JPanel();
        InputSystem input = new InputSystem(panel);

        KeyEvent e = new KeyEvent(panel, KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, KeyEvent.VK_D, ' ');

        for (var l : panel.getKeyListeners()) {
            l.keyPressed(e);
        }

        assertEquals(Direction.RIGHT, input.consumeDirection());
    }

    @Test
    void testConsumeDirectionClears() {
        JPanel panel = new JPanel();
        InputSystem input = new InputSystem(panel);

        KeyEvent event = new KeyEvent(panel, KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, ' ');

        for (var l : panel.getKeyListeners()) {
            l.keyPressed(event);
        }

        input.consumeDirection();
        assertNull(input.consumeDirection());
    }

    @Test
    void testInvalidKeyDoesNothing() {
        JPanel panel = new JPanel();
        InputSystem input = new InputSystem(panel);

        KeyEvent event = new KeyEvent(panel, KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, KeyEvent.VK_SHIFT, ' ');

        for (var listener : panel.getKeyListeners()) {
            listener.keyPressed(event);
        }

        assertNull(input.consumeDirection());
    }

    @Test
    void testTickDoesNotCrash() {
        JPanel panel = new JPanel();
        InputSystem input = new InputSystem(panel);

        input.tick(1);

        assertNull(input.consumeDirection());
    }

    @Test
    void testRenderDoesNotCrash() {
        JPanel panel = new JPanel();
        InputSystem input = new InputSystem(panel);

        Graphics g = panel.getGraphics();

        input.render(g);

        assertTrue(true);
    }
}