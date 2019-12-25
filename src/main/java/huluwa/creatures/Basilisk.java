package huluwa.creatures;

import huluwa.utils.Range;
import huluwa.utils.Position;

public class Basilisk extends Cheerleader {
    public Basilisk(Position position) {
        id = "She.png";
        hp = 50;
        miss = 0.1;
        buff = 3;
        this.position = position;
        this.moveRange = new Range(0, 2);
        this.cheerRange = new Range(0, 3);
    }
}
