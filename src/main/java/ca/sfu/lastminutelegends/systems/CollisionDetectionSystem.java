package ca.sfu.lastminutelegends.systems;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.GameState;
import ca.sfu.lastminutelegends.entities.Entity;
import ca.sfu.lastminutelegends.entities.MovingEnemy;
import ca.sfu.lastminutelegends.entities.Punishment;
import ca.sfu.lastminutelegends.entities.RegularReward;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CollisionDetectionSystem implements GameSystem {

    @Override
    public void tick(int tick) {
        handleEnemyCollision();
        handleEndPointCollision();
    }
    
    private void handleEnemyCollision() {
        Game game = Game.instance();

        List<Entity> removedPunishments = new ArrayList<>();
        for (Entity e : game.getEntities()) {
            if (e instanceof MovingEnemy enemy) {
                if (enemy.getPosition().equals(game.getPlayer().getPosition())) {
                    game.setState(GameState.Lost);
                    return;
                }
            }

            if (e instanceof Punishment punishment) {
                if (punishment.getPosition().equals(game.getPlayer().getPosition())) {
                    removedPunishments.add(punishment);
                    game.addScore(-25);
                }
            }
        }

        game.getEntities().removeAll(removedPunishments);
    }
    
    private void handleEndPointCollision() {
        Game game = Game.instance();
        
        if (game.getPlayer().getPosition().equals(game.getBoard().getEndPointPos())){
            for (Entity e : game.getEntities()) {
                if (e instanceof RegularReward)
                    return;
            }

            game.setState(GameState.Won);
        }
    }

    @Override
    public void render(Graphics g) {
        // nothing
    }
}