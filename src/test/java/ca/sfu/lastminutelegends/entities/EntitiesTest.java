package ca.sfu.lastminutelegends.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntitiesTest {

    @Test
    void testPositionCreation() {
        Position pos = new Position(2, 3);
        assertNotNull(pos);
    }

    @Test
    void testPlayerCreation() {
        Position pos = new Position(0, 0);
        Player player = new Player(pos);
        assertNotNull(player);
    }

    @Test
    void testMovingEnemyCreation() {
        Position pos = new Position(1, 1);
        MovingEnemy enemy = new MovingEnemy(pos);
        assertNotNull(enemy);
    }

    @Test
    void testMultiplePlayers() {
        Player p1 = new Player(new Position(0, 0));
        Player p2 = new Player(new Position(0, 0));

        assertNotSame(p1, p2);
    }

    @Test
    void testMovingEnemyDifferentInstances() {
        MovingEnemy e1 = new MovingEnemy(new Position(1, 1));
        MovingEnemy e2 = new MovingEnemy(new Position(1, 1));

        assertNotSame(e1, e2);
    }
}