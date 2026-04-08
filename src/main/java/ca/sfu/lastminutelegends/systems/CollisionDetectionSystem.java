package ca.sfu.lastminutelegends.systems;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.GameState;
import ca.sfu.lastminutelegends.entities.*;

import java.awt.*;

public class CollisionDetectionSystem implements GameSystem {

    @Override
    public void tick(int tick) {
        for (Entity e : Game.instance().getEntities()) {
            if (e.getPosition().equals(Game.instance().getPlayer().getPosition())) {
                e.onCollideWithPlayer();
            }
        }
        
        handleEndPointCollision();
    }

    /**
     * Handle special case of player colliding with end point. The end point is a Cell not an Entity and thus doesn't 
     * have the onCollideWithPlayer method
     */
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