package ca.sfu.lastminutelegends.systems;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.GameState;
import ca.sfu.lastminutelegends.board.Board;
import ca.sfu.lastminutelegends.board.BoardAssembler;
import ca.sfu.lastminutelegends.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CollisionDetectionSystem.
 *
 * These tests verify:
 * - Player collision with enemies (lose condition)
 * - Player collision with punishments (score decrease)
 * - Player collision with rewards (collection and score increase)
 * - End point logic (win condition)
 */
public class CollisionDetectionSystemTest {

    private CollisionDetectionSystem system;

    /**
     * Resets shared game state before each test.
     */
    @BeforeEach
    void setup() {
        Game.instance().getEntities().clear();
        Game.instance().setState(GameState.Playing);
        Game.instance().setPlayer(null);
        Game.instance().setBoard(null);
        system = new CollisionDetectionSystem();
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
     * Player colliding with an enemy should immediately set the game state to LOST.
     */
    @Test
    void playerCollisionWithEnemyCausesLoss() {
        Board board = makeBoard("...");
        Game.instance().setBoard(board);

        Player player = new Player(new Position(1, 0));
        MovingEnemy enemy = new MovingEnemy(new Position(1, 0));

        Game.instance().setPlayer(player);
        Game.instance().getEntities().add(player);
        Game.instance().getEntities().add(enemy);

        system.tick(0);

        assertEquals(GameState.Lost, Game.instance().getState());
    }

    /**
     * Player colliding with punishment should decrease score and remove punishment.
     */
    @Test
    void playerCollisionWithPunishmentReducesScore() {
        Board board = makeBoard("...");
        Game.instance().setBoard(board);

        Player player = new Player(new Position(1, 0));
        Punishment punishment = new Punishment(new Position(1, 0));

        Game.instance().setPlayer(player);
        Game.instance().getEntities().add(player);
        Game.instance().getEntities().add(punishment);

        system.tick(0);

        assertEquals(0, Game.instance().getScore());
        assertFalse(Game.instance().getEntities().contains(punishment));
    }

    /**
     * Player colliding with a regular reward should collect it and increase score.
     */
    @Test
    void playerCollectsRewardAndScoreIncreases() {
        Board board = makeBoard("...");
        Game.instance().setBoard(board);

        Player player = new Player(new Position(1, 0));
        RegularReward reward = new RegularReward(new Position(1, 0));

        Game.instance().setPlayer(player);
        Game.instance().getEntities().add(player);
        Game.instance().getEntities().add(reward);

        system.tick(0);

        assertEquals(10, Game.instance().getScore());
        assertFalse(Game.instance().getEntities().contains(reward));
    }

    /**
     * Expired bonus rewards should not be collected.
     */
    @Test
    void expiredBonusRewardIsNotCollected() {
        Board board = makeBoard("...");
        Game.instance().setBoard(board);

        Player player = new Player(new Position(1, 0));
        BonusReward bonus = new BonusReward(new Position(1, 0), 50, 1);

        bonus.onTick(board, player);

        Game.instance().setPlayer(player);
        Game.instance().getEntities().add(player);
        Game.instance().getEntities().add(bonus);

        system.tick(0);

        assertEquals(0, Game.instance().getScore());
        assertTrue(Game.instance().getEntities().contains(bonus));
    }

    /**
     * Player reaching endpoint while regular rewards remain should not win.
     */
    @Test
    void playerDoesNotWinIfRewardsRemain() {
        Board board = makeBoard("S.E");
        Game.instance().setBoard(board);

        Player player = new Player(new Position(2, 0));
        RegularReward reward = new RegularReward(new Position(0, 0));

        Game.instance().setPlayer(player);
        Game.instance().getEntities().add(player);
        Game.instance().getEntities().add(reward);

        system.tick(0);

        assertNotEquals(GameState.Won, Game.instance().getState());
    }

    /**
     * Player reaching endpoint with no regular rewards remaining should win the game.
     */
    @Test
    void playerWinsIfNoRewardsRemain() {
        Board board = makeBoard("S.E");
        Game.instance().setBoard(board);

        Player player = new Player(new Position(2, 0));

        Game.instance().setPlayer(player);
        Game.instance().getEntities().add(player);

        system.tick(0);

        assertEquals(GameState.Won, Game.instance().getState());
    }
}