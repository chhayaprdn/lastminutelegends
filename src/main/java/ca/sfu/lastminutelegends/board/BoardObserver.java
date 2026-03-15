package ca.sfu.lastminutelegends.board;

public interface BoardObserver {
    /**
     * Called when a char is parsed from the board file
     * 
     * @param c the char read
     * @param x the col index
     * @param y the row index
     */
    void onCellParsed(char c, int x, int y);
}
