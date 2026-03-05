package ca.sfu.lastminutelegends.systems;

import ca.sfu.lastminutelegends.board.Board;
import ca.sfu.lastminutelegends.entities.Direction;
import ca.sfu.lastminutelegends.entities.Player;

import java.awt.*;

/**
 * Applies player movement once per tick using the input direction.
 * This keeps input handling separate from player logic (clean separation of concerns).
 */
public class PlayerSystem implements GameSystem {
    private final Board board;
    private final Player player;
    private final InputSystem input;

    public PlayerSystem(Board board, Player player, InputSystem input) {
        this.board = board;
        this.player = player;
        this.input = input;
    }

    @Override
    public void tick(int tick) {
        Direction dir = input.consumeDirection();
        player.tryMove(dir, board);
    }

    @Override
    public void render(Graphics g) {
        // Rendering is handled by EntityRenderer.
    }
}