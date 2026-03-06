package ca.sfu.lastminutelegends.systems;

import java.awt.*;

public interface GameSystem {
    void tick(int tick);
    void render(Graphics g);
}
