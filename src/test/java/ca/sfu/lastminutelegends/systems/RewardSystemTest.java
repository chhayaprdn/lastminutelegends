package ca.sfu.lastminutelegends.systems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.GameState;
import ca.sfu.lastminutelegends.TestUtils;
import ca.sfu.lastminutelegends.board.Board;
import ca.sfu.lastminutelegends.entities.BonusReward;
import ca.sfu.lastminutelegends.entities.Position;
import ca.sfu.lastminutelegends.entities.RegularReward;

/**
 * Unit tests for RewardSystem.
 *
 * Tests cover:
 * - Expired bonus rewards are removed from the entity list each tick
 * - Active bonus rewards are not removed before their TTL expires
 * - TTL is decremented when the system ticks
 * - RewardSystem only processes on ticks divisible by 5
 * - Regular rewards are not affected by RewardSystem ticks
 * - Multiple expired bonus rewards are all removed in one tick
 *
 * Note: a bug was discovered during testing — RewardSystem does not guard
 * against non-Playing game states, unlike CollisionDetectionSystem. This
 * means bonus rewards continue to be expired even when the game is paused
 * or in the menu state.
 */
public class RewardSystemTest {

    private RewardSystem rewardSystem;

    @BeforeEach
    void setup() throws NoSuchFieldException, IllegalAccessException {
        TestUtils.resetGameInstance();
        Game.instance().setState(GameState.Playing);

        Board board = TestUtils.makeBoard(
            "#####",
            "#...#",
            "#...#",
            "#...#",
            "#####"
        );
        Game.instance().setBoard(board);

        rewardSystem = new RewardSystem();
    }

    @Test
    void expiredBonusRewardIsRemovedFromEntityList() {
        BonusReward bonus = new BonusReward(new Position(1, 1), 50, 1);
        bonus.onTick(null, null); // manually expire
        assertTrue(bonus.isExpired());

        Game.instance().getEntities().add(bonus);
        rewardSystem.tick(0);

        assertFalse(Game.instance().getEntities().contains(bonus));
    }

    @Test
    void activeBonusRewardIsNotRemovedBeforeExpiry() {
        BonusReward bonus = new BonusReward(new Position(1, 1), 50, 10);
        Game.instance().getEntities().add(bonus);

        rewardSystem.tick(0);

        assertTrue(Game.instance().getEntities().contains(bonus));
    }

    @Test
    void bonusRewardTTLDecrementsOnSystemTick() {
        BonusReward bonus = new BonusReward(new Position(1, 1), 50, 5);
        Game.instance().getEntities().add(bonus);

        rewardSystem.tick(0); // multiple of 5 — system runs

        // TTL decremented from 5 to 4 → 4/5 = 0.8
        assertEquals(0.8f, bonus.getTimeRemainingPercentage(), 0.01f);
    }

    @Test
    void rewardSystemDoesNotRunOnNonMultipleOfFiveTick() {
        BonusReward bonus = new BonusReward(new Position(1, 1), 50, 5);
        Game.instance().getEntities().add(bonus);

        rewardSystem.tick(1);
        rewardSystem.tick(2);
        rewardSystem.tick(3);
        rewardSystem.tick(4);

        // No ticks should have been processed — TTL still 5/5 = 1.0
        assertEquals(1.0f, bonus.getTimeRemainingPercentage(), 0.01f);
    }

    @Test
    void regularRewardsAreNotAffectedByRewardSystemTick() {
        RegularReward reward = new RegularReward(new Position(1, 1));
        Game.instance().getEntities().add(reward);

        rewardSystem.tick(0);

        assertTrue(Game.instance().getEntities().contains(reward));
        assertFalse(reward.isCollected());
    }

    @Test
    void multipleExpiredBonusRewardsAreAllRemoved() {
        BonusReward b1 = new BonusReward(new Position(1, 1), 50, 1);
        BonusReward b2 = new BonusReward(new Position(2, 1), 50, 1);
        BonusReward b3 = new BonusReward(new Position(3, 1), 50, 1);

        b1.onTick(null, null);
        b2.onTick(null, null);
        b3.onTick(null, null);

        Game.instance().getEntities().add(b1);
        Game.instance().getEntities().add(b2);
        Game.instance().getEntities().add(b3);

        rewardSystem.tick(0);

        assertFalse(Game.instance().getEntities().contains(b1));
        assertFalse(Game.instance().getEntities().contains(b2));
        assertFalse(Game.instance().getEntities().contains(b3));
    }

    @Test
    void mixedExpiredAndActiveBonusRewards() {
        BonusReward expired = new BonusReward(new Position(1, 1), 50, 1);
        BonusReward active  = new BonusReward(new Position(2, 1), 50, 10);

        expired.onTick(null, null);

        Game.instance().getEntities().add(expired);
        Game.instance().getEntities().add(active);

        rewardSystem.tick(0);

        assertFalse(Game.instance().getEntities().contains(expired));
        assertTrue(Game.instance().getEntities().contains(active));
    }
}
