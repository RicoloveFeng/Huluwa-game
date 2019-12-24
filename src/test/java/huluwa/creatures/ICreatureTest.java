package huluwa.creatures;

import huluwa.exceptions.MoveToOutsideOfField;
import huluwa.utils.Position;
import org.junit.Test;

import static org.junit.Assert.*;

public class ICreatureTest {

    @Test
    public void takeDamage() {
        ICreature creature = new Minion(new Position(0,0),0);
        creature.takeDamage(1000);
        assertFalse(creature.isAlive());
    }

    @Test(expected = MoveToOutsideOfField.class)
    public void moveTo() throws MoveToOutsideOfField {
        ICreature creature = new Minion(new Position(0,0),0);
        creature.moveTo(20,20);
        fail("Can go into outside of map");
    }
}