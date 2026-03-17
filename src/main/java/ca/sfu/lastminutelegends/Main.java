package ca.sfu.lastminutelegends;

/**
 * Main is the entry point of the maze game application.
 * It creates the main window, sets up the UI,
 * and connects keyboard input to the board panel.
 */
public class Main {

    /**
     * Launches the game application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Game game = Game.instance();
        game.load();
        game.loop();
    }
}