package ca.sfu.lastminutelegends.systems;

import java.awt.Graphics;

public interface GameSystem {
    /**
     * Updates this system's state for the current game tick.
     *
     * @param tick the current tick count since the game started
     */
    void tick(int tick);
    
    /**
     * Renders this system's visual output to the screen.
     *
     * @param g the graphics context to draw on
     */
    void render(Graphics g);
}
