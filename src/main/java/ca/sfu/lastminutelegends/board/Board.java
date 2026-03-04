package ca.sfu.lastminutelegends.board;

import java.util.List;

public class Board {
    private List<List<Cell>> cells;
    private int width;
    private int height;
    
    Board(List<List<Cell>> cells) {
        this.cells = cells;
        this.height = cells.size();
        this.width = cells.getFirst().size();
    }
}
