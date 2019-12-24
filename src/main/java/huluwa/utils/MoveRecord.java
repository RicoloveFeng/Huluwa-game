package huluwa.utils;

import static huluwa.utils.Utils.*;
import huluwa.creatures.ICreature;

public class MoveRecord {
    public final ICreature from;
    public final ICreature to;
    public final Position src;
    public final Position dest;
    public final MOVE move;
    public MoveRecord(ICreature f, ICreature t, Position s, Position d, MOVE m){
        from = f;
        to = t;
        src = s;
        dest = d;
        move = m;
    }
}
