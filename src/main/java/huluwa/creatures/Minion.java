package huluwa.creatures;

import huluwa.Range;
import huluwa.utils.Position;

public class Minion extends Creature implements Fighter {
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

    public Minion(Position position, int number){
        id = "Louluo.png";
        hp = 40;
        ad = 5;
        miss = 0.05;
        moveRange = new Range(0, 1);
        attackRange = new Range(0, 1);
        this.position = position;
    }
}
