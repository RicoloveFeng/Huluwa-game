package huluwa.creatures;

import huluwa.utils.Range;

public abstract class Fighter extends Creature implements IFighter {
    int ad;
    Range attackRange;
    ICreature target;

    @Override
    public int getAttackDamage() {
        return ad;
    }

    @Override
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

    @Override
    public boolean isAttackable() {
        return true;
    }
}
