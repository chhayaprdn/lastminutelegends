package ca.sfu.lastminutelegends.systems;

import ca.sfu.lastminutelegends.Game;

public class TimerSystem implements GameSystem {
    @Override
    public void tick(int tick) {
        if (tick % 10 != 0)
            return;
        
        Game.instance().incrementTimer();
    }
}
