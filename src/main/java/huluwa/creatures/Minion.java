package huluwa.creatures;

import huluwa.utils.Range;
import huluwa.utils.Position;

public class Minion extends Fighter {
    public Minion(Position position, int number) {
        id = "Louluo.png";
        hp = 40;
        ad = 5;
        miss = 0.05;
        moveRange = new Range(0, 1);
        attackRange = new Range(0, 1);
        this.position = position;
    }
}
