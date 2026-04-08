package ca.sfu.lastminutelegends.systems;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.GameFonts;

import java.awt.*;

public class HudRenderer implements GameSystem {
    public static int TIMER_WIDTH = 200;
    public static int SCORE_WIDTH = 200;
    
    @Override
    public void tick(int tick) {
        
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(GameFonts.headline(24f));
        FontMetrics fm = g.getFontMetrics();
        
        String timer = String.format("TIMER: %d:%02d", Game.instance().getTimer() / 60, Game.instance().getTimer() % 60);
        g.drawString(timer, TIMER_WIDTH / 2 - fm.stringWidth(timer) / 2, Game.instance().getBoardOffsetY() + 25);
        
        String score = String.format("SCORE: %d", Game.instance().getScore());
        g.drawString(score, Game.instance().getCanvasWidth() - SCORE_WIDTH / 2 - fm.stringWidth(score) / 2, Game.instance().getBoardOffsetY() + 25);
    }
}
