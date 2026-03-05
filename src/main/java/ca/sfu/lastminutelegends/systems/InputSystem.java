package ca.sfu.lastminutelegends.systems;

import ca.sfu.lastminutelegends.entities.Direction;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Captures keyboard input and stores the most recent direction pressed.
 * We consume the direction once per tick to keep movement tick-based.
 */
public class InputSystem implements GameSystem {

    private final AtomicReference<Direction> lastDirection = new AtomicReference<>(null);

    public InputSystem(Component listenOn) {
        listenOn.setFocusable(true);
        listenOn.requestFocusInWindow();

        listenOn.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP, KeyEvent.VK_W -> lastDirection.set(Direction.UP);
                    case KeyEvent.VK_DOWN, KeyEvent.VK_S -> lastDirection.set(Direction.DOWN);
                    case KeyEvent.VK_LEFT, KeyEvent.VK_A -> lastDirection.set(Direction.LEFT);
                    case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> lastDirection.set(Direction.RIGHT);
                    default -> { /* ignore other keys */ }
                }
            }
        });
    }

    /**
     * Returns the last direction and clears it.
     * If no direction was pressed since last tick, returns null.
     */
    public Direction consumeDirection() {
        return lastDirection.getAndSet(null);
    }

    @Override
    public void tick(int tick) {
        // Input is event-driven (KeyListener), so no per-tick logic needed here.
    }

    @Override
    public void render(Graphics g) {
        // Nothing to render for input.
    }
}