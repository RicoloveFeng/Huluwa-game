package huluwa.creatures;

import huluwa.Range;

public interface Fighter {
    int getAttackDamage();
    Range getAttackRange();
    ICreature getTarget();
    void setTarget(ICreature tg);
}
