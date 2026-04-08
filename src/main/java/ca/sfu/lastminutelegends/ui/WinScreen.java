package ca.sfu.lastminutelegends.ui;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.GameFonts;

import java.awt.*;

public class WinScreen implements Screen {
    @Override
    public void render(Graphics g) {
        int canvasWidth = Game.instance().getCanvasWidth();
        int canvasHeight = Game.instance().getCanvasHeight();

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, canvasWidth, canvasHeight);

        g.setColor(Color.GREEN);
        g.setFont(GameFonts.headline(100f));
        FontMetrics fm = g.getFontMetrics();
        String wonText = "YOU WON";
        g.drawString(wonText, canvasWidth / 2 - fm.stringWidth(wonText) / 2, canvasHeight / 2);

        g.setColor(Color.BLACK);
        g.setFont(GameFonts.body(28f));
        fm = g.getFontMetrics();
        String timer = String.format("TIMER: %d:%02d", Game.instance().getTimer() / 60, Game.instance().getTimer() % 60);
        String score = String.format("SCORE: %d", Game.instance().getScore());

        g.drawString(timer, canvasWidth / 2 - fm.stringWidth(timer) - 20, canvasHeight / 2 + 60);
        g.drawString(score, canvasWidth / 2 + 20, canvasHeight / 2 + 60);
    }
}
