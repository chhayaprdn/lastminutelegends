package ca.sfu.lastminutelegends.systems;

import ca.sfu.lastminutelegends.Game;

import java.awt.*;

public class HudRenderer implements GameSystem {
    public static int TIMER_WIDTH = 200;
    public static int SCORE_WIDTH = 200;
    
    @Override
    public void tick(int tick) {
        
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        
        String timer = String.format("TIMER: %d:%02d", Game.instance().getTimer() / 60, Game.instance().getTimer() % 60);
        g.drawString(timer, 20, Game.instance().getBoardOffsetY() + 25);
        
        String score = String.format("SCORE: %d", Game.instance().getScore());
        g.drawString(score, Game.instance().getCanvasWidth() - 150, Game.instance().getBoardOffsetY() + 25);
    }
}
