package ca.sfu.lastminutelegends.ui;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.GameFonts;

import java.awt.*;

public class MainMenuScreen implements Screen {
    @Override
    public void render(Graphics g) {
        int canvasWidth = Game.instance().getCanvasWidth();
        int canvasHeight = Game.instance().getCanvasHeight();
        
        g.setColor(Color.RED);
        g.fillRect(0, 0, canvasWidth, canvasHeight);
        
        g.setColor(Color.WHITE);
        String title = "LAST-MINUTE LEGENDS";
        g.setFont(GameFonts.headline(48f));
        FontMetrics fm = g.getFontMetrics();
        g.drawString(title, canvasWidth / 2 - fm.stringWidth(title) / 2, 350);
        
        g.setFont(GameFonts.body(28f));
        fm = g.getFontMetrics();
        String enterLine = "Press ENTER to Start";
        g.drawString(enterLine, canvasWidth / 2 - fm.stringWidth(enterLine) / 2, 600);
    }
}
