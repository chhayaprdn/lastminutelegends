package ca.sfu.lastminutelegends.board;

import java.util.ArrayList;
import java.util.List;

public class BoardAssembler implements BoardObserver {
    private final List<List<Cell>> cells = new ArrayList<>();
    private int prevY = -1;
    
    @Override
    public void onCellParsed(char c, int _x, int y) {
        if (prevY != y) {
            cells.add(new ArrayList<>());
            prevY = y;
        }
        
        switch (c) {
            case '#' -> cells.getLast().add(CellFactory.wall());
            case 'S' -> cells.getLast().add(CellFactory.startPoint());
            case 'E' -> cells.getLast().add(CellFactory.endPoint());
            case '.', 'R' -> cells.getLast().add(CellFactory.empty());
            default -> {
                System.err.println("Unexpected char '" + c + "' in board file. Defaulting to empty cell.");
                cells.getLast().add(CellFactory.empty());
            }
        }
    }

    /**
     * Assembles a {@link Board} from board read events.
     * 
     * @return the assembled board. Must be called after {@link BoardReader#readBoard()}
     */
    public Board getBoard() {
        return new Board(cells);
    }
}
