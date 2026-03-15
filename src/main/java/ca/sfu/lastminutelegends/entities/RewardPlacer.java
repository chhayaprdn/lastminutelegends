package ca.sfu.lastminutelegends.entities;

import ca.sfu.lastminutelegends.board.BoardObserver;
import ca.sfu.lastminutelegends.reward.RegularReward;

import java.util.List;

public class RewardPlacer implements BoardObserver {
    private final List<Entity> entities;
    
    public RewardPlacer(List<Entity> entities) {
        this.entities = entities;
    }
    
    @Override
    public void onCellParsed(char c, int x, int y) {
        if (c == 'R') {
            entities.add(new RegularReward(new Position(x, y)));
        }
    }
}
