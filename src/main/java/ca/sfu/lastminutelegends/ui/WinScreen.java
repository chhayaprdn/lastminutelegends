package ca.sfu.lastminutelegends.ui;

import ca.sfu.lastminutelegends.Game;

import java.awt.*;

public class WinScreen implements Screen {
    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        int canvasWidth = Game.instance().getCanvas().getCanvasWidth();
        int canvasHeight = Game.instance().getCanvas().getCanvasHeight();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        GradientPaint background = new GradientPaint(
                0, 0, new Color(30, 30, 47),
                0, canvasHeight, new Color(15, 23, 42)
        );
        g2d.setPaint(background);
        g2d.fillRect(0, 0, canvasWidth, canvasHeight);

        String wonText = "YOU WON";
        Font titleFont = new Font("Arial", Font.BOLD, 100);
        g2d.setFont(titleFont);
        FontMetrics titleMetrics = g2d.getFontMetrics();

        int titleX = canvasWidth / 2 - titleMetrics.stringWidth(wonText) / 2;
        int titleY = canvasHeight / 2;

        g2d.setColor(new Color(34, 197, 94, 70));
        g2d.drawString(wonText, titleX - 2, titleY - 2);
        g2d.drawString(wonText, titleX + 2, titleY - 2);
        g2d.drawString(wonText, titleX - 2, titleY + 2);
        g2d.drawString(wonText, titleX + 2, titleY + 2);

        g2d.setColor(new Color(0, 0, 0, 120));
        g2d.drawString(wonText, titleX + 6, titleY + 6);
        g2d.setColor(new Color(0, 0, 0, 90));
        g2d.drawString(wonText, titleX + 3, titleY + 3);

        g2d.setColor(new Color(34, 197, 94));
        g2d.drawString(wonText, titleX, titleY);

        Font infoFont = new Font("Arial", Font.PLAIN, 28);
        g2d.setFont(infoFont);
        FontMetrics infoMetrics = g2d.getFontMetrics();

        String timer = String.format("TIMER: %d:%02d",
                Game.instance().getTimer() / 60,
                Game.instance().getTimer() % 60);
        String score = String.format("SCORE: %d", Game.instance().getScore());

        int infoY = canvasHeight / 2 + 60;
        int timerX = canvasWidth / 2 - infoMetrics.stringWidth(timer) - 20;
        int scoreX = canvasWidth / 2 + 20;

        g2d.setColor(new Color(0, 0, 0, 110));
        g2d.drawString(timer, timerX + 2, infoY + 2);
        g2d.drawString(score, scoreX + 2, infoY + 2);

        g2d.setColor(new Color(229, 231, 235));
        g2d.drawString(timer, timerX, infoY);
        g2d.drawString(score, scoreX, infoY);

        String restart = "Press ENTER to Restart";
        int restartX = canvasWidth / 2 - infoMetrics.stringWidth(restart) / 2;
        int restartY = canvasHeight / 2 + 120;
        g2d.setColor(new Color(0, 0, 0, 110));
        g2d.drawString(restart, restartX + 2, restartY + 2);
        g2d.setColor(new Color(229, 231, 235));
        g2d.drawString(restart, restartX, restartY);
    }
}
