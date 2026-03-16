package ca.sfu.lastminutelegends;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * BoardPanel is responsible for drawing the maze game board.
 * It displays the player, walls, rewards, and exit,
 * and forwards movement commands to the GameBoard.
 */
public class BoardPanel extends JPanel {
    private static final int CELL_SIZE = 50;

    private final GameBoard board;
    private final JLabel scoreLabel;

    private boolean gameOver = false;

    /**
     * Creates a board panel using the given game board and score label.
     *
     * @param board the game board storing game state
     * @param scoreLabel the label used to display the score
     */
    public BoardPanel(GameBoard board, JLabel scoreLabel) {
        this.board = board;
        this.scoreLabel = scoreLabel;

        setPreferredSize(new Dimension(GameBoard.COLS * CELL_SIZE, GameBoard.ROWS * CELL_SIZE));
        setBackground(Color.LIGHT_GRAY);
    }

    /**
     * Moves the player on the board and updates the UI.
     * If the player reaches the exit with all rewards collected,
     * the game displays a win message and stops further movement.
     *
     * @param dRow change in row
     * @param dCol change in column
     */
    public void movePlayer(int dRow, int dCol) {
        if (gameOver) {
            return;
        }

        board.movePlayer(dRow, dCol);
        scoreLabel.setText("Score: " + board.getScore());

        if (board.isAtExit()) {
            if (board.allRewardsCollected()) {
                javax.swing.JOptionPane.showMessageDialog(this, "You win!");
                gameOver = true;
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Collect all rewards first!");
            }
            return;
        }

        repaint();
    }

    /**
     * Draws the full board including walls, rewards, grid lines,
     * exit cell, and player.
     *
     * @param g the graphics context used for drawing
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        boolean[][] walls = board.getWalls();
        boolean[][] rewards = board.getRewards();

        for (int row = 0; row < GameBoard.ROWS; row++) {
            for (int col = 0; col < GameBoard.COLS; col++) {
                if (walls[row][col]) {
                    g.setColor(Color.DARK_GRAY);
                    g.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }

        for (int row = 0; row < GameBoard.ROWS; row++) {
            for (int col = 0; col < GameBoard.COLS; col++) {
                if (rewards[row][col]) {
                    g.setColor(Color.YELLOW);
                    g.fillOval(col * CELL_SIZE + 10, row * CELL_SIZE + 10, 30, 30);
                }
            }
        }

        g.setColor(Color.BLACK);

        for (int row = 0; row <= GameBoard.ROWS; row++) {
            g.drawLine(0, row * CELL_SIZE, GameBoard.COLS * CELL_SIZE, row * CELL_SIZE);
        }

        for (int col = 0; col <= GameBoard.COLS; col++) {
            g.drawLine(col * CELL_SIZE, 0, col * CELL_SIZE, GameBoard.ROWS * CELL_SIZE);
        }

        g.setColor(Color.GREEN);
        g.fillRect(board.getExitCol() * CELL_SIZE, board.getExitRow() * CELL_SIZE, CELL_SIZE, CELL_SIZE);

        g.setColor(Color.BLUE);
        g.fillRect(board.getPlayerCol() * CELL_SIZE, board.getPlayerRow() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }
}