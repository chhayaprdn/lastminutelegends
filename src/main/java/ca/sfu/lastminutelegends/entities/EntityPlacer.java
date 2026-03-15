package ca.sfu.lastminutelegends.entities;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.board.BoardObserver;
import ca.sfu.lastminutelegends.reward.RegularReward;

public class EntityPlacer implements BoardObserver {
    @Override
    public void onCellParsed(char c, int x, int y) {
        if (c == 'R') {
            Game.instance().getEntities().add(new RegularReward(new Position(x, y)));
        } else if (c == 'M') {
            Game.instance().getEntities().add(new MovingEnemy(new Position(x, y)));
        } else if (c == 'S') {
            Player player = new Player(new Position(x, y));
            Game.instance().setPlayer(player);
            Game.instance().getEntities().add(player);
        }
    }
}
