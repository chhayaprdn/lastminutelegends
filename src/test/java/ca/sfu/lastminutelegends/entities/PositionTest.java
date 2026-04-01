package ca.sfu.lastminutelegends.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Position class.
 *
 * These tests verify:
 * - Correct movement behavior in all directions
 * - Correct Manhattan distance calculation
 * - Proper equality and hashCode implementation
 */
public class PositionTest {

    /**
     * Verifies that moving UP decreases the y-coordinate by 1.
     */
    @Test
    void moveUpReturnsCorrectPosition() {
        Position start = new Position(3, 4);

        Position result = start.move(Direction.UP);

        assertEquals(new Position(3, 3), result);
    }

    /**
     * Verifies that moving DOWN increases the y-coordinate by 1.
     */
    @Test
    void moveDownReturnsCorrectPosition() {
        Position start = new Position(3, 4);

        Position result = start.move(Direction.DOWN);

        assertEquals(new Position(3, 5), result);
    }

    /**
     * Verifies that moving LEFT decreases the x-coordinate by 1.
     */
    @Test
    void moveLeftReturnsCorrectPosition() {
        Position start = new Position(3, 4);

        Position result = start.move(Direction.LEFT);

        assertEquals(new Position(2, 4), result);
    }

    /**
     * Verifies that moving RIGHT increases the x-coordinate by 1.
     */
    @Test
    void moveRightReturnsCorrectPosition() {
        Position start = new Position(3, 4);

        Position result = start.move(Direction.RIGHT);

        assertEquals(new Position(4, 4), result);
    }

    /**
     * Verifies that Manhattan distance is calculated correctly.
     */
    @Test
    void manhattanDistanceIsCalculatedCorrectly() {
        Position a = new Position(1, 2);
        Position b = new Position(4, 6);

        int distance = a.manhattanDistance(b);

        assertEquals(7, distance);
    }

    /**
     * Verifies that two positions with the same coordinates are equal.
     */
    @Test
    void equalPositionsAreEqual() {
        Position a = new Position(5, 7);
        Position b = new Position(5, 7);

        assertEquals(a, b);
    }

    /**
     * Verifies that positions with different coordinates are not equal.
     */
    @Test
    void differentPositionsAreNotEqual() {
        Position a = new Position(5, 7);
        Position b = new Position(5, 8);

        assertNotEquals(a, b);
    }

    /**
     * Verifies that equal objects produce the same hash code.
     */
    @Test
    void equalPositionsHaveSameHashCode() {
        Position a = new Position(2, 9);
        Position b = new Position(2, 9);

        assertEquals(a.hashCode(), b.hashCode());
    }
}