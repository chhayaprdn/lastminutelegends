package ca.sfu.lastminutelegends.entities;

import ca.sfu.lastminutelegends.board.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Represents the main character controlled by the player.
 * Movement rules:
 * - At most one tile per tick
 * - Cannot move outside the board
 * - Cannot move into non-passable cells (walls)
 */

public class Player extends MovingEntity {
    private static final BufferedImage UP_TEXTURE;
    private static final BufferedImage DOWN_TEXTURE;
    private static final BufferedImage LEFT_TEXTURE;
    private static final BufferedImage RIGHT_TEXTURE;

    static {
        try {
            UP_TEXTURE = ImageIO.read(BonusReward.class.getResourceAsStream("/textures/playerUp.png"));
            DOWN_TEXTURE = ImageIO.read(BonusReward.class.getResourceAsStream("/textures/playerDown.png"));
            LEFT_TEXTURE = ImageIO.read(BonusReward.class.getResourceAsStream("/textures/playerLeft.png"));
            RIGHT_TEXTURE = ImageIO.read(BonusReward.class.getResourceAsStream("/textures/playerRight.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private Direction lastDir = Direction.UP;
    
    public Player(Position startPos) {
        super(startPos);
    }

    /**
     * Attempt to move the player one tile in the given direction.
     * If the move is invalid (blocked/outside), the player stays put.
     */
    public void tryMove(Direction dir, Board board) {
        if (dir == null) return;

        lastDir = dir;
        
        Position next = position.move(dir);
        if (isWalkable(board, next, true)) {
            position = next;
        }
    }

    @Override
    public void render(Graphics g, int cellSize, int offsetX, int offsetY) {
        BufferedImage texture = UP_TEXTURE;
        
        switch (lastDir) {
            case DOWN -> texture = DOWN_TEXTURE;
            case LEFT -> texture = LEFT_TEXTURE;
            case RIGHT -> texture = RIGHT_TEXTURE;
        }

        g.drawImage(
                texture,
                offsetX + position.x * cellSize,
                offsetY + position.y * cellSize,
                cellSize,
                cellSize,
                null
        );
    }
}