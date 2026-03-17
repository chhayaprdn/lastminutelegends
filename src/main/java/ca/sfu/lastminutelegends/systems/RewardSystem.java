package ca.sfu.lastminutelegends.systems;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.GameState;
import ca.sfu.lastminutelegends.board.Cell;
import ca.sfu.lastminutelegends.entities.BonusReward;
import ca.sfu.lastminutelegends.entities.Entity;
import ca.sfu.lastminutelegends.entities.Position;
import ca.sfu.lastminutelegends.entities.Reward;

/**
 * GameSystem responsible for the full reward lifecycle each game tick.
 */
public class RewardSystem implements GameSystem {

    private static final double BONUS_SPAWN_CHANCE = 0.05;
    private static final int BONUS_POINT_VALUE = 50;
    private static final int BONUS_TTL = 10;

    private final Random random = new Random();

    /**
     * Runs one game tick. Does nothing if the game is not in the Playing state.
     *
     * @param currentTick the current tick count
     */
    @Override
    public void tick(int currentTick) {
        if (Game.instance().getState() != GameState.Playing) return;

        expireOldBonusRewards();
        trySpawnBonus();
        collectRewardsAtPlayerPosition();
    }

    /**
     * No rendering is done here. Each reward's own render() method is called
     * by EntityRenderer as part of its pass over Game.getEntities().
     *
     * @param g the graphics context (unused)
     */
    @Override
    public void render(Graphics g) {
    }

    /**
     * Ticks every active {@link BonusReward} and removes those whose TTL has
     * reached zero.
     */
    private void expireOldBonusRewards() {
        List<BonusReward> expired = new ArrayList<>();

        for (Entity e : Game.instance().getEntities()) {
            if (e instanceof BonusReward bonus) {
                bonus.onTick(Game.instance().getBoard(), Game.instance().getPlayer());
                if (bonus.isExpired()) {
                    expired.add(bonus);
                }
            }
        }

        Game.instance().getEntities().removeAll(expired);
    }

    /**
     * Spawns a new {@link BonusReward} at a random valid board location if the
     * random roll succeeds.
     */
    private void trySpawnBonus() {
        if (random.nextDouble() >= BONUS_SPAWN_CHANCE) return;

        List<Position> candidates = findValidSpawnLocations();
        if (candidates.isEmpty()) return;

        Position spawnPos = candidates.get(random.nextInt(candidates.size()));
        Game.instance().getEntities().add(
                new BonusReward(spawnPos, BONUS_POINT_VALUE, BONUS_TTL));
    }
    
    /**
     * Checks whether the player's current position overlaps with any uncollected
     * reward. If so, marks the reward as collected, adds its point value to the
     * score, and removes it from the entity list.
     */
    private void collectRewardsAtPlayerPosition() {
        Position playerPos = Game.instance().getPlayer().getPosition();
        List<Reward> toCollect = new ArrayList<>();

        for (Entity e : Game.instance().getEntities()) {
            if (!(e instanceof Reward reward)) continue;
            if (reward.isCollected()) continue;
            if (!reward.getPosition().equals(playerPos)) continue;

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

    /**
     * @return list of valid spawn positions; may be empty if the board is full
     */
    private List<Position> findValidSpawnLocations() {
        List<Position> valid = new ArrayList<>();

        for (int y = 0; y < Game.instance().getBoard().getHeight(); y++) {
            for (int x = 0; x < Game.instance().getBoard().getWidth(); x++) {
                Cell cell = Game.instance().getBoard().getCell(x, y);
                if (!cell.isPassable()) continue;

                Position pos = new Position(x, y);
                if (!isOccupied(pos)) {
                    valid.add(pos);
                }
            }
        }

        return valid;
    }

    /**
     * Returns true if any entity currently occupies the given position.
     *
     * @param pos the board position to check
     * @return whether the position is already occupied
     */
    private boolean isOccupied(Position pos) {
        for (Entity e : Game.instance().getEntities()) {
            if (e.getPosition().equals(pos)) return true;
        }
        return false;
    }
}