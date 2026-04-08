package ca.sfu.lastminutelegends.ui;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.render.GameRenderer;
import ca.sfu.lastminutelegends.systems.GameSystem;

import java.awt.*;

public class GameScreen implements Screen {
    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(0, 0, Game.instance().getCanvas().getCanvasWidth(), Game.instance().getCanvas().getCanvasHeight());
        
        for (GameRenderer renderer : Game.instance().getRenderers()) {
            renderer.render(g);
        }
    }
}
