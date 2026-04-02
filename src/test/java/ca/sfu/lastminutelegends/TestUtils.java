package ca.sfu.lastminutelegends;

import ca.sfu.lastminutelegends.board.Board;
import ca.sfu.lastminutelegends.board.BoardAssembler;

import java.lang.reflect.Field;

public class TestUtils {

    /**
     * Sets the singleton Game instance to null so that the next time the instance() method is called a new Game object
     * is created. This effectively resets the game. Reflection is used to achieve this
     * 
     * @throws IllegalAccessException if there is an error in setting INSTANCE to null
     * @throws NoSuchFieldException if the INSTANCE field doesn't exist on Game
     */
    public static void resetGameInstance() throws IllegalAccessException, NoSuchFieldException {
        Field field = Game.class.getDeclaredField("INSTANCE");
        field.setAccessible(true);
        field.set(null, null);
    }

    /**
     * Utility method to construct a board from string rows.
     */
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
