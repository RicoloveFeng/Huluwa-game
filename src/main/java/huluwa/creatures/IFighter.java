package huluwa.creatures;

import huluwa.utils.Range;

public interface IFighter extends ICreature {
    int getAttackDamage();

    Range getAttackRange();

    ICreature getTarget();

    void setTarget(ICreature tg);
}
