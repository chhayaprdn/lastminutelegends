package ca.sfu.lastminutelegends;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameBoardLogicTest {

    @Test
    void testMultipleMoves() {
        GameBoard board = new GameBoard();

        board.movePlayer(1, 0);
        board.movePlayer(0, 1);
        board.movePlayer(-1, 0);
        board.movePlayer(0, -1);

        assertNotNull(board);
        assertEquals(0, board.getPlayerCol());
        assertEquals(0, board.getPlayerRow());
    }

    @Test
    void testBoundaryConditions() {
        GameBoard board = new GameBoard();

        // extreme moves → hit boundary checks
        board.movePlayer(-100, 0);
        board.movePlayer(0, -100);
        board.movePlayer(100, 0);
        board.movePlayer(0, 100);

        assertNotNull(board);
        assertEquals(0, board.getPlayerCol());
        assertEquals(0, board.getPlayerRow());
    }

    @Test
    void testRepeatedMoves() {
        GameBoard board = new GameBoard();

        for (int i = 0; i < 10; i++) {
            board.movePlayer(1, 0);
            board.movePlayer(0, 1);
        }

        assertNotNull(board);
        assertEquals(8, board.getPlayerCol());
        assertEquals(9, board.getPlayerRow());
    }
}