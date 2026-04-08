package ca.sfu.lastminutelegends;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.Timer;


import ca.sfu.lastminutelegends.board.Board;
import ca.sfu.lastminutelegends.board.BoardAssembler;
import ca.sfu.lastminutelegends.board.BoardReader;
import ca.sfu.lastminutelegends.entities.Entity;
import ca.sfu.lastminutelegends.entities.EntityPlacer;
import ca.sfu.lastminutelegends.entities.Player;
import ca.sfu.lastminutelegends.render.BoardRenderer;
import ca.sfu.lastminutelegends.render.GameRenderer;
import ca.sfu.lastminutelegends.systems.CollisionDetectionSystem;
import ca.sfu.lastminutelegends.systems.EnemySystem;
import ca.sfu.lastminutelegends.render.EntityRenderer;
import ca.sfu.lastminutelegends.systems.GameSystem;
import ca.sfu.lastminutelegends.render.HudRenderer;
import ca.sfu.lastminutelegends.systems.InputSystem;
import ca.sfu.lastminutelegends.systems.PlayerSystem;
import ca.sfu.lastminutelegends.systems.RewardSystem;
import ca.sfu.lastminutelegends.systems.TimerSystem;

public class Game {
    private static Game INSTANCE = null;

    
    private GameCanvas canvas;
    private Board board;
    private List<GameSystem> systems;
    private List<GameRenderer> renderers;
    private List<Entity> entities;
    private GameState state;
    private int tick;
    private int score;
    private int timer;
    private Player player;

    private Game() {
        this.systems = new ArrayList<>();
        this.renderers = new ArrayList<>();
        this.entities = new ArrayList<>();
        this.state = GameState.Menu;
        this.tick = 0;
        this.score = 0;
        this.timer = 0;
    }

    /** Returns the singleton Game instance, creating it if necessary. */
    public static Game instance() {
        if (INSTANCE == null) {
            INSTANCE = new Game();
        }

        return INSTANCE;
    }

    /** Initializes the Swing window, loads the board, and registers all game systems. */
    public void load() {
        setCanvas(new GameCanvas());

        SwingUtilities.invokeLater(() -> {
            GameWindow window = new GameWindow(this.canvas);
            window.show();
        });

        loadBoard();
        loadSystems();
        loadRenderers();
    }

    /** Starts the tick loop with 100ms interval and render loop with 16ms interval */
    public void loop() {
        Timer tickLoop = new Timer(100, _ -> tick());
        Timer renderLoop = new Timer(16, _ -> canvas.repaint());

        tickLoop.start();
        renderLoop.start();
    }
    
    void tick() {
        if (this.state != GameState.Playing) {
            return;
        }

        for (GameSystem system : this.systems) {
            system.tick(this.tick);
        }

        this.tick++;
    }

    void loadBoard() {
        BoardReader reader = new BoardReader("/board.txt");
        BoardAssembler assembler = new BoardAssembler();

        reader.addObserver(assembler);
        reader.addObserver(new EntityPlacer());
        reader.readBoard();

        this.board = assembler.getBoard();
    }

    void loadSystems() {
        InputSystem inputSystem = new InputSystem(this.canvas);

        addSystem(inputSystem);
        addSystem(new PlayerSystem(inputSystem));
        addSystem(new EnemySystem());
        addSystem(new RewardSystem());
        addSystem(new CollisionDetectionSystem());
        addSystem(new TimerSystem());
    }
    
    void loadRenderers() {
        addRenderer(new BoardRenderer());
        addRenderer(new EntityRenderer());
        addRenderer(new HudRenderer());
    }

    void addSystem(GameSystem system) {
        this.systems.add(system);
    }
    
    void addRenderer(GameRenderer renderer) {
        this.renderers.add(renderer);
    }

    public List<GameSystem> getSystems() {
        return this.systems;
    }
    
    public List<GameRenderer> getRenderers() {
        return this.renderers;
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
    
    public void setBoard(Board board) {
        this.board = board;
    }
    
    void setCanvas(GameCanvas canvas) {
        this.canvas = canvas;
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

    public GameCanvas getCanvas() {
        return canvas;
    }

    /**
     * Adds delta to the player's score. If the resulting score is negative,
     * the game state is set to lost.
     *
     * @param delta points to add (use a negative value to subtract)
     */
    public void addScore(int delta) {
        this.score += delta;

        if (this.score < 0) {
            this.score = 0;
            setState(GameState.Lost);
        }
    }

    public int getTimer() {
        return timer;
    }

    public void incrementTimer() {
        timer++;
    }
    
    public int getTick() {
        return tick;
    }
}
