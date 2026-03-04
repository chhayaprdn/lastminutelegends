package ca.sfu.lastminutelegends.board;

public class EmptyCell implements Cell {
    @Override
    public boolean isPassable() {
        return true;
    }
}
