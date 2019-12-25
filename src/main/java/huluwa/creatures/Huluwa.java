package huluwa.creatures;

import huluwa.utils.Range;
import huluwa.utils.Position;

public class Huluwa extends Fighter {
    public Huluwa(String name, int hp, int ad, double miss, Range ar, Range mr, Position pos) {
        this.id = name;
        this.hp = hp;
        this.ad = ad;
        this.miss = miss;
        this.attackRange = ar;
        this.moveRange = mr;
        this.position = pos;
        target = null;
    }

    public Huluwa(String name, int hp, int ad, Range ar, Range mr, Position pos) {
        this(name, hp, ad, 0.1, ar, mr, pos);
    }
}
