package huluwa.strategy;

import huluwa.exceptions.StrategyOutOfPosition;
import huluwa.utils.Position;
import huluwa.utils.Utils;

import java.util.ArrayList;

public class Strategy implements IStrategy {
    int posCount;
    int posLimit;
    ArrayList<Position> positions;

    public Position nextPosition(Utils.DIRECTION direction, Position base) throws StrategyOutOfPosition {
        if (posCount < posLimit) {
            Position ret = positions.get(posCount);
            if (direction == Utils.DIRECTION.TO_HULUWA) ret.scale(-1, -1);
            ret.offset(base.col(), base.row());
            posCount++;
            return ret;
        } else {
            throw new StrategyOutOfPosition("in generating " + posCount);
        }
    }

    public int getLimit() {
        return posLimit;
    }
}
