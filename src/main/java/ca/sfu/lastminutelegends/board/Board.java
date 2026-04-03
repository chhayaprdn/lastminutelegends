package ca.sfu.lastminutelegends.board;

import ca.sfu.lastminutelegends.entities.Position;

import java.util.List;

public class Board {
    private final List<List<Cell>> cells;
    private final int width;
    private final int height;
    private Position endPointPos;
    
    Board(List<List<Cell>> cells) {
        this.cells = cells;
        this.height = cells.size();
        this.width = cells.getFirst().size();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (cells.get(y).get(x) instanceof EndPoint)
                    endPointPos = new Position(x, y);
            }
        }
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
    
    public Position getEndPointPos() {
        return endPointPos;
    }
}
