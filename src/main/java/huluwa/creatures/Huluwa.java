package huluwa.creatures;

import huluwa.Range;
import huluwa.utils.Position;

public class Huluwa extends Creature implements Fighter {
    int ad;
    Range attackRange;
    ICreature target;

    public int getAttackDamage() {
        return ad;
    }

    public Range getAttackRange() {
        return attackRange;
    }

    @Override
    public ICreature getTarget() {
        return target;
    }

    @Override
    public void setTarget(ICreature tg) {
        target = tg;
    }

    public Huluwa(String name, int hp, int ad, double miss, Range ar, Range mr, Position pos){
        this.id = name;
        this.hp = hp;
        this.ad = ad;
        this.miss = miss;
        this.attackRange = ar;
        this.moveRange = mr;
        this.position = pos;
        target = null;
    }
    public Huluwa(String name, int hp, int ad, Range ar, Range mr, Position pos){
        this(name, hp, ad, 0.1, ar, mr, pos);
    }
}
