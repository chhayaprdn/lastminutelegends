package ca.sfu.lastminutelegends.systems;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.GameState;
import ca.sfu.lastminutelegends.entities.Entity;
import ca.sfu.lastminutelegends.entities.MovingEnemy;

import java.awt.*;

public class CollisionDetectionSystem implements GameSystem {

    @Override
    public void tick(int tick) {

        if (Game.instance().getState() != GameState.Playing) {
            return;
        }

        for (Entity e : Game.instance().getEntities()) {
            if (e instanceof MovingEnemy enemy) {
                if (enemy.getPosition().equals(Game.instance().getPlayer().getPosition())) {
                    Game.instance().setState(GameState.Lost);
                    return;
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        // nothing
    }
}