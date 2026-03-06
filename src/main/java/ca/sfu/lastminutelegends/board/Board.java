package ca.sfu.lastminutelegends.board;

import java.util.List;

public class Board {
    private final List<List<Cell>> cells;
    private final int width;
    private final int height;
    
    Board(List<List<Cell>> cells) {
        this.cells = cells;
        this.height = cells.size();
        this.width = cells.getFirst().size();
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public Cell getCell(int x, int y) {
        return this.cells.get(y).get(x);
    }
}
