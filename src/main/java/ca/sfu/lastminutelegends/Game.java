package ca.sfu.lastminutelegends;

import ca.sfu.lastminutelegends.board.Board;
import ca.sfu.lastminutelegends.board.BoardLoader;
import ca.sfu.lastminutelegends.systems.BoardRenderer;
import ca.sfu.lastminutelegends.systems.GameSystem;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import ca.sfu.lastminutelegends.entities.MovingEnemy;
import ca.sfu.lastminutelegends.entities.Player;
import ca.sfu.lastminutelegends.entities.Position;
import ca.sfu.lastminutelegends.systems.EnemySystem;
import ca.sfu.lastminutelegends.systems.EntityRenderer;
import ca.sfu.lastminutelegends.systems.InputSystem;
import ca.sfu.lastminutelegends.systems.PlayerSystem;

import java.util.Arrays;

public class Game {
    private static Game INSTANCE = null;
    
    private JFrame frame;
    private GameCanvas canvas;
    private List<GameSystem> systems;
    private Board board;
    private int tick;
    private Player player;
    private List<MovingEnemy> enemies;
    
    private Game() {
        this.systems = new ArrayList<>();
        this.tick = 0;
    }

    public static Game instance() {
        if (INSTANCE == null) {
            INSTANCE = new Game();
        }
        
        return INSTANCE;
    }

    public void load() {
        this.canvas = new GameCanvas();
        
        SwingUtilities.invokeLater(() -> {
            this.frame = new JFrame("Last-Minute Legends");
            this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.frame.setSize(1600, 900);
            this.frame.add(this.canvas);
            this.frame.setLocationRelativeTo(null);
            this.frame.setVisible(true);
        });
        
        this.board = BoardLoader.loadBoard("/board.txt");
        loadSystems();
    }
    
    public void loop() {
        Timer tickLoop = new Timer(100, _ -> {
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

    private void loadSystems() {
        // Temporary spawn positions (we can later auto-detect Start/End from the board)
        this.player = new Player(new Position(1, 6)); // near 'S' in board.txt
        this.enemies = Arrays.asList(
                new MovingEnemy(new Position(7, 1))     // demo enemy spawn
        );

        InputSystem inputSystem = new InputSystem(this.canvas);

        addSystem(new BoardRenderer(this.board));
        addSystem(inputSystem);
        addSystem(new PlayerSystem(this.board, this.player, inputSystem));
        addSystem(new EnemySystem(this.board, this.player, this.enemies));
        addSystem(new EntityRenderer(this.player, this.enemies));
    }
    
    private void addSystem(GameSystem system) {
        this.systems.add(system);
    }
    
    public List<GameSystem> getSystems() {
        return this.systems;
    }
}
