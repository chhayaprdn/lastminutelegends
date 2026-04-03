package ca.sfu.lastminutelegends.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import ca.sfu.lastminutelegends.Game;

/**
 * Renders the main menu using sizes/positions relative to the canvas so resizing works across platforms.
 */
public class MainMenuScreen implements Screen {
    @Override
    public void render(Graphics g) {
        if (g == null) return;
        int canvasWidth = Game.instance().getCanvasWidth();
        int canvasHeight = Game.instance().getCanvasHeight();

        Graphics2D g2 = (Graphics2D) g.create();
        // HiDPI / smooth text
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.RED);
        g2.fillRect(0, 0, canvasWidth, canvasHeight);

        // Choose font sizes relative to canvas width/height
        int titleSize = Math.max(36, canvasWidth / 10);
        int subtitleSize = Math.max(28, canvasWidth / 20);
        int hintSize = Math.max(14, canvasWidth / 40);

        Font titleFont = new Font("Arial", Font.BOLD, titleSize);
        Font subtitleFont = new Font("Arial", Font.BOLD, titleSize);
        Font hintFont = new Font("Arial", Font.PLAIN, hintSize);

        g2.setColor(Color.WHITE);

        // First title line
        g2.setFont(titleFont);
        FontMetrics fmTitle = g2.getFontMetrics();
        String firstTitleLine = "LAST-MINUTE";
        int xTitle1 = (canvasWidth - fmTitle.stringWidth(firstTitleLine)) / 2;
        int yTitle = (int) (canvasHeight * 0.30); // relative vertical position
        g2.drawString(firstTitleLine, xTitle1, yTitle);

        // Second title line, just below the first
        g2.setFont(subtitleFont);
        FontMetrics fmSubtitle = g2.getFontMetrics();
        String secondTitleLine = "LEGENDS";
        int xTitle2 = (canvasWidth - fmSubtitle.stringWidth(secondTitleLine)) / 2;
        int yTitle2 = yTitle + fmTitle.getHeight();
        g2.drawString(secondTitleLine, xTitle2, yTitle2);

        // Hint line near bottom (relative)
        g2.setFont(hintFont);
        FontMetrics fmHint = g2.getFontMetrics();
        String enterLine = "Press ENTER to Start";
        int xHint = (canvasWidth - fmHint.stringWidth(enterLine)) / 2;
        int yHint = (int) (canvasHeight * 0.75);
        g2.drawString(enterLine, xHint, yHint);

        g2.dispose();
    }
}