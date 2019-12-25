package huluwa.creatures;

import huluwa.utils.Range;

public interface ICheerleader extends ICreature {
    Range getCheerRange();

    int attackBuff();
}
