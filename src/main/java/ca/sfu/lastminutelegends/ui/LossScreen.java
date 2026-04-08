package ca.sfu.lastminutelegends.ui;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.GameFonts;

import java.awt.*;

public class LossScreen implements Screen {
    @Override
    public void render(Graphics g) {
        int canvasWidth = Game.instance().getCanvas().getCanvasWidth();
        int canvasHeight = Game.instance().getCanvas().getCanvasHeight();
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, canvasWidth, canvasHeight);

        g.setColor(Color.RED);
        g.setFont(GameFonts.headline(100f));
        FontMetrics fm = g.getFontMetrics();
        String lostText = "YOU LOST";
        g.drawString(lostText, canvasWidth / 2 - fm.stringWidth(lostText) / 2, canvasHeight / 2);
        
        g.setColor(Color.GRAY);
        g.setFont(GameFonts.body(28f));
        fm = g.getFontMetrics();
        String timer = String.format("TIMER: %d:%02d", Game.instance().getTimer() / 60, Game.instance().getTimer() % 60);
        String score = String.format("SCORE: %d", Game.instance().getScore());

        g.drawString(timer, canvasWidth / 2 - fm.stringWidth(timer) - 20, canvasHeight / 2 + 60);
        g.drawString(score, canvasWidth / 2 + 20, canvasHeight / 2 + 60);

        String restart = "Press ENTER to Restart";
        g.drawString(restart, canvasWidth / 2 - fm.stringWidth(restart) / 2, canvasHeight / 2 + 120);
    }
}
