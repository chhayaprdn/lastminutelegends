package ca.sfu.lastminutelegends.ui;

import ca.sfu.lastminutelegends.Game;

import java.awt.*;

public class MainMenuScreen implements Screen {
    @Override
    public void render(Graphics g) {
        int canvasWidth = Game.instance().getCanvasWidth();
        int canvasHeight = Game.instance().getCanvasHeight();
        
        g.setColor(Color.RED);
        g.fillRect(0, 0, canvasWidth, canvasHeight);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 100));
        FontMetrics fm = g.getFontMetrics();
        String firstTitleLine = "LAST-MINUTE";
        String secondTitleLine = "LEGENDS";
        g.drawString(firstTitleLine, canvasWidth / 2 - fm.stringWidth(firstTitleLine) / 2, 350);
        g.drawString(secondTitleLine, canvasWidth / 2 - fm.stringWidth(secondTitleLine) / 2, 350 + fm.getHeight() - 25);
        
        g.setFont(new Font("Arial", Font.PLAIN, 28));
        fm = g.getFontMetrics();
        String enterLine = "Press ENTER to Start";
        g.drawString(enterLine, canvasWidth / 2 - fm.stringWidth(enterLine) / 2, 600);
    }
}
