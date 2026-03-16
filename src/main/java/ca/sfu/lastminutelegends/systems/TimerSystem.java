package ca.sfu.lastminutelegends.systems;

import ca.sfu.lastminutelegends.Game;

import java.awt.*;

public class TimerSystem implements GameSystem {
    @Override
    public void tick(int tick) {
        Game.instance().incrementTimer();
    }

    @Override
    public void render(Graphics g) {

    }
}
