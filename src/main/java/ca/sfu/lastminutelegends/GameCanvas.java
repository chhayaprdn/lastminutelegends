package ca.sfu.lastminutelegends;

import ca.sfu.lastminutelegends.systems.GameSystem;

import javax.swing.*;
import java.awt.*;

public class GameCanvas extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        for (GameSystem system : Game.instance().getSystems()) {
            system.render(g);
        }
    }
}
