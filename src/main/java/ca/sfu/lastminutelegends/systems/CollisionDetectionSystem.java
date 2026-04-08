package ca.sfu.lastminutelegends.systems;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.GameState;
import ca.sfu.lastminutelegends.entities.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CollisionDetectionSystem implements GameSystem {

    @Override
    public void tick(int tick) {
        handleEnemyCollision();
        handleRewardCollision();
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

    /**
     * Checks whether the player's current position overlaps with any uncollected
     * reward. If so, marks the reward as collected, adds its point value to the
     * score, and removes it from the entity list.
     */
    private void handleRewardCollision() {
        List<Reward> toCollect = new ArrayList<>();

        for (Entity e : Game.instance().getEntities()) {
            if (!(e instanceof Reward reward)) continue;
            if (reward.isCollected()) continue;
            if (!reward.getPosition().equals(Game.instance().getPlayer().getPosition())) continue;

            // Bonus rewards must still be active to be collectable
            if (reward instanceof BonusReward bonus && bonus.isExpired()) continue;

            toCollect.add(reward);
        }

        for (Reward reward : toCollect) {
            reward.collect();
            Game.instance().addScore(reward.getPointValue());
        }

        Game.instance().getEntities().removeAll(toCollect);
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
}