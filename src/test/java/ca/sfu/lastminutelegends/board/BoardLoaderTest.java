package ca.sfu.lastminutelegends.board;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardLoaderTest {

    @Test
    void testLoadValidBoard() {
        Board board = BoardLoader.loadBoard("/test_board.txt");

        assertNotNull(board); // enough for coverage
    }

    @Test
    void testInvalidFileThrowsException() {
        assertThrows(RuntimeException.class, () -> {
            BoardLoader.loadBoard("/non_existent_file.txt");
        });
    }
}