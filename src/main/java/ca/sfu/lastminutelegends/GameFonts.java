package ca.sfu.lastminutelegends;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

/**
 * UI fonts loaded from bundled OTF resources (Karma family).
 */
public final class GameFonts {

    private static final Font FALLBACK = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
    private static final Font HEADLINE_BASE = load("/fonts/KarmaFuture.otf");
    private static final Font BODY_BASE = load("/fonts/KarmaSuture.otf");

    private GameFonts() {
    }

    private static Font load(String resourcePath) {
        try (InputStream in = GameFonts.class.getResourceAsStream(resourcePath)) {
            if (in == null) {
                return FALLBACK;
            }
            return Font.createFont(Font.TRUETYPE_FONT, in);
        } catch (FontFormatException | IOException e) {
            return FALLBACK;
        }
    }

    /** Bold display style (e.g. titles, HUD labels). */
    public static Font headline(float size) {
        return HEADLINE_BASE.deriveFont(Font.BOLD, size);
    }

    /** Regular body style (e.g. subtitles, stats). */
    public static Font body(float size) {
        return BODY_BASE.deriveFont(Font.PLAIN, size);
    }
}
