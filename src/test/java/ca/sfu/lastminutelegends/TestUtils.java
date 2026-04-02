package ca.sfu.lastminutelegends;

import ca.sfu.lastminutelegends.board.Board;
import ca.sfu.lastminutelegends.board.BoardAssembler;

import java.lang.reflect.Field;

public class TestUtils {

    public static void resetGameInstance() throws IllegalAccessException, NoSuchFieldException {
        Field field = Game.class.getDeclaredField("INSTANCE");
        field.setAccessible(true);
        field.set(null, null);
    }
    
    public static Board makeBoard(String... rows) {
        BoardAssembler assembler = new BoardAssembler();

        for (int y = 0; y < rows.length; y++) {
            char[] chars = rows[y].toCharArray();
            for (int x = 0; x < chars.length; x++) {
                assembler.onCellParsed(chars[x], x, y);
            }
        }

        return assembler.getBoard();
    }
    
}
