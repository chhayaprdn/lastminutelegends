package ca.sfu.lastminutelegends.ui;

import java.awt.Graphics;

/**
 * Represents a distinct screen of the game (e.g. main menu, gameplay, win/loss).
 * The active screen is rendered each frame by {@link ca.sfu.lastminutelegends.GameCanvas}.
 */
public interface Screen {
    /**
     * Renders this screen's contents to the canvas.
     *
     * @param g the graphics context to draw on
     */
    void render(Graphics g);
}
