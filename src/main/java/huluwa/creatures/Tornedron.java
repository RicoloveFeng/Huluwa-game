package huluwa.creatures;

import huluwa.utils.Range;
import huluwa.utils.Position;

public class Tornedron extends Fighter {
    public Tornedron(Position position) {
        id = "Xiezi.png";
        hp = 60;
        miss = 0.05;
        ad = 9;
        moveRange = new Range(0, 2);
        attackRange = new Range(1, 2);
        this.position = position;
    }
}
