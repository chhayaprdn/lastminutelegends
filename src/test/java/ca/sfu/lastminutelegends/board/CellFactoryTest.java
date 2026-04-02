package ca.sfu.lastminutelegends.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CellFactoryTest {
    
    @Test
    void testCellFactoryProducesCorrectCells() {
        assertInstanceOf(Wall.class, CellFactory.wall());
        assertInstanceOf(EmptyCell.class, CellFactory.empty());
        assertInstanceOf(StartPoint.class, CellFactory.startPoint());
        assertInstanceOf(EndPoint.class, CellFactory.endPoint());
    }
    
}
