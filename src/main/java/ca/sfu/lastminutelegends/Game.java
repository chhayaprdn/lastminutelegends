package ca.sfu.lastminutelegends;

import ca.sfu.lastminutelegends.systems.GameSystem;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private static Game INSTANCE = null;
    
    private JFrame frame;
    private GameCanvas canvas;
    private List<GameSystem> systems;
    private int tick;
    
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
        SwingUtilities.invokeLater(() -> {
            this.frame = new JFrame("Last-Minute Legends");
            this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.frame.setSize(1600, 900);
            this.canvas = new GameCanvas();
            this.frame.add(this.canvas);
            this.frame.setLocationRelativeTo(null);
            this.frame.setVisible(true);
        });
        
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
        
    }
    
    private void addSystem(GameSystem system) {
        this.systems.add(system);
    }
    
    public List<GameSystem> getSystems() {
        return this.systems;
    }
}
