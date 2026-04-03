package ca.sfu.lastminutelegends.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for BonusReward.
 *
 * Tests cover:
 * - Initial state (not collected, not expired)
 * - TTL countdown via onTick()
 * - Expiry after TTL reaches zero
 * - Collected rewards do not tick or expire
 * - Time remaining percentage calculation
 */
public class BonusRewardTest {

    @Test
    void pointValueIsSetCorrectly() {
        BonusReward reward = new BonusReward(new Position(0, 0), 50, 10);
        assertEquals(50, reward.getPointValue());
    }

    @Test
    void notCollectedOnCreation() {
        BonusReward reward = new BonusReward(new Position(0, 0), 50, 10);
        assertFalse(reward.isCollected());
    }

    @Test
    void notExpiredOnCreation() {
        BonusReward reward = new BonusReward(new Position(0, 0), 50, 10);
        assertFalse(reward.isExpired());
    }

    @Test
    void ttlCountsDownOnTick() {
        BonusReward reward = new BonusReward(new Position(0, 0), 50, 5);
        reward.onTick(null, null);
        reward.onTick(null, null);
        // After 2 ticks on a TTL-5 reward, time remaining = 3/5 = 0.6
        assertEquals(0.6f, reward.getTimeRemainingPercentage(), 0.01f);
    }

    @Test
    void expiresAfterAllTicksConsumed() {
        BonusReward reward = new BonusReward(new Position(0, 0), 50, 3);
        reward.onTick(null, null);
        reward.onTick(null, null);
        reward.onTick(null, null);
        assertTrue(reward.isExpired());
    }

    @Test
    void notExpiredBeforeAllTicksConsumed() {
        BonusReward reward = new BonusReward(new Position(0, 0), 50, 3);
        reward.onTick(null, null);
        reward.onTick(null, null);
        assertFalse(reward.isExpired());
    }

    @Test
    void collectedRewardDoesNotTick() {
        BonusReward reward = new BonusReward(new Position(0, 0), 50, 2);
        reward.collect();
        reward.onTick(null, null);
        reward.onTick(null, null);
        reward.onTick(null, null);
        // Even after more ticks than TTL, collected reward should not be expired
        assertFalse(reward.isExpired());
    }

    @Test
    void collectedRewardIsNotExpired() {
        BonusReward reward = new BonusReward(new Position(0, 0), 50, 1);
        reward.collect();
        reward.onTick(null, null);
        assertFalse(reward.isExpired());
    }

    @Test
    void timeRemainingPercentageIsFullAtStart() {
        BonusReward reward = new BonusReward(new Position(0, 0), 50, 10);
        assertEquals(1.0f, reward.getTimeRemainingPercentage(), 0.01f);
    }

    @Test
    void timeRemainingPercentageIsHalfwayAfterHalfTicks() {
        BonusReward reward = new BonusReward(new Position(0, 0), 50, 10);
        for (int i = 0; i < 5; i++) {
            reward.onTick(null, null);
        }
        assertEquals(0.5f, reward.getTimeRemainingPercentage(), 0.01f);
    }

    @Test
    void positionIsSetCorrectly() {
        Position pos = new Position(2, 4);
        BonusReward reward = new BonusReward(pos, 50, 10);
        assertEquals(pos, reward.getPosition());
    }
}
