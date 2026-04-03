package ca.sfu.lastminutelegends.systems;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.GameState;
import ca.sfu.lastminutelegends.TestUtils;
import ca.sfu.lastminutelegends.board.Board;
import ca.sfu.lastminutelegends.entities.Direction;
import ca.sfu.lastminutelegends.entities.Player;
import ca.sfu.lastminutelegends.entities.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests for player movement through the input and player systems.
 *
 * These tests verify the interaction between:
 * - InputSystem
 * - PlayerSystem
 * - Player
 * - Board movement constraints
 */
public class PlayerMovementIntegrationTest {

    private InputSystem inputSystem;
    private PlayerSystem playerSystem;

    /**
     * Resets shared game state before each test.
     */
    @BeforeEach
    void setup() {
        Game.instance().getEntities().clear();
        Game.instance().setState(GameState.Playing);
        Game.instance().setPlayer(null);
        Game.instance().setBoard(null);

        // A plain Swing component is enough for constructing InputSystem in tests.
        java.awt.Panel dummyPanel = new java.awt.Panel();
        inputSystem = new InputSystem(dummyPanel);
        playerSystem = new PlayerSystem(inputSystem);
    }

    /**
     * Injects a direction into InputSystem without using actual keyboard events.
     */
    @SuppressWarnings("unchecked")
    private void setPendingDirection(Direction direction) throws Exception {
        Field field = InputSystem.class.getDeclaredField("lastDirection");
        field.setAccessible(true);
        AtomicReference<Direction> ref = (AtomicReference<Direction>) field.get(inputSystem);
        ref.set(direction);
    }

    /**
     * Verifies that a valid movement input moves the player to the next cell.
     */
    @Test
    void validInputMovesPlayer() throws Exception {
        Board board = TestUtils.makeBoard("...");
        Game.instance().setBoard(board);

        Player player = new Player(new Position(1, 0));
        Game.instance().setPlayer(player);
        Game.instance().getEntities().add(player);

        setPendingDirection(Direction.RIGHT);
        playerSystem.tick(0);

        assertEquals(new Position(2, 0), player.getPosition());
    }

    /**
     * Verifies that movement input does not move the player into a wall.
     */
    @Test
    void inputDoesNotMovePlayerIntoWall() throws Exception {
        Board board = TestUtils.makeBoard(".#.");
        Game.instance().setBoard(board);

        Player player = new Player(new Position(0, 0));
        Game.instance().setPlayer(player);
        Game.instance().getEntities().add(player);

        setPendingDirection(Direction.RIGHT);
        playerSystem.tick(0);

        assertEquals(new Position(0, 0), player.getPosition());
    }

    /**
     * Verifies that movement input does not move the player outside board boundaries.
     */
    @Test
    void inputDoesNotMovePlayerOutsideBoard() throws Exception {
        Board board = TestUtils.makeBoard("...");
        Game.instance().setBoard(board);

        Player player = new Player(new Position(0, 0));
        Game.instance().setPlayer(player);
        Game.instance().getEntities().add(player);

        setPendingDirection(Direction.LEFT);
        playerSystem.tick(0);

        assertEquals(new Position(0, 0), player.getPosition());
    }
}