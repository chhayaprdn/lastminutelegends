package ca.sfu.lastminutelegends.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class BoardAssemblerTest {
    
    private BoardAssembler boardAssembler;
    
    @BeforeEach
    void setup() {
        boardAssembler = new BoardAssembler();
    }
    
    @Test
    void assemblerProducesCorrectCells() {
        boardAssembler.onCellParsed('#', 0, 0);
        boardAssembler.onCellParsed('.', 1, 0);
        boardAssembler.onCellParsed('S', 2, 0);
        boardAssembler.onCellParsed('E', 3, 0);
        
        Board board = boardAssembler.getBoard();

        assertInstanceOf(Wall.class, board.getCell(0, 0));
        assertInstanceOf(EmptyCell.class, board.getCell(1, 0));
        assertInstanceOf(StartPoint.class, board.getCell(2, 0));
        assertInstanceOf(EndPoint.class, board.getCell(3, 0));
    }
    
    @Test
    void assemblerProducesEmptyCellsForEntities() {
        boardAssembler.onCellParsed('R', 0, 0);
        boardAssembler.onCellParsed('M', 1, 0);
        boardAssembler.onCellParsed('P', 2, 0);

        Board board = boardAssembler.getBoard();

        assertInstanceOf(EmptyCell.class, board.getCell(0, 0));
        assertInstanceOf(EmptyCell.class, board.getCell(1, 0));
        assertInstanceOf(EmptyCell.class, board.getCell(2, 0));
    }
    
    @Test
    void assemblerProducesErrorOnUnknownChar() {
        var errorContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errorContent));

        boardAssembler.onCellParsed('5', 0, 0);
        assertTrue(errorContent.toString().contains("Unexpected char '5' in board file"));
        
        Board board = boardAssembler.getBoard();
        assertInstanceOf(EmptyCell.class, board.getCell(0, 0)); // Still should set it to an empty cell
        
        System.setErr(System.err);
    }
    
    @Test
    void assemblerHandlesRowsCorrectly() {
        boardAssembler.onCellParsed('#', 0, 0);
        boardAssembler.onCellParsed('.', 1, 0);
        boardAssembler.onCellParsed('.', 0, 1);
        boardAssembler.onCellParsed('#', 1, 1);
        boardAssembler.onCellParsed('#', 0, 2);
        boardAssembler.onCellParsed('#', 1, 2);

        Board board = boardAssembler.getBoard();

        assertInstanceOf(Wall.class, board.getCell(0, 0));
        assertInstanceOf(EmptyCell.class, board.getCell(1, 0));
        assertInstanceOf(EmptyCell.class, board.getCell(0, 1));
        assertInstanceOf(Wall.class, board.getCell(1, 1));
        assertInstanceOf(Wall.class, board.getCell(1, 2));
        assertInstanceOf(Wall.class, board.getCell(1, 2));
    }
    
    @Test
    void assemblerUsesSingletonCells() {
        boardAssembler.onCellParsed('#', 0, 0);
        boardAssembler.onCellParsed('.', 1, 0);
        boardAssembler.onCellParsed('.', 0, 1);
        boardAssembler.onCellParsed('#', 1, 1);

        Board board = boardAssembler.getBoard();

        assertSame(board.getCell(0, 0), board.getCell(1, 1));
        assertSame(board.getCell(1, 0), board.getCell(0, 1));
    }
    
}
