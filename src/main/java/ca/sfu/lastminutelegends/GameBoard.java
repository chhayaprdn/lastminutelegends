package ca.sfu.lastminutelegends;

/**
 * GameBoard stores the game state for the maze game.
 * It keeps track of the player position, exit location,
 * wall cells, reward cells, and current score.
 * It also handles player movement and reward collection.
 */
public class GameBoard {
    public static final int ROWS = 10;
    public static final int COLS = 10;

    private int playerRow = 0;
    private int playerCol = 0;

    private int exitRow = 9;
    private int exitCol = 9;

    private int score = 0;

    private boolean[][] walls = new boolean[ROWS][COLS];
    private boolean[][] rewards = new boolean[ROWS][COLS];

    /**
     * Creates a new game board and initializes
     * the walls and reward positions.
     */
    public GameBoard() {
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

    /**
     * Moves the player if the target cell is inside the board
     * and is not blocked by a wall. If the player lands on a reward,
     * the reward is collected and the score increases.
     *
     * @param dRow change in row
     * @param dCol change in column
     */
    public void movePlayer(int dRow, int dCol) {
        int newRow = playerRow + dRow;
        int newCol = playerCol + dCol;

        if (newRow >= 0 && newRow < ROWS && newCol >= 0 && newCol < COLS && !walls[newRow][newCol]) {
            playerRow = newRow;
            playerCol = newCol;

            if (rewards[playerRow][playerCol]) {
                rewards[playerRow][playerCol] = false;
                score += 10;
            }
        }
    }

    /**
     * Checks whether all rewards on the board have been collected.
     *
     * @return true if no rewards remain, false otherwise
     */
    public boolean allRewardsCollected() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (rewards[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks whether the player is currently on the exit cell.
     *
     * @return true if the player is at the exit, false otherwise
     */
    public boolean isAtExit() {
        return playerRow == exitRow && playerCol == exitCol;
    }

    /**
     * Returns the player's current row.
     *
     * @return player row
     */
    public int getPlayerRow() {
        return playerRow;
    }

    /**
     * Returns the player's current column.
     *
     * @return player column
     */
    public int getPlayerCol() {
        return playerCol;
    }

    /**
     * Returns the exit row.
     *
     * @return exit row
     */
    public int getExitRow() {
        return exitRow;
    }

    /**
     * Returns the exit column.
     *
     * @return exit column
     */
    public int getExitCol() {
        return exitCol;
    }

    /**
     * Returns the current score.
     *
     * @return current score
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the wall layout of the board.
     *
     * @return 2D array of wall cells
     */
    public boolean[][] getWalls() {
        return walls;
    }

    /**
     * Returns the reward layout of the board.
     *
     * @return 2D array of reward cells
     */
    public boolean[][] getRewards() {
        return rewards;
    }
}