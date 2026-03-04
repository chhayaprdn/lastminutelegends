package ca.sfu.lastminutelegends.board;

public class Wall implements Cell {
    @Override
    public boolean isPassable() {
        return false;
    }
}
