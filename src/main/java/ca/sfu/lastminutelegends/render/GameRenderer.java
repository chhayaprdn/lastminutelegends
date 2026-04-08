package ca.sfu.lastminutelegends.render;

import java.awt.*;

public interface GameRenderer {
    /**
     * Renders this system's visual output to the screen.
     *
     * @param g the graphics context to draw on
     */
    void render(Graphics g);
}
