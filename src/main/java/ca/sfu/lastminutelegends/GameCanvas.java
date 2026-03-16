package ca.sfu.lastminutelegends;

import ca.sfu.lastminutelegends.systems.GameSystem;
import ca.sfu.lastminutelegends.ui.MainMenu;

import javax.swing.*;
import java.awt.*;

public class GameCanvas extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (Game.instance().getState() == GameState.Menu) {
            MainMenu.render(g);
        }
        
        if (Game.instance().getState() == GameState.Playing) {
            for (GameSystem system : Game.instance().getSystems()) {
                system.render(g);
            }
        }

        if (Game.instance().getState() == GameState.Lost) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString("YOU LOST", 550, 120);
        }
    }
}
