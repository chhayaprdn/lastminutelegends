package ca.sfu.lastminutelegends.reward;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import ca.sfu.lastminutelegends.entities.Position;

/**
 * Regular rewards (School Supplies)
 * Must be collected to win the game
 */
public class RegularReward extends Reward {
    
    public RegularReward(Position position) {
        super(position, "R", 10); // 10 points for regular rewards
    }
    
    @Override
    public void onTick() {
        // Regular rewards don't do anything on ticks
    }
    
    @Override
    public void render(Graphics g, int cellSize, int offsetX, int offsetY) {
        if (collected) return;
    
        // Use position.x and position.y (not getX()/getY())
        int x = offsetX + position.x * cellSize + cellSize / 4;
        int y = offsetY + position.y * cellSize + cellSize / 4;
        int size = cellSize / 2;

        // Green for school supplies
        g.setColor(Color.GREEN);
        g.fillRect(x, y, size, size);
    
        // Draw R symbol
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.drawString("R", x + size/4, y + size/2);
    }
    
}