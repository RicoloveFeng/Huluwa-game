package huluwa;

import huluwa.creatures.ICheerleader;
import huluwa.creatures.ICreature;
import huluwa.utils.Position;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import static huluwa.utils.Utils.LARGE_NUM;

public class Team implements Iterable<ICreature> {
    private ArrayList<ICreature> member;
    private ICreature cheerleader;

    public void add(ICreature c) {
        member.add(c);
    }

    public void setCheerleader(ICreature c) {
        cheerleader = c;
    }

    public int getMemberSize() {
        int ret = 0;
        if (cheerleader != null) ret += 1;
        return member.size() + ret;
    }

    public ICheerleader getCheerleader() {
        return (ICheerleader) cheerleader;
    }

    public boolean contains(ICreature creature) {
        return creature == cheerleader || member.contains(creature);
    }

    public Position bestCheerPosition(BattleField bf) {
        int[][] planMap = new int[bf.height][bf.width];
        for (ICreature fighter : member) {
            if (fighter.isAlive()) {
                Position base = fighter.getPosition();
                for (int i = base.col() - 2; i < base.col() + 3; i++) {
                    for (int j = base.row() - 2; j < base.row() + 3; j++) {
                        if (0 <= i && i < bf.height && 0 <= j && j < bf.width) {
                            planMap[i][j] += 1;
                        }
                    }
                }
            }
        }
        int maxHeat = -1;
        Position ret = null;
        for (int i = 0; i < bf.height; i++) {
            for (int j = 0; j < bf.width; j++) {
                if (planMap[i][j] > maxHeat && !bf.getLand(i, j).isBlocking()) {
                    maxHeat = planMap[i][j];
                    ret = new Position(i, j);
                }
            }
        }
        return ret;
    }

    public ArrayList<ICreature> getAliveMembers() {
        ArrayList<ICreature> ret = new ArrayList<>();
        for (ICreature guy : this) {
            if (guy.getHp() > 0) {
                ret.add(guy);
            }
        }
        Collections.shuffle(ret);
        return ret;
    }

    public ICreature getNearsetMember(Position base) {
        int min_dis = LARGE_NUM;
        ICreature ret = null;
        for (ICreature guy : this.getAliveMembers()) {
            if (Position.distance(base, guy.getPosition()) < min_dis) {
                min_dis = Position.distance(base, guy.getPosition());
                ret = guy;
            }
        }
        return ret;
    }

    public boolean isOccupying(Position pos) {
        for (ICreature guy : this) {
            if (guy.getPosition().equals(pos) && guy.isAlive()) return true;
        }
        return false;
    }

    @NotNull
    public Iterator<ICreature> iterator() {
        return new iter();
    }

    private class iter implements Iterator<ICreature> {
        boolean cheered;
        int index;

        public boolean hasNext() {
            return !cheered || index < member.size();
        }

        public ICreature next() {
            if (!cheered) {
                if (cheerleader == null) {
                    throw new NullPointerException("Cheerleader not set");
                }
                cheered = true;
                return cheerleader;
            } else {
                return member.get(index++);
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        iter() {
            cheered = false;
            index = 0;
        }
    }

    Team() {
        member = new ArrayList<ICreature>();
        cheerleader = null;
    }
}
