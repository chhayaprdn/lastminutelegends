package ca.sfu.lastminutelegends.systems;

import ca.sfu.lastminutelegends.board.Board;
import ca.sfu.lastminutelegends.entities.MovingEnemy;
import ca.sfu.lastminutelegends.entities.Player;

import java.awt.*;
import java.util.List;

/**
 * Moves all enemies once per tick.
 * For Milestone 1, enemies use simple Manhattan-distance chasing.
 */
public class EnemySystem implements GameSystem {
    private final Board board;
    private final Player player;
    private final List<MovingEnemy> enemies;

    public EnemySystem(Board board, Player player, List<MovingEnemy> enemies) {
        this.board = board;
        this.player = player;
        this.enemies = enemies;
    }

    @Override
    public void tick(int tick) {
        for (MovingEnemy e : enemies) {
            e.moveOneTick(player.getPos(), board);
        }
    }

    @Override
    public void render(Graphics g) {
        // Rendering is handled by EntityRenderer.
    }
}