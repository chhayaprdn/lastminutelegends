package ca.sfu.lastminutelegends.systems;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.GameState;
import ca.sfu.lastminutelegends.TestUtils;
import ca.sfu.lastminutelegends.entities.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

public class InputSystemTest {

    private static JPanel panel;
    private static InputSystem input;
    
    @BeforeEach
    void setup() {
        panel = new JPanel();
        input = new InputSystem(panel);
    }
    
    private void simulateKeyPress(int key) {
        KeyEvent event = new KeyEvent(panel, KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, key, ' ');

        for (var listener : panel.getKeyListeners()) {
            listener.keyPressed(event);
        }
    }
    
    @Test
    void testKeyPressUp() {
        simulateKeyPress(KeyEvent.VK_UP);

        assertEquals(Direction.UP, input.consumeDirection());
    }

    @Test
    void testDownKey() {
        simulateKeyPress(KeyEvent.VK_DOWN);

        assertEquals(Direction.DOWN, input.consumeDirection());
    }

    @Test
    void testLeftKey() {
        simulateKeyPress(KeyEvent.VK_LEFT);

        assertEquals(Direction.LEFT, input.consumeDirection());
    }

    @Test
    void testRightKey() {
        simulateKeyPress(KeyEvent.VK_RIGHT);

        assertEquals(Direction.RIGHT, input.consumeDirection());
    }

    @Test
    void testWKey() {
        simulateKeyPress(KeyEvent.VK_W);

        assertEquals(Direction.UP, input.consumeDirection());
    }

    @Test
    void testAKey() {
        simulateKeyPress(KeyEvent.VK_A);

        assertEquals(Direction.LEFT, input.consumeDirection());
    }

    @Test
    void testSKey() {
        simulateKeyPress(KeyEvent.VK_S);

        assertEquals(Direction.DOWN, input.consumeDirection());
    }

    @Test
    void testDKey() {
        simulateKeyPress(KeyEvent.VK_D);

        assertEquals(Direction.RIGHT, input.consumeDirection());
    }
    
    @Test
    void testEnterKeyChangesGameStateToPlaying() throws NoSuchFieldException, IllegalAccessException {
        TestUtils.resetGameInstance();

        assertEquals(GameState.Menu, Game.instance().getState());
        
        simulateKeyPress(KeyEvent.VK_ENTER);
        assertEquals(GameState.Playing, Game.instance().getState());
    }

    @Test
    void testEnterKeyDoesNotChangeGameStateWhenNotMenu() throws NoSuchFieldException, IllegalAccessException {
        TestUtils.resetGameInstance();

        Game.instance().setState(GameState.Won);
        simulateKeyPress(KeyEvent.VK_ENTER);
        assertEquals(GameState.Won, Game.instance().getState());
        
        Game.instance().setState(GameState.Lost);
        simulateKeyPress(KeyEvent.VK_ENTER);
        assertEquals(GameState.Lost, Game.instance().getState());
    }

    @Test
    void testConsumeDirectionClears() {
        simulateKeyPress(KeyEvent.VK_DOWN);

        input.consumeDirection();
        assertNull(input.consumeDirection());
    }

    @Test
    void testInvalidKeyDoesNothing() {
        simulateKeyPress(KeyEvent.VK_SHIFT);
        
        assertNull(input.consumeDirection());
    }

    @Test
    void testTickDoesNotCrash() {
        input.tick(1);

        assertNull(input.consumeDirection());
    }

    @Test
    void testRenderDoesNotCrash() {
        Graphics g = panel.getGraphics();

        input.render(g);

        assertTrue(true);
    }
}