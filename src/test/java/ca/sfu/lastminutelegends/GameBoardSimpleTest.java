package ca.sfu.lastminutelegends;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameBoardSimpleTest {

    @Test
    void testRewardOnce() {
        GameBoard board = new GameBoard();

        // go to reward at (0,2)
        board.movePlayer(0, 1);
        board.movePlayer(0, 1);

        assertEquals(10, board.getScore());
    }

    @Test
    void testWallPreventsMove() {
        GameBoard board = new GameBoard();

        board.movePlayer(1, 0);
        board.movePlayer(0, 1); // wall

        // should NOT enter wall
        assertEquals(1, board.getPlayerRow());
        assertEquals(0, board.getPlayerCol());
    }
}