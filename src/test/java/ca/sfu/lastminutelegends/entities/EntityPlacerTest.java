package ca.sfu.lastminutelegends.entities;

import ca.sfu.lastminutelegends.Game;
import ca.sfu.lastminutelegends.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntityPlacerTest {
    
    private EntityPlacer placer;
    
    @BeforeEach
    void setup() throws NoSuchFieldException, IllegalAccessException {
        TestUtils.resetGameInstance();
        placer = new EntityPlacer();
    }
    
    @Test
    void entityPlacerPlacesPlayer() {
        placer.onCellParsed('S', 5, 4);
        
        assertEquals(1, Game.instance().getEntities().size());
        assertInstanceOf(Player.class, Game.instance().getEntities().getFirst());
        assertNotNull(Game.instance().getPlayer());
        assertEquals(new Position(5, 4), Game.instance().getEntities().getFirst().getPosition());
        assertEquals(new Position(5, 4), Game.instance().getPlayer().getPosition());
    }
    
    @Test
    void entityPlacerPlacesReward() {
        placer.onCellParsed('R', 3, 2);
        
        assertEquals(1, Game.instance().getEntities().size());
        assertInstanceOf(RegularReward.class, Game.instance().getEntities().getFirst());
        assertEquals(new Position(3, 2), Game.instance().getEntities().getFirst().getPosition());
    }

    @Test
    void entityPlacerPlacesEnemy() {
        placer.onCellParsed('M', 0, 0);

        assertEquals(1, Game.instance().getEntities().size());
        assertInstanceOf(MovingEnemy.class, Game.instance().getEntities().getFirst());
        assertEquals(new Position(0, 0), Game.instance().getEntities().getFirst().getPosition());
    }

    @Test
    void entityPlacerPlacesPunishment() {
        placer.onCellParsed('P', 11, 23);

        assertEquals(1, Game.instance().getEntities().size());
        assertInstanceOf(Punishment.class, Game.instance().getEntities().getFirst());
        assertEquals(new Position(11, 23), Game.instance().getEntities().getFirst().getPosition());
    }
    
    @Test
    void entityPlacerPlacesMultipleEntities() {
        placer.onCellParsed('P', 1, 0);
        placer.onCellParsed('R', 2, 0);
        placer.onCellParsed('R', 3, 0);
        placer.onCellParsed('M', 4, 0);

        assertEquals(4, Game.instance().getEntities().size());
        assertInstanceOf(MovingEnemy.class, Game.instance().getEntities().getLast());
        assertEquals(new Position(2, 0), Game.instance().getEntities().get(1).getPosition());
        assertEquals(new Position(3, 0), Game.instance().getEntities().get(2).getPosition());
    }
}
