package ca.sfu.lastminutelegends.systems;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.GameState;
import ca.sfu.lastminutelegends.entities.MovingEnemy;
import ca.sfu.lastminutelegends.entities.Player;

import java.awt.*;
import java.util.List;

public class CollisionDetectionSystem implements GameSystem {

    private final Player player;
    private final List<MovingEnemy> enemies;

    public CollisionDetectionSystem(Player player, List<MovingEnemy> enemies) {
        this.player = player;
        this.enemies = enemies;
    }

    @Override
    public void tick(int tick) {

        if (Game.instance().getState() != GameState.Playing) {
            return;
        }

        for (MovingEnemy enemy : enemies) {
            if (enemy.getPos().equals(player.getPos())) {
                Game.instance().setState(GameState.Lost);
                return;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        // nothing
    }
}