package ca.sfu.lastminutelegends.systems;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.GameState;
import ca.sfu.lastminutelegends.board.Board;
import ca.sfu.lastminutelegends.board.BoardAssembler;
import ca.sfu.lastminutelegends.entities.MovingEnemy;
import ca.sfu.lastminutelegends.entities.Player;
import ca.sfu.lastminutelegends.entities.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests for enemy movement and collision handling.
 *
 * These tests verify the interaction between:
 * - EnemySystem
 * - CollisionDetectionSystem
 * - MovingEnemy
 * - Player
 * - Game state transitions
 */
public class EnemyCollisionIntegrationTest {

    private EnemySystem enemySystem;
    private CollisionDetectionSystem collisionSystem;

    /**
     * Resets shared game state before each test.
     */
    @BeforeEach
    void setup() {
        Game.instance().getEntities().clear();
        Game.instance().setState(GameState.Playing);
        Game.instance().setPlayer(null);
        Game.instance().setBoard(null);

        enemySystem = new EnemySystem();
        collisionSystem = new CollisionDetectionSystem();
    }

    /**
     * Utility method to construct a board from string rows.
     */
    private Board makeBoard(String... rows) {
        BoardAssembler assembler = new BoardAssembler();

        for (int y = 0; y < rows.length; y++) {
            char[] chars = rows[y].toCharArray();
            for (int x = 0; x < chars.length; x++) {
                assembler.onCellParsed(chars[x], x, y);
            }
        }

        return assembler.getBoard();
    }

    /**
     * Verifies that when the enemy reaches the player through normal system ticks,
     * the collision system changes the game state to LOST.
     */
    @Test
    void enemyReachesPlayerAndGameBecomesLost() {
        Board board = makeBoard("...");
        Game.instance().setBoard(board);

        Player player = new Player(new Position(2, 0));
        MovingEnemy enemy = new MovingEnemy(new Position(1, 0));

        Game.instance().setPlayer(player);
        Game.instance().getEntities().add(player);
        Game.instance().getEntities().add(enemy);

        enemySystem.tick(5);
        collisionSystem.tick(5);

        assertEquals(GameState.Lost, Game.instance().getState());
    }

    /**
     * Verifies that if the enemy has not yet reached the player,
     * the game remains in the PLAYING state.
     */
    @Test
    void enemyDoesNotReachPlayerAndGameRemainsPlaying() {
        Board board = makeBoard("....");
        Game.instance().setBoard(board);

        Player player = new Player(new Position(3, 0));
        MovingEnemy enemy = new MovingEnemy(new Position(0, 0));

        Game.instance().setPlayer(player);
        Game.instance().getEntities().add(player);
        Game.instance().getEntities().add(enemy);

        enemySystem.tick(5);
        collisionSystem.tick(5);

        assertEquals(GameState.Playing, Game.instance().getState());
    }
}