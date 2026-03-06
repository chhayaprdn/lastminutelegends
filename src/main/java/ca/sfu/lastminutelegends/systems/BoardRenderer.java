package ca.sfu.lastminutelegends.systems;

import ca.sfu.lastminutelegends.board.*;

import java.awt.*;

public class BoardRenderer implements GameSystem {
    private final Board board;
    
    public BoardRenderer(Board board) {
        this.board = board;
    }
    
    @Override
    public void tick(int tick) {
        
    }

    @Override
    public void render(Graphics g) {
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                Cell cell = board.getCell(x, y);

                switch (cell) {
                    case Wall _ -> g.setColor(Color.DARK_GRAY);
                    case StartPoint _ -> g.setColor(Color.BLUE);
                    case EndPoint _ -> g.setColor(Color.GREEN);
                    case null, default -> g.setColor(Color.LIGHT_GRAY);
                }
                
                g.fillRect(50 + x * 50, 50 + y * 50, 50, 50);
            }
        }
    }
}
