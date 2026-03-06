package ca.sfu.lastminutelegends;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Last Minute Legends");
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new BorderLayout());

            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
            JLabel scoreLabel = new JLabel("Score: 0");
            JLabel timeLabel = new JLabel("Time: 0");
            final int[] seconds = {0};

	    Timer timer = new Timer(1000, e -> {
    		seconds[0]++;
    		timeLabel.setText("Time: " + seconds[0]);
	    });
	    timer.start();

            topPanel.add(scoreLabel);
            topPanel.add(timeLabel);

            BoardPanel boardPanel = new BoardPanel(scoreLabel);
            boardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

            frame.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent e) {
                    switch (e.getKeyCode()) {
                        case java.awt.event.KeyEvent.VK_UP:
                            boardPanel.movePlayer(-1, 0);
                            break;
                        case java.awt.event.KeyEvent.VK_DOWN:
                            boardPanel.movePlayer(1, 0);
                            break;
                        case java.awt.event.KeyEvent.VK_LEFT:
                            boardPanel.movePlayer(0, -1);
                            break;
                        case java.awt.event.KeyEvent.VK_RIGHT:
                            boardPanel.movePlayer(0, 1);
                            break;
                    }
                }
            });

            frame.add(topPanel, BorderLayout.NORTH);
            frame.add(boardPanel, BorderLayout.CENTER);

            frame.setFocusable(true);
            frame.setVisible(true);
        });
    }
}