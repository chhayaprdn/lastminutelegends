package ca.sfu.lastminutelegends.board;

import ca.sfu.lastminutelegends.TestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    private static Board board;
    
    @BeforeAll
    public static void setup() {
        board = TestUtils.makeBoard("#.S", ".#.", "..E", "###");
    }
    
    @Test
    void testBoardHasCorrectDimensions() {
        assertEquals(3, board.getWidth());
        assertEquals(4, board.getHeight());
    }

    @Test
    void testBoardGetCell() {
        assertInstanceOf(Wall.class, board.getCell(0, 0));
        assertInstanceOf(EmptyCell.class, board.getCell(1, 0));
        assertInstanceOf(StartPoint.class, board.getCell(2, 0));
        assertInstanceOf(EndPoint.class, board.getCell(2, 2));
        assertInstanceOf(Wall.class, board.getCell(1, 3));

        assertThrows(IndexOutOfBoundsException.class, () -> board.getCell(5, 4));
        assertThrows(IndexOutOfBoundsException.class, () -> board.getCell(0, 6));
    }

    @Test
    void testCellPassability() {
        assertFalse(board.getCell(0, 0).isPassable());
        assertTrue(board.getCell(1, 0).isPassable());
        assertTrue(board.getCell(2, 0).isPassable());
        assertTrue(board.getCell(2, 2).isPassable());
        assertFalse(board.getCell(1, 3).isPassable());
    }

    @Test
    void testBoardEndPointPos() {
        assertEquals(2, board.getEndPointPos().x);
        assertEquals(2, board.getEndPointPos().y);
    }

    @Test
    void testBoardEndPointPosWithNoEndPoint() {
        Board boardWithNoEndPoint = TestUtils.makeBoard(".#", "S.");

        assertNull(boardWithNoEndPoint.getEndPointPos());
    }
    
}
