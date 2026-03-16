package ca.sfu.lastminutelegends.systems;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.board.Cell;
import ca.sfu.lastminutelegends.entities.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * GameSystem that handles all reward-related logic
 */
public class RewardSystem implements GameSystem {
    private static final double BONUS_SPAWN_CHANCE = 0.05; // 5% chance per tick
    private static final int BONUS_POINTS = 50;
    private static final int BONUS_TTL = 10; // 10 ticks
    
    private final Random random = new Random();
    
    @Override
    public void tick(int currentTick) {
        // Update rewards (TTL, spawning)
        updateBonusRewards();
        
        // Check for collection
        int pointsEarned = checkCollection();
        if (pointsEarned > 0) {
            // Player score update will be handled by Game class
            System.out.println("Earned " + pointsEarned + " points!");
        }
    }

    /**
     * Check if player collected any rewards at current position
     * @return points earned (0 if none)
     */
    private int checkCollection() {
        int pointsEarned = 0;
        Position playerPos = Game.instance().getPlayer().getPosition();


        List<Reward> collectedRewards = new ArrayList<>();
        for (Entity e : Game.instance().getEntities()) {
            // Check regular rewards
            if (e instanceof RegularReward regularReward) {
                if (!regularReward.isCollected() && regularReward.getPosition().equals(playerPos)) {
                    regularReward.collect();
                    pointsEarned += regularReward.getPointValue();
                    collectedRewards.add(regularReward);
                }
            }
            
            // Check bonus rewards
            if (e instanceof BonusReward bonusReward) {
                if (!bonusReward.isCollected() && !bonusReward.isExpired() && bonusReward.getPosition().equals(playerPos)) {
                    bonusReward.collect();
                    pointsEarned += bonusReward.getPointValue();
                    collectedRewards.add(bonusReward);
                }
            }
        }
        
        Game.instance().getEntities().removeAll(collectedRewards);

        return pointsEarned;
    }

    /**
     * Update bonus rewards (called every tick)
     */
    private void updateBonusRewards() {
        // Delete expired bonus rewards
        List<BonusReward> expired = new ArrayList<>();
        for (Entity e : Game.instance().getEntities()) {
            if (e instanceof BonusReward bonusReward) {
                bonusReward.onTick(Game.instance().getBoard(), Game.instance().getPlayer());
                if (bonusReward.isExpired()) {
                    expired.add(bonusReward);
                }
            }
        }
        Game.instance().getEntities().removeAll(expired);

        // Try to spawn new bonus
        if (shouldSpawnBonus()) {
            spawnBonusReward();
        }
    }
    
    /**
     * Determine if a bonus reward should spawn
     */
    private boolean shouldSpawnBonus() {
        return random.nextDouble() < BONUS_SPAWN_CHANCE;
    }

    /**
     * Spawn a new bonus reward at a random empty location
     */
    private void spawnBonusReward() {
        List<Position> validSpawns = findValidSpawnLocations();

        if (validSpawns.isEmpty()) {
            return; // No valid spawn locations
        }

        Position spawnPos = validSpawns.get(random.nextInt(validSpawns.size()));
        BonusReward newBonus = new BonusReward(spawnPos, BONUS_POINTS, BONUS_TTL);
        Game.instance().getEntities().add(newBonus);
    }

    /**
     * Find all valid locations where a bonus reward can spawn
     */
    private List<Position> findValidSpawnLocations() {
        List<Position> valid = new ArrayList<>();

        for (int y = 0; y < Game.instance().getBoard().getHeight(); y++) {
            for (int x = 0; x < Game.instance().getBoard().getWidth(); x++) {
                Position pos = new Position(x, y);
                Cell cell = Game.instance().getBoard().getCell(x, y);

                // Check if cell is passable (empty or reward cell)
                if (cell.isPassable()) {
                    // Check if position is not occupied by any other entity
                    if (!isPositionOccupied(pos)) {
                        valid.add(pos);
                    }
                }
            }
        }

        return valid;
    }

    /**
     * Check if a position is occupied by any entity
     */
    private boolean isPositionOccupied(Position pos) {
        // Check regular rewards
        for (Entity e : Game.instance().getEntities()) {
            if (e.getPosition().equals(pos)) {
                return true;
            }
        }

        return false;
    }
    
    @Override
    public void render(Graphics g) {
        // Rendering handled by BoardRenderer calling each reward's render method
    }
}