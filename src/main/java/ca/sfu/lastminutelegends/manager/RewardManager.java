package ca.sfu.lastminutelegends.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.sfu.lastminutelegends.board.Board;
import ca.sfu.lastminutelegends.board.Cell;
import ca.sfu.lastminutelegends.board.RewardCell;
import ca.sfu.lastminutelegends.entities.Player;
import ca.sfu.lastminutelegends.entities.Position;
import ca.sfu.lastminutelegends.reward.BonusReward;
import ca.sfu.lastminutelegends.reward.RegularReward;
import ca.sfu.lastminutelegends.reward.Reward;

/**
 * Manages all rewards in the game
 */
public class RewardManager {
    private List<Reward> regularRewards;
    private List<BonusReward> bonusRewards;
    private Board board;
    private Random random;
    
    private static final double BONUS_SPAWN_CHANCE = 0.05; // 5% chance per tick
    private static final int BONUS_POINTS = 50;
    private static final int BONUS_TTL = 10; // 10 ticks
    
    public RewardManager(Board board) {
        this.regularRewards = new ArrayList<>();
        this.bonusRewards = new ArrayList<>();
        this.board = board;
        this.random = new Random();
        
        // Initialize regular rewards from board
        initializeRegularRewards();
    }
    
    /**
     * Find all regular rewards on the board and create Reward objects
     */
    private void initializeRegularRewards() {
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                Cell cell = board.getCell(x, y);
                if (cell instanceof RewardCell) {
                    RewardCell rewardCell = (RewardCell) cell;
                    if (!rewardCell.isCollected()) {
                        RegularReward reward = new RegularReward(new Position(x, y));
                        regularRewards.add(reward);
                    }
                }
            }
        }
        System.out.println("Initialized " + regularRewards.size() + " regular rewards");
    }
    
    /**
     * Check if player collected any rewards at current position
     * @return points earned (0 if none)
     */
    public int checkCollection(Player player) {
        int pointsEarned = 0;
        Position playerPos = player.getPos();
        
        // Check regular rewards
        List<Reward> collectedRegular = new ArrayList<>();
        for (Reward reward : regularRewards) {
            if (!reward.isCollected() && reward.getPosition().equals(playerPos)) {
                reward.collect();
                pointsEarned += reward.getPointValue();
                collectedRegular.add(reward);
                
                // Update the board cell
                Cell cell = board.getCell(playerPos.x, playerPos.y);
                if (cell instanceof RewardCell) {
                    ((RewardCell) cell).setCollected(true);
                }
                
                System.out.println("Collected regular reward! +" + reward.getPointValue());
            }
        }
        regularRewards.removeAll(collectedRegular);
        
        // Check bonus rewards
        List<BonusReward> collectedBonus = new ArrayList<>();
        for (BonusReward bonus : bonusRewards) {
            if (!bonus.isCollected() && !bonus.isExpired() && bonus.getPosition().equals(playerPos)) {
                bonus.collect();
                pointsEarned += bonus.getPointValue();
                collectedBonus.add(bonus);
                System.out.println("Collected bonus reward! +" + bonus.getPointValue());
            }
        }
        bonusRewards.removeAll(collectedBonus);
        
        return pointsEarned;
    }
    
    /**
     * Update all rewards (called every tick)
     */
    public void update(int currentTick) {
        // Update existing bonuses
        List<BonusReward> expired = new ArrayList<>();
        for (BonusReward bonus : bonusRewards) {
            bonus.onTick();
            if (bonus.isExpired()) {
                expired.add(bonus);
                System.out.println("Bonus reward expired at " + bonus.getPosition());
            }
        }
        bonusRewards.removeAll(expired);
        
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
        bonusRewards.add(newBonus);
        
        System.out.println("Spawned bonus reward at " + spawnPos + " (TTL: " + BONUS_TTL + ")");
    }
    
    /**
     * Find all valid locations where a bonus reward can spawn
     */
    private List<Position> findValidSpawnLocations() {
        List<Position> valid = new ArrayList<>();
        
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                Position pos = new Position(x, y);
                Cell cell = board.getCell(x, y);
                
                // Check if cell is passable (empty or reward cell)
                if (cell.isPassable()) {
                    // Check if position is not occupied by player or other rewards
                    if (!isPositionOccupied(pos)) {
                        valid.add(pos);
                    }
                }
            }
        }
        
        return valid;
    }
    
    /**
     * Check if a position is occupied by any reward
     */
    private boolean isPositionOccupied(Position pos) {
        // Check regular rewards
        for (Reward reward : regularRewards) {
            if (!reward.isCollected() && reward.getPosition().equals(pos)) {
                return true;
            }
        }
        
        // Check bonus rewards
        for (BonusReward bonus : bonusRewards) {
            if (!bonus.isCollected() && !bonus.isExpired() && bonus.getPosition().equals(pos)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Check if all regular rewards have been collected
     */
    public boolean allRegularRewardsCollected() {
        return regularRewards.isEmpty();
    }
    
    /**
     * Get count of remaining regular rewards
     */
    public int getRemainingRegularRewards() {
        return regularRewards.size();
    }
    
    /**
     * Get all active rewards (for rendering)
     */
    public List<Reward> getAllActiveRewards() {
        List<Reward> all = new ArrayList<>();
        all.addAll(regularRewards);
        all.addAll(bonusRewards);
        return all;
    }
    
    public List<BonusReward> getActiveBonusRewards() {
        return bonusRewards;
    }
}