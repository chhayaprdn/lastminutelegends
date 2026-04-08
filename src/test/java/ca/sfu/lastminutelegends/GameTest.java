package ca.sfu.lastminutelegends;

import ca.sfu.lastminutelegends.board.Board;
import ca.sfu.lastminutelegends.systems.GameSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    
    private Game game;
    
    @BeforeEach
    void setup() throws IllegalAccessException, NoSuchFieldException {
        TestUtils.resetGameInstance();
        game = Game.instance();
    }
    
    @Test
    void testAddScore() {
        game.setState(GameState.Playing);
        game.addScore(25);
        
        assertEquals(GameState.Playing, game.getState());
        assertEquals(25, game.getScore());
        
        game.addScore(15);
        game.addScore(-5);
        game.addScore(13);
        game.addScore(-3);

        assertEquals(GameState.Playing, game.getState());
        assertEquals(45, game.getScore());
    }
    
    @Test
    void addScoreOnPointBoundary() {
        game.setState(GameState.Playing);
        game.addScore(0);

        assertEquals(GameState.Playing, game.getState());
        assertEquals(0, game.getScore());
        
        game.addScore(10);
        assertEquals(10, game.getScore());
        
        game.addScore(-10);
        assertEquals(GameState.Playing, game.getState());
        assertEquals(0, game.getScore());
    }
    
    @Test
    void addScoreOffPointBoundary() {
        game.setState(GameState.Playing);
        game.addScore(0);

        assertEquals(GameState.Playing, game.getState());
        assertEquals(0, game.getScore());

        game.addScore(-1);
        assertEquals(GameState.Lost, game.getState());
        assertEquals(0, game.getScore());
    }
    
    @Test
    void addScoreChangesGameStateToLost() {
        game.addScore(20);
        game.addScore(-40);
        
        assertEquals(GameState.Lost, game.getState());
        assertEquals(0, game.getScore());
    }
    
    @Test
    void incrementTimerIncrementsTheTimer() {
        assertEquals(0, game.getTimer());
        game.incrementTimer();
        assertEquals(1, game.getTimer());
        game.incrementTimer();
        game.incrementTimer();
        assertEquals(3, game.getTimer());
    }
    
    @Test
    void startStateIsMenu() {
        assertEquals(GameState.Menu, game.getState());
    }
    
    @Test
    void stateTransitionsToPlaying() {
        assertEquals(GameState.Menu, game.getState());
        game.setState(GameState.Playing);
        assertEquals(GameState.Playing, game.getState());
    }
    
    static class SpySystem implements GameSystem {
        private int tick = 0;
        
        @Override
        public void tick(int tick) {
            this.tick = tick;
        }

        @Override
        public void render(Graphics g) {}
    }
    
    @Test
    void gameSystemsTickEachTick() {
        game.setState(GameState.Playing);
        
        SpySystem system1 = new SpySystem();
        SpySystem system2 = new SpySystem();
        
        game.addSystem(system1);
        game.addSystem(system2);
        
        assertEquals(2, game.getSystems().size());
        
        game.tick();
        
        assertEquals(0, system1.tick);
        assertEquals(0, system2.tick);
        
        game.tick();
        
        assertEquals(1, system1.tick);
        assertEquals(1, system2.tick);
    }
    
    @Test
    void gameSystemsTickOnlyWhenStateIsPlaying() {
        SpySystem system = new SpySystem();
        
        game.addSystem(system);
        
        assertEquals(0, system.tick);
        game.tick();
        game.tick();
        assertEquals(0, system.tick);
        assertEquals(0, game.getTick());

        game.setState(GameState.Won);
        game.tick();
        game.tick();
        assertEquals(0, system.tick);
        assertEquals(0, game.getTick());

        game.setState(GameState.Lost);
        game.tick();
        game.tick();
        assertEquals(0, system.tick);
        assertEquals(0, game.getTick());

        game.setState(GameState.Playing);
        game.tick();
        game.tick();
        assertEquals(1, system.tick);
        assertEquals(2, game.getTick());
    }
    
    @Test
    void testCellSizeCalculation() {
        GameCanvas canvas = new GameCanvas();
        canvas.setSize(1500, 900);
        game.setCanvas(canvas);
        
        Board board = TestUtils.makeBoard("###", "...", "###");
        game.setBoard(board);
        
        // 1500 - 200 - 200 = 1100
        // 1100 / 3 = 366
        assertEquals(366, game.getCanvas().getCellSize());
    }
    
}
