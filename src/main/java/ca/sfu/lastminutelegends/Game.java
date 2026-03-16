package ca.sfu.lastminutelegends;

import ca.sfu.lastminutelegends.board.Board;
import ca.sfu.lastminutelegends.board.BoardAssembler;
import ca.sfu.lastminutelegends.board.BoardReader;
import ca.sfu.lastminutelegends.entities.*;
import ca.sfu.lastminutelegends.systems.*;
import ca.sfu.lastminutelegends.systems.BoardRenderer;
import ca.sfu.lastminutelegends.systems.CollisionDetectionSystem;
import ca.sfu.lastminutelegends.systems.GameSystem;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private static Game INSTANCE = null;
    
    private JFrame frame;
    private GameCanvas canvas;
    private Board board;
    private List<GameSystem> systems;
    private List<Entity> entities;
    private GameState state;
    private int tick;
    private int score;
    private int timer;
    private Player player;
    
    private Game() {
        this.systems = new ArrayList<>();
        this.entities = new ArrayList<>();
        this.state = GameState.Menu;
        this.tick = 0;
        this.score = 0;
        this.timer = 0;
    }

    public static Game instance() {
        if (INSTANCE == null) {
            INSTANCE = new Game();
        }
        
        return INSTANCE;
    }

    public void load() {
        this.canvas = new GameCanvas();
        this.state = GameState.Playing;
        
        SwingUtilities.invokeLater(() -> {
            this.frame = new JFrame("Last-Minute Legends");
            this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.frame.setSize(1600, 900);
            this.frame.add(this.canvas);
            this.frame.setLocationRelativeTo(null);
            this.frame.setVisible(true);
        });
        
        loadBoard();
        loadSystems();
    }
    
    public void loop() {
        Timer tickLoop = new Timer(100, _ -> {
            if (this.state != GameState.Playing) {
                return;
            }

            for (GameSystem system : this.systems) {
                system.tick(this.tick);
            }
            
            this.tick++;
        });
        
        Timer renderLoop = new Timer(16, _ -> {
            this.canvas.repaint();
        });
        
        tickLoop.start();
        renderLoop.start();
    }

    private void loadBoard() {
        BoardReader reader = new BoardReader("/board.txt");
        BoardAssembler assembler = new BoardAssembler();
        
        reader.addObserver(assembler);
        reader.addObserver(new EntityPlacer());
        reader.readBoard();
        
        this.board = assembler.getBoard();
    }
    
    private void loadSystems() {
        InputSystem inputSystem = new InputSystem(this.canvas);

        addSystem(inputSystem);
        addSystem(new PlayerSystem(inputSystem));
        addSystem(new EnemySystem());
        addSystem(new RewardSystem());
        addSystem(new CollisionDetectionSystem());
        addSystem(new TimerSystem());
        addSystem(new BoardRenderer());
        addSystem(new EntityRenderer());
        addSystem(new HudRenderer());
    }
    
    private void addSystem(GameSystem system) {
        this.systems.add(system);
    }
    
    public List<GameSystem> getSystems() {
        return this.systems;
    }

    public GameState getState() {
        return this.state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public Board getBoard() {
        return board;
    }
    
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public Player getPlayer() {
        return player;
    }

    public List<Entity> getEntities() {
        return entities;
    }
    
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }

    public int getTimer() {
        return timer;
    }
    
    public void incrementTimer() {
        timer++;
    }
    
    public int getCellSize() {
        int availableWidth = canvas.getWidth() - HudRenderer.TIMER_WIDTH - HudRenderer.SCORE_WIDTH;
        
        return availableWidth / board.getWidth();
    }
    
    public int getBoardOffsetX() {
        return HudRenderer.TIMER_WIDTH;
    }
    
    public int getBoardOffsetY() {
        return 50;
    }
    
    public int getCanvasWidth() {
        return canvas.getWidth();
    }
}
