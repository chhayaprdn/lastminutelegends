package ca.sfu.lastminutelegends;

import ca.sfu.lastminutelegends.ui.GameScreen;
import ca.sfu.lastminutelegends.ui.LossScreen;
import ca.sfu.lastminutelegends.ui.MainMenuScreen;
import ca.sfu.lastminutelegends.ui.WinScreen;

import javax.swing.*;
import java.awt.*;

public class GameCanvas extends JPanel {
    MainMenuScreen mainMenuScreen = new MainMenuScreen();
    GameScreen gameScreen = new GameScreen();
    WinScreen winScreen = new WinScreen();
    LossScreen lossScreen = new LossScreen();
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        switch (Game.instance().getState()) {
            case GameState.Menu -> mainMenuScreen.render(g);
            case GameState.Playing -> gameScreen.render(g);
            case GameState.Won -> winScreen.render(g);
            case GameState.Lost -> lossScreen.render(g);
        }
    }
}
