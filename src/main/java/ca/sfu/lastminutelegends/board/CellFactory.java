package ca.sfu.lastminutelegends.board;

public class CellFactory {
    private static final Cell WALL = new Wall();
    private static final Cell EMPTY = new EmptyCell();
    private static final Cell START_POINT = new StartPoint();
    private static final Cell END_POINT = new EndPoint();
    private static final Cell REGULAR_REWARD = new RewardCell();
    
    public static Cell wall() {
        return WALL;
    }
    
    public static Cell empty() {
        return EMPTY;
    }
    
    public static Cell startPoint() {
        return START_POINT;
    }
    
    public static Cell endPoint() {
        return END_POINT;
    }

    public static Cell regularReward() {
        return REGULAR_REWARD;
    }
}
