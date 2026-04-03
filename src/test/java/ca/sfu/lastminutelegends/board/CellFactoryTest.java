package ca.sfu.lastminutelegends.board;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CellFactoryTest {

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
}