package ca.sfu.lastminutelegends.systems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.GameState;
import ca.sfu.lastminutelegends.TestUtils;
import ca.sfu.lastminutelegends.board.Board;
import ca.sfu.lastminutelegends.entities.BonusReward;
import ca.sfu.lastminutelegends.entities.Player;
import ca.sfu.lastminutelegends.entities.Position;
import ca.sfu.lastminutelegends.entities.RegularReward;

/**
 * Integration tests for the reward collection and score update flow.
 *
 * These tests verify the interaction between rewards (RegularReward, BonusReward),
 * the CollisionDetectionSystem, and the Game score — confirming that the full
 * collection pipeline works correctly end-to-end.
 *
 * Covers:
 * - Player stepping on a regular reward increases score and removes reward
 * - Player stepping on a bonus reward increases score and removes reward
 * - Expired bonus rewards are not collected when stepped on
 * - Collecting all regular rewards and reaching endpoint triggers win
 * - Collecting one reward does not affect other rewards
 * - Score accumulates correctly across multiple collections
 */
public class RewardCollectionIntegrationTest {

    private CollisionDetectionSystem collisionSystem;
    private Board board;

    @BeforeEach
    void setup() throws NoSuchFieldException, IllegalAccessException {
        TestUtils.resetGameInstance();
        Game.instance().setState(GameState.Playing);
        collisionSystem = new CollisionDetectionSystem();
        board = TestUtils.makeBoard("S.E");
        Game.instance().setBoard(board);
    }

    @Test
    void playerSteppingOnRegularRewardIncreasesScore() {
        Player player = new Player(new Position(1, 0));
        RegularReward reward = new RegularReward(new Position(1, 0));

        Game.instance().setPlayer(player);
        Game.instance().getEntities().add(player);
        Game.instance().getEntities().add(reward);

        collisionSystem.tick(0);

        assertEquals(10, Game.instance().getScore());
    }

    @Test
    void playerSteppingOnRegularRewardRemovesItFromBoard() {
        Player player = new Player(new Position(1, 0));
        RegularReward reward = new RegularReward(new Position(1, 0));

        Game.instance().setPlayer(player);
        Game.instance().getEntities().add(player);
        Game.instance().getEntities().add(reward);

        collisionSystem.tick(0);

        assertTrue(Game.instance().getMarkedEntities().contains(reward));
    }

    @Test
    void playerSteppingOnBonusRewardIncreasesScore() {
        Player player = new Player(new Position(1, 0));
        BonusReward bonus = new BonusReward(new Position(1, 0), 25, 10);

        Game.instance().setPlayer(player);
        Game.instance().getEntities().add(player);
        Game.instance().getEntities().add(bonus);

        collisionSystem.tick(0);

        assertEquals(25, Game.instance().getScore());
    }

    @Test
    void expiredBonusRewardIsNotCollectedWhenSteppedOn() {
        Player player = new Player(new Position(1, 0));
        BonusReward bonus = new BonusReward(new Position(1, 0), 25, 1);

        // Manually expire the bonus reward
        bonus.onTick(null, null);
        assertTrue(bonus.isExpired());

        Game.instance().setPlayer(player);
        Game.instance().getEntities().add(player);
        Game.instance().getEntities().add(bonus);

        collisionSystem.tick(0);

        assertEquals(0, Game.instance().getScore());
    }

    @Test
    void collectingOneRewardDoesNotAffectOthers() {
        board = TestUtils.makeBoard("S..E");
        Game.instance().setBoard(board);

        Player player = new Player(new Position(1, 0));
        RegularReward r1 = new RegularReward(new Position(1, 0));
        RegularReward r2 = new RegularReward(new Position(2, 0));

        Game.instance().setPlayer(player);
        Game.instance().getEntities().add(player);
        Game.instance().getEntities().add(r1);
        Game.instance().getEntities().add(r2);

        collisionSystem.tick(0);

        // r1 collected, r2 untouched
        assertTrue(Game.instance().getMarkedEntities().contains(r1));
        assertTrue(Game.instance().getEntities().contains(r2));
        assertFalse(r2.isCollected());
        assertEquals(10, Game.instance().getScore());
    }

    @Test
    void scoreAccumulatesAcrossMultipleCollections() {
        board = TestUtils.makeBoard("S..E");
        Game.instance().setBoard(board);

        Player player = new Player(new Position(1, 0));
        RegularReward r1 = new RegularReward(new Position(1, 0));

        Game.instance().setPlayer(player);
        Game.instance().getEntities().add(player);
        Game.instance().getEntities().add(r1);

        collisionSystem.tick(0); // collect r1 → score = 10

        // Move player to next reward
        BonusReward bonus = new BonusReward(new Position(1, 0), 25, 10);
        Game.instance().getEntities().add(bonus);

        collisionSystem.tick(1); // collect bonus → score = 35

        assertEquals(35, Game.instance().getScore());
    }

    @Test
    void collectingAllRegularRewardsAndReachingEndpointWins() {
        Player player = new Player(new Position(2, 0)); // on endpoint
        RegularReward reward = new RegularReward(new Position(1, 0));

        Game.instance().setPlayer(player);
        Game.instance().getEntities().add(player);
        Game.instance().getEntities().add(reward);

        // Collect the reward first
        reward.collect();
        Game.instance().getEntities().remove(reward);

        collisionSystem.tick(0); // no rewards remain + on endpoint → win

        assertEquals(GameState.Won, Game.instance().getState());
    }

    @Test
    void reachingEndpointWithRemainingRewardsDoesNotWin() {
        Player player = new Player(new Position(2, 0)); // on endpoint
        RegularReward reward = new RegularReward(new Position(1, 0)); // not collected

        Game.instance().setPlayer(player);
        Game.instance().getEntities().add(player);
        Game.instance().getEntities().add(reward);

        collisionSystem.tick(0);

        assertNotEquals(GameState.Won, Game.instance().getState());
    }
}
