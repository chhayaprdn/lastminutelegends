package ca.sfu.lastminutelegends.ui;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class TextureLoader {
    
    public static BufferedImage load(String path) {
        try {
            return ImageIO.read(Objects.requireNonNull(TextureLoader.class.getResourceAsStream("/textures/" + path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
}
