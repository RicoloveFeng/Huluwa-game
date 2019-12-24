package huluwa.creatures;

import huluwa.Range;
import huluwa.utils.Position;

public class Grandpa extends Creature implements Cheerleader {
    Range cheerRange;

    public Range getCheerRange() {
        return cheerRange;
    }

    @Override
    public int attackBuff() {
        return 3;
    }

    public Grandpa(Position position){
        id = "Yeye.png";
        hp = 40;
        miss = 0.05;
        this.position = position;
        this.moveRange = new Range(0, 2);
        this.cheerRange = new Range(0, 3);
    }
}
