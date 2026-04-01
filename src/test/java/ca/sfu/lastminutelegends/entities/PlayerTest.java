package ca.sfu.lastminutelegends.entities;

import ca.sfu.lastminutelegends.board.Board;
import ca.sfu.lastminutelegends.board.BoardAssembler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Player movement behavior.
 *
 * These tests verify:
 * - Movement into valid cells
 * - Prevention of movement into walls
 * - Boundary constraints
 * - Handling of null input
 */
public class PlayerTest {

    /**
     * Utility method to construct a board from string rows.
     */
    private Board makeBoard(String... rows) {
        BoardAssembler assembler = new BoardAssembler();

        for (int y = 0; y < rows.length; y++) {
            char[] chars = rows[y].toCharArray();
            for (int x = 0; x < chars.length; x++) {
                assembler.onCellParsed(chars[x], x, y);
            }
        }

        return assembler.getBoard();
    }

    /**
     * Player should move into a valid empty cell.
     */
    @Test
    void playerMovesIntoValidCell() {
        Board board = makeBoard("...", "...", "...");
        Player player = new Player(new Position(1, 1));

        player.tryMove(Direction.RIGHT, board);

        assertEquals(new Position(2, 1), player.getPosition());
    }

    /**
     * Player should not move into a wall cell.
     */
    @Test
    void playerDoesNotMoveIntoWall() {
        Board board = makeBoard("...", ".#.", "...");
        Player player = new Player(new Position(0, 1));

        player.tryMove(Direction.RIGHT, board);

        assertEquals(new Position(0, 1), player.getPosition());
    }

    /**
     * Player should not move outside the left boundary.
     */
    @Test
    void playerDoesNotMoveOutsideLeftBoundary() {
        Board board = makeBoard("...", "...", "...");
        Player player = new Player(new Position(0, 1));

        player.tryMove(Direction.LEFT, board);

        assertEquals(new Position(0, 1), player.getPosition());
    }

    /**
     * Player should not move outside the upper boundary.
     */
    @Test
    void playerDoesNotMoveOutsideUpperBoundary() {
        Board board = makeBoard("...", "...", "...");
        Player player = new Player(new Position(1, 0));

        player.tryMove(Direction.UP, board);

        assertEquals(new Position(1, 0), player.getPosition());
    }

    /**
     * Player should not move when the direction is null.
     */
    @Test
    void nullDirectionDoesNotMovePlayer() {
        Board board = makeBoard("...", "...", "...");
        Player player = new Player(new Position(1, 1));

        player.tryMove(null, board);

        assertEquals(new Position(1, 1), player.getPosition());
    }
}