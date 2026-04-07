package ca.sfu.lastminutelegends.systems;

import ca.sfu.lastminutelegends.Game;

import ca.sfu.lastminutelegends.entities.Direction;

import java.awt.*;


/**
 * Applies player movement once per tick using the input direction.
 * This keeps input handling separate from player logic (clean separation of concerns).
 */
public class PlayerSystem implements GameSystem {
    private final InputSystem input;

    public PlayerSystem(InputSystem input) {
        this.input = input;
    }

    @Override
    public void tick(int tick) {
        Direction dir = input.consumeDirection();
        Game.instance().getPlayer().tryMove(dir, Game.instance().getBoard());
    }

    @Override
    public void render(Graphics g) {
        // Rendering is handled by EntityRenderer.
    }
}