package ca.sfu.lastminutelegends;

import javax.swing.JFrame;

public class GameWindow {
    private final JFrame frame;

    public GameWindow(GameCanvas canvas) {
        frame = new JFrame("Last-Minute Legends");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 900);
        frame.add(canvas);
        frame.setLocationRelativeTo(null);
    }

    public void show() {
        frame.setVisible(true);
    }
}