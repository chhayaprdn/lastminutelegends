package ca.sfu.lastminutelegends.entities;

import java.awt.*;

public class Punishment extends Entity {
    public Punishment(Position position) {
        super(position);
    }
    
    @Override
    public void render(Graphics g, int cellSize, int offsetX, int offsetY) {
        g.setColor(Color.CYAN);
        g.fillOval(
                offsetX + position.x * cellSize + 10,
                offsetY + position.y * cellSize + 10,
                cellSize - 20,
                cellSize - 20
        );
    }
}
