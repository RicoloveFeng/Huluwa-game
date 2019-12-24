package huluwa;

import huluwa.lands.Land;
import huluwa.utils.Position;
import org.junit.Test;

import static org.junit.Assert.*;

public class BattleFieldTest {

    @Test
    public void getLand() {
        BattleField bf = new BattleField();
        Land test;
        test = bf.getLand(-1, -1);
        assertNull(test);
        test = bf.getLand(new Position(5, 5));
        assertNotNull(test);
        test = bf.getLand(14,18);
        assertNull(test);
    }
}