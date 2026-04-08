package ca.sfu.lastminutelegends.systems;

import java.awt.Graphics;

public interface GameSystem {
    /**
     * Updates this system's state for the current game tick.
     *
     * @param tick the current tick count since the game started
     */
    void tick(int tick);
}
