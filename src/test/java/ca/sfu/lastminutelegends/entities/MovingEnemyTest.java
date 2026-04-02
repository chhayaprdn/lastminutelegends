package ca.sfu.lastminutelegends.entities;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.TestUtils;
import ca.sfu.lastminutelegends.board.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for MovingEnemy behavior.
 *
 * These tests verify:
 * - Enemy moves toward the player
 * - Enemy avoids walls
 * - Tie-breaking priority is respected
 * - Enemy remains stationary when no moves are available
 */
public class MovingEnemyTest {

    /**
     * Clears entity list before each test to avoid shared state issues.
     */
    @BeforeEach
    void clearGameEntities() {
        Game.instance().getEntities().clear();
    }

    /**
     * Enemy should move one step closer to the player.
     */
    @Test
    void enemyMovesCloserToPlayer() {
        Board board = TestUtils.makeBoard("...", "...", "...");

        Player player = new Player(new Position(2, 1));
        MovingEnemy enemy = new MovingEnemy(new Position(0, 1));

        Game.instance().getEntities().add(enemy);
        Game.instance().getEntities().add(player);

        enemy.onTick(board, player);

        assertEquals(new Position(1, 1), enemy.getPosition());
    }

    /**
     * Enemy should not move into a wall, and instead choose another valid direction.
     */
    @Test
    void enemyDoesNotMoveIntoWall() {
        Board board = TestUtils.makeBoard("...", ".#.", "...");

        Player player = new Player(new Position(2, 1));
        MovingEnemy enemy = new MovingEnemy(new Position(0, 1));

        Game.instance().getEntities().add(enemy);
        Game.instance().getEntities().add(player);

        enemy.onTick(board, player);

        assertEquals(new Position(0, 0), enemy.getPosition());
    }

    /**
     * Enemy should follow deterministic tie-breaking priority when multiple moves are equally optimal.
     */
    @Test
    void enemyUsesTieBreakingPriority() {
        Board board = TestUtils.makeBoard("...", "...", "...");

        Player player = new Player(new Position(0, 0));
        MovingEnemy enemy = new MovingEnemy(new Position(1, 1));

        Game.instance().getEntities().add(enemy);
        Game.instance().getEntities().add(player);

        enemy.onTick(board, player);

        assertEquals(new Position(1, 0), enemy.getPosition());
    }

    /**
     * Enemy should remain in place if all surrounding cells are blocked.
     */
    @Test
    void enemyStaysStillIfNoValidMoveExists() {
        Board board = TestUtils.makeBoard("###", "#.#", "###");

        Player player = new Player(new Position(0, 0));
        MovingEnemy enemy = new MovingEnemy(new Position(1, 1));

        Game.instance().getEntities().add(enemy);
        Game.instance().getEntities().add(player);

        enemy.onTick(board, player);

        assertEquals(new Position(1, 1), enemy.getPosition());
    }
}