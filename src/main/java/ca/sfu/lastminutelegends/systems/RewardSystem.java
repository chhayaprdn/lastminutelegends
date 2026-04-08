package ca.sfu.lastminutelegends.systems;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.board.Cell;
import ca.sfu.lastminutelegends.entities.BonusReward;
import ca.sfu.lastminutelegends.entities.Entity;
import ca.sfu.lastminutelegends.entities.Position;

/**
 * GameSystem responsible for the full reward lifecycle each game tick.
 */
public class RewardSystem implements GameSystem {
    private static final double BONUS_SPAWN_CHANCE = 0.15; // 5% chance per tick
    private static final int BONUS_POINTS = 25;
    private static final int BONUS_TTL = 10;
    private static final int TICK_INTERVAL = 5;
    
    private final Random random = new Random();

    /**
     * Runs one game tick. Does nothing if the game is not in the Playing state.
     *
     * @param tick the current tick count
     */
    @Override
    public void tick(int tick) {
        if (tick % 5 != 0)
            return;
    
        expireOldBonusRewards();
        trySpawnBonus();
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
                new BonusReward(spawnPos, BONUS_POINTS, BONUS_TTL));
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