package ca.sfu.lastminutelegends.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for RegularReward.
 *
 * Tests cover:
 * - Initial point value
 * - Collection state before and after collect()
 * - Calling collect() more than once
 * - Position assignment
 */
public class RegularRewardTest {

    @Test
    void initialPointValueIsTen() {
        RegularReward reward = new RegularReward(new Position(0, 0));
        assertEquals(10, reward.getPointValue());
    }

    @Test
    void notCollectedOnCreation() {
        RegularReward reward = new RegularReward(new Position(0, 0));
        assertFalse(reward.isCollected());
    }

    @Test
    void collectMarksAsCollected() {
        RegularReward reward = new RegularReward(new Position(0, 0));
        reward.collect();
        assertTrue(reward.isCollected());
    }

    @Test
    void collectingTwiceRemainsCollected() {
        RegularReward reward = new RegularReward(new Position(0, 0));
        reward.collect();
        reward.collect();
        assertTrue(reward.isCollected());
    }

    @Test
    void positionIsSetCorrectly() {
        Position pos = new Position(3, 5);
        RegularReward reward = new RegularReward(pos);
        assertEquals(pos, reward.getPosition());
    }

    @Test
    void differentPositionsAreStoredCorrectly() {
        RegularReward r1 = new RegularReward(new Position(0, 0));
        RegularReward r2 = new RegularReward(new Position(4, 7));
        assertNotEquals(r1.getPosition(), r2.getPosition());
    }
}
