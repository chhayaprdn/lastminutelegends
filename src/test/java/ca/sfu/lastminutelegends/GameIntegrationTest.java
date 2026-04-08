package ca.sfu.lastminutelegends;

import ca.sfu.lastminutelegends.board.EndPoint;
import ca.sfu.lastminutelegends.board.StartPoint;
import ca.sfu.lastminutelegends.board.Wall;
import ca.sfu.lastminutelegends.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameIntegrationTest {

    private Game game;

    @BeforeEach
    void setup() throws IllegalAccessException, NoSuchFieldException {
        TestUtils.resetGameInstance();
        game = Game.instance();
    }
    
    @Test
    void loadBoardLoadsBoardAndEntitiesCorrectly() {
        game.loadBoard();
        
        assertEquals(19, game.getBoard().getWidth());
        assertEquals(12, game.getBoard().getHeight());
        
        assertInstanceOf(Wall.class, game.getBoard().getCell(18, 11));
        assertInstanceOf(EndPoint.class, game.getBoard().getCell(17, 0));
        assertInstanceOf(StartPoint.class, game.getBoard().getCell(1, 11));
        
        assertEquals(new Position(17, 0), game.getBoard().getEndPointPos());
        
        assertNotNull(game.getPlayer());
        assertEquals(new Position(1, 11), game.getPlayer().getPosition());
        assertTrue(game.getEntities().contains(game.getPlayer()));
        
        List<Position> rewards = new ArrayList<>();
        List<Position> enemies = new ArrayList<>();
        List<Position> punishments = new ArrayList<>();
        
        for (var entity : game.getEntities()) {
            if (entity instanceof RegularReward) {
                rewards.add(entity.getPosition());
            } else if (entity instanceof MovingEnemy) {
                enemies.add(entity.getPosition());
            } else if (entity instanceof Punishment) {
                punishments.add(entity.getPosition());
            }
        }
        
        assertEquals(8, rewards.size());
        assertEquals(2, enemies.size());
        assertEquals(4, punishments.size());
        
        assertTrue(rewards.contains(new Position(1, 1)));
        assertTrue(rewards.contains(new Position(16, 4)));
        
        assertTrue(enemies.contains(new Position(14, 3)));
        assertTrue(enemies.contains(new Position(14, 9)));
        
        assertTrue(punishments.contains(new Position(7, 2)));
        assertTrue(punishments.contains(new Position(3, 9)));
    }
    
    @Test
    void multipleTicks()  {
        game.setCanvas(new GameCanvas());
        game.loadBoard();
        game.loadSystems();
        game.loadRenderers();
        game.setState(GameState.Playing);
        
        var optionalEnemy = game.getEntities().stream().filter(e -> e instanceof MovingEnemy).findFirst();
        assertTrue(optionalEnemy.isPresent());
        Entity enemy = optionalEnemy.get();
        Position prevEnemyPos = enemy.getPosition();
        
        for (int i = 0; i < 20; i++) {
            game.tick();
        }
        
        assertEquals(20, game.getTick());
        assertEquals(2, game.getTimer());
        assertNotNull(game.getPlayer());
        assertEquals(GameState.Playing, game.getState());
        
        assertNotEquals(prevEnemyPos, enemy.getPosition());
    }
    
}
