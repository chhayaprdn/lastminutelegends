package ca.sfu.lastminutelegends.board;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardReaderIntegrationTest {
    
    static class SpyBoardObserver implements BoardObserver {
        List<String> parsedCells = new ArrayList<>();
        
        @Override
        public void onCellParsed(char c, int x, int y) {
            parsedCells.add(c + "," + x + "," + y);
        }
    }
    
    @Test
    void observerGetsCorrectEvents() {
        SpyBoardObserver observer = new SpyBoardObserver();
        BoardReader reader = new BoardReader("/small_test_board.txt");
        reader.addObserver(observer);
        reader.readBoard();
        
        assertEquals(List.of("#,0,0", ".,1,0", "S,0,1", "E,1,1"), observer.parsedCells);
    }
    
    @Test
    void multipleObserversGetCorrectEvents() {
        SpyBoardObserver observer1 = new SpyBoardObserver();
        SpyBoardObserver observer2 = new SpyBoardObserver();
        SpyBoardObserver observer3 = new SpyBoardObserver();
        BoardReader reader = new BoardReader("/small_test_board.txt");
        
        reader.addObserver(observer1);
        reader.addObserver(observer2);
        reader.addObserver(observer3);
        
        reader.readBoard();

        assertEquals(List.of("#,0,0", ".,1,0", "S,0,1", "E,1,1"), observer1.parsedCells);
        assertEquals(observer1.parsedCells, observer2.parsedCells);
        assertEquals(observer2.parsedCells, observer3.parsedCells);
    }
    
    @Test
    void boardReaderThrowsOnMissingFile() {
        BoardReader reader = new BoardReader("/nonexistent_board_file.txt");
        
        assertThrows(RuntimeException.class, reader::readBoard);
    }
    
    
    
}
