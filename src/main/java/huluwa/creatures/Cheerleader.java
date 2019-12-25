package huluwa.creatures;

import huluwa.utils.Range;

public abstract class Cheerleader extends Creature implements ICheerleader {
    Range cheerRange;
    int buff;

    @Override
    public Range getCheerRange() {
        return cheerRange;
    }

    @Override
    public int attackBuff() {
        return buff;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    //public int attackBuff() {
    //    return buff;
    //}
}
