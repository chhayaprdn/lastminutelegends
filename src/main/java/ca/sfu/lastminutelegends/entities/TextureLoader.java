package ca.sfu.lastminutelegends.entities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TextureLoader {

    public static BufferedImage loadTexture(String path) {
        try {
            return ImageIO.read(TextureLoader.class.getResourceAsStream(path));
        } catch (IOException e) {
            System.err.println("Failed to load texture: " + path);
            return null;
        }
    }
}