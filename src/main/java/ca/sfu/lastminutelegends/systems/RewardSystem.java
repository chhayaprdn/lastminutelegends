package ca.sfu.lastminutelegends.systems;

import ca.sfu.lastminutelegends.entities.Player;
import ca.sfu.lastminutelegends.manager.RewardManager;

import java.awt.*;

/**
 * GameSystem that handles all reward-related logic
 */
public class RewardSystem implements GameSystem {
    private RewardManager rewardManager;
    private Player player;
    
    public RewardSystem(RewardManager rewardManager, Player player) {
        this.rewardManager = rewardManager;
        this.player = player;
    }
    
    @Override
    public void tick(int currentTick) {
        if (player == null || rewardManager == null) return;
        
        // Update rewards (TTL, spawning)
        rewardManager.update(currentTick);
        
        // Check for collection
        int pointsEarned = rewardManager.checkCollection(player);
        if (pointsEarned > 0) {
            // Player score update will be handled by Game class
            System.out.println("Earned " + pointsEarned + " points!");
        }
    }
    
    @Override
    public void render(Graphics g) {
        // Rendering handled by BoardRenderer calling each reward's render method
    }
}