package ca.sfu.lastminutelegends.entities;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.ui.TextureLoader;

import java.awt.image.BufferedImage;

public class Punishment extends Entity {
    private static final BufferedImage TEXTURE;

    static {
        TEXTURE = TextureLoader.load("punishment.png");
    }
    
    public Punishment(Position position) {
        super(position);
    }

    @Override
    public void onCollideWithPlayer() {
        super.onCollideWithPlayer();

        Game.instance().addScore(-25);
        markForDeletion();
    }

    @Override
    protected BufferedImage getTexture() {
        return TEXTURE;
    }
}
