package ca.sfu.lastminutelegends;

import ca.sfu.lastminutelegends.render.HudRenderer;
import ca.sfu.lastminutelegends.ui.GameScreen;
import ca.sfu.lastminutelegends.ui.LossScreen;
import ca.sfu.lastminutelegends.ui.MainMenuScreen;
import ca.sfu.lastminutelegends.ui.WinScreen;

import javax.swing.JPanel;
import java.awt.Graphics;

public class GameCanvas extends JPanel {
    private final MainMenuScreen mainMenuScreen = new MainMenuScreen();
    private final GameScreen gameScreen = new GameScreen();
    private final WinScreen winScreen = new WinScreen();
    private final LossScreen lossScreen = new LossScreen();

    public int getCellSize() {
        int availableWidth = getWidth() - HudRenderer.TIMER_WIDTH - HudRenderer.SCORE_WIDTH;
        return availableWidth / Game.instance().getBoard().getWidth();
    }

    public int getBoardOffsetX() {
        return HudRenderer.TIMER_WIDTH;
    }

    public int getBoardOffsetY() {
        return 50;
    }

    public int getCanvasWidth() {
        return getWidth();
    }

    public int getCanvasHeight() {
        return getHeight();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        switch (Game.instance().getState()) {
            case Menu -> mainMenuScreen.render(g);
            case Playing -> gameScreen.render(g);
            case Won -> winScreen.render(g);
            case Lost -> lossScreen.render(g);
        }
    }
}