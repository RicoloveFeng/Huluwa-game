package huluwa.creatures;

import huluwa.Range;
import huluwa.utils.Position;

public class Basilisk extends Creature implements Cheerleader {
    Range cheerRange;

    public Range getCheerRange() {
        return cheerRange;
    }

    @Override
    public int attackBuff() {
        return 3;
    }

    public Basilisk(Position position){
        id = "She.png";
        hp = 50;
        miss = 0.1;
        this.position = position;
        this.moveRange = new Range(0, 2);
        this.cheerRange = new Range(0, 3);
    }
}
