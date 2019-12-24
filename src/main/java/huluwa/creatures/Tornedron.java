package huluwa.creatures;

import huluwa.Range;
import huluwa.utils.Position;

public class Tornedron extends Creature implements Fighter{
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

    public Tornedron(Position position){
        id = "Xiezi.png";
        hp = 60;
        miss = 0.05;
        ad = 9;
        moveRange = new Range(0, 2);
        attackRange = new Range(1, 2);
        this.position = position;
    }
}
