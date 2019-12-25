package huluwa.creatures;

import huluwa.utils.Range;
import huluwa.utils.Position;

public class Grandpa extends Cheerleader {
    public Grandpa(Position position) {
        id = "Yeye.png";
        hp = 40;
        miss = 0.05;
        buff = 4;
        this.position = position;
        this.moveRange = new Range(0, 2);
        this.cheerRange = new Range(0, 3);
    }
}
