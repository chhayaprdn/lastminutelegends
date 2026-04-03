package ca.sfu.lastminutelegends.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CellFactoryTest {

    // ===== YOUR TESTS (keep for coverage) =====
    @Test
    void testWallCell() {
        Cell cell = CellFactory.wall();
        assertNotNull(cell);
    }

    @Test
    void testEmptyCell() {
        Cell cell = CellFactory.empty();
        assertNotNull(cell);
    }

    @Test
    void testStartPoint() {
        Cell cell = CellFactory.startPoint();
        assertNotNull(cell);
    }

    @Test
    void testEndPoint() {
        Cell cell = CellFactory.endPoint();
        assertNotNull(cell);
    }

    // ===== MAIN BRANCH TEST =====
    @Test
    void testCellFactoryProducesCorrectCells() {
        assertInstanceOf(Wall.class, CellFactory.wall());
        assertInstanceOf(EmptyCell.class, CellFactory.empty());
        assertInstanceOf(StartPoint.class, CellFactory.startPoint());
        assertInstanceOf(EndPoint.class, CellFactory.endPoint());
    }
}