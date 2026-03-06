package ca.sfu.lastminutelegends;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BoardPanel extends JPanel {
    private static final int ROWS = 10;
    private static final int COLS = 10;
    private static final int CELL_SIZE = 50;

    private int playerRow = 0;
    private int playerCol = 0;

    private int exitRow = 9;
    private int exitCol = 9;

    private int score = 0;

    private JLabel scoreLabel;

    private boolean[][] walls = new boolean[ROWS][COLS];
    private boolean[][] rewards = new boolean[ROWS][COLS];

    public BoardPanel(JLabel scoreLabel) {
        this.scoreLabel = scoreLabel;

        setPreferredSize(new Dimension(COLS * CELL_SIZE, ROWS * CELL_SIZE));
        setBackground(Color.LIGHT_GRAY);

        walls[1][1] = true;
        walls[1][2] = true;
        walls[1][3] = true;
        walls[2][3] = true;
        walls[3][3] = true;
        walls[4][5] = true;
        walls[5][5] = true;
        walls[6][5] = true;
        walls[7][2] = true;
        walls[7][3] = true;

        rewards[0][2] = true;
        rewards[2][2] = true;
        rewards[4][1] = true;
        rewards[6][7] = true;
        rewards[8][8] = true;
    }

    public void movePlayer(int dRow, int dCol) {
        int newRow = playerRow + dRow;
        int newCol = playerCol + dCol;

        if (newRow >= 0 && newRow < ROWS && newCol >= 0 && newCol < COLS && !walls[newRow][newCol]) {
            playerRow = newRow;
            playerCol = newCol;

            if (rewards[playerRow][playerCol]) {
                rewards[playerRow][playerCol] = false;
                score += 10;
                scoreLabel.setText("Score: " + score);
            }

            if (score < 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Game Over!");
                return;
            }

            if (playerRow == exitRow && playerCol == exitCol) {
                if (allRewardsCollected()) {
                    javax.swing.JOptionPane.showMessageDialog(this, "You win!");
                } else {
                    javax.swing.JOptionPane.showMessageDialog(this, "Collect all rewards first!");
                }
                return;
            }

            repaint();
        }
    }

    private boolean allRewardsCollected() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (rewards[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (walls[row][col]) {
                    g.setColor(Color.DARK_GRAY);
                    g.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (rewards[row][col]) {
                    g.setColor(Color.YELLOW);
                    g.fillOval(col * CELL_SIZE + 10, row * CELL_SIZE + 10, 30, 30);
                }
            }
        }

        g.setColor(Color.BLACK);

        for (int row = 0; row <= ROWS; row++) {
            g.drawLine(0, row * CELL_SIZE, COLS * CELL_SIZE, row * CELL_SIZE);
        }

        for (int col = 0; col <= COLS; col++) {
            g.drawLine(col * CELL_SIZE, 0, col * CELL_SIZE, ROWS * CELL_SIZE);
        }

        g.setColor(Color.GREEN);
        g.fillRect(exitCol * CELL_SIZE, exitRow * CELL_SIZE, CELL_SIZE, CELL_SIZE);

        g.setColor(Color.BLUE);
        g.fillRect(playerCol * CELL_SIZE, playerRow * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }
}