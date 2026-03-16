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
    private List<GameSystem> systems;
    private Board board;
    private int tick;
    private Player player;
    private List<Entity> entities = new ArrayList<>();
    private GameState state;
    
    private Game() {
        this.systems = new ArrayList<>();
        this.tick = 0;
        this.state = GameState.Menu;
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
        addSystem(new PlayerSystem(this.board, this.player, inputSystem));
        addSystem(new EnemySystem());
        addSystem(new RewardSystem());
        addSystem(new CollisionDetectionSystem(this.player, this.enemies));
        addSystem(new BoardRenderer(this.board));
        addSystem(new EntityRenderer());
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
}
