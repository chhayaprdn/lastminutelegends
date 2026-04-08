package ca.sfu.lastminutelegends.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CellFactoryTest {
    @Test
    void testWallCell() {
        Cell cell = CellFactory.wall();
        assertNotNull(cell);
        assertInstanceOf(Wall.class, CellFactory.wall());
    }

    @Test
    void testEmptyCell() {
        Cell cell = CellFactory.empty();
        assertNotNull(cell);
        assertInstanceOf(EmptyCell.class, CellFactory.empty());
    }

    @Test
    void testStartPoint() {
        Cell cell = CellFactory.startPoint();
        assertNotNull(cell);
        assertInstanceOf(StartPoint.class, CellFactory.startPoint());
    }

    @Test
    void testEndPoint() {
        Cell cell = CellFactory.endPoint();
        assertNotNull(cell);
        assertInstanceOf(EndPoint.class, CellFactory.endPoint());
    }
}