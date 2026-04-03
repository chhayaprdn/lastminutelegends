package ca.sfu.lastminutelegends.board;

import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    void testBoardCreation() {
        List<List<Cell>> cells = new ArrayList<>();

        List<Cell> row = new ArrayList<>();
        row.add(CellFactory.empty());
        row.add(CellFactory.wall());

        cells.add(row);

        Board board = new Board(cells);

        assertNotNull(board);
    }

    @Test
    void testBoardFromLoader() {
        Board board = BoardLoader.loadBoard("/test_board.txt");

        assertNotNull(board);
    }
}