package ca.sfu.lastminutelegends;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameBoardTest {

    @Test
    void testInitialPosition() {
        GameBoard board = new GameBoard();
        assertEquals(0, board.getPlayerRow());
        assertEquals(0, board.getPlayerCol());
    }

    @Test
    void testMovePlayerValid() {
        GameBoard board = new GameBoard();
        board.movePlayer(0, 1);
        assertEquals(0, board.getPlayerRow());
        assertEquals(1, board.getPlayerCol());
    }

    @Test
    void testMovePlayerBlockedByWall() {
        GameBoard board = new GameBoard();
        board.movePlayer(1, 1); // (1,1) is a wall
        assertEquals(0, board.getPlayerRow());
        assertEquals(0, board.getPlayerCol());
    }

    @Test
    void testMovePlayerOutOfBounds() {
        GameBoard board = new GameBoard();
        board.movePlayer(-1, 0);
        assertEquals(0, board.getPlayerRow());
    }

    @Test
    void testRewardCollection() {
        GameBoard board = new GameBoard();

        board.movePlayer(0, 1); // move to (0,1)
        board.movePlayer(0, 1); // move to (0,2) → reward

        assertEquals(10, board.getScore());
    }

    @Test
    void testAllRewardsCollectedFalse() {
        GameBoard board = new GameBoard();
        assertFalse(board.allRewardsCollected());
    }

    @Test
    void testAllRewardsCollectedTrue() {
        GameBoard board = new GameBoard();

        boolean[][] rewards = board.getRewards();
        for (int r = 0; r < GameBoard.ROWS; r++) {
            for (int c = 0; c < GameBoard.COLS; c++) {
                rewards[r][c] = false;
            }
        }

        assertTrue(board.allRewardsCollected());
    }

    @Test
    void testExitDetection() {
        GameBoard board = new GameBoard();

        // move to exit manually
        for (int i = 0; i < 9; i++) board.movePlayer(1, 0);
        for (int i = 0; i < 9; i++) board.movePlayer(0, 1);

        assertTrue(board.isAtExit());
    }

    @Test
    void testCannotMoveThroughWall() {
        GameBoard board = new GameBoard();

        board.movePlayer(1, 1); // wall at (1,1)

        assertEquals(0, board.getPlayerRow());
        assertEquals(0, board.getPlayerCol());
    }

    @Test
    void testRewardRemovedAfterCollection() {
        GameBoard board = new GameBoard();

        board.movePlayer(0, 1);
        board.movePlayer(0, 1); // reward at (0,2)

        assertFalse(board.getRewards()[0][2]);
    }

    @Test
    void testScoreAccumulatesMultipleRewards() {
        GameBoard board = new GameBoard();

        board.movePlayer(0, 1);
        board.movePlayer(0, 1); // first reward

        board.movePlayer(1, 0);
        board.movePlayer(1, 0); // second reward at (2,2)

        assertEquals(10, board.getScore());
    }

    @Test
    void testExitFalseInitially() {
        GameBoard board = new GameBoard();
        assertFalse(board.isAtExit());
    }

    @Test
    void testMoveOutOfBoundsUp() {
        GameBoard board = new GameBoard();

        board.movePlayer(-1, 0);

        assertEquals(0, board.getPlayerRow());
        assertEquals(0, board.getPlayerCol());
    }

    @Test
    void testMoveOutOfBoundsLeft() {
        GameBoard board = new GameBoard();

        board.movePlayer(0, -1);

        assertEquals(0, board.getPlayerRow());
        assertEquals(0, board.getPlayerCol());
    }

    @Test
    void testMoveValidPosition() {
        GameBoard board = new GameBoard();

        board.movePlayer(1, 0);

        assertEquals(1, board.getPlayerRow());
    }

    @Test
    void testPlayerPositionAfterMultipleMoves() {
        GameBoard board = new GameBoard();

        board.movePlayer(1, 0);
        board.movePlayer(1, 0);
        board.movePlayer(0, 1);

        assertEquals(2, board.getPlayerRow());
        assertEquals(1, board.getPlayerCol());
    }
}