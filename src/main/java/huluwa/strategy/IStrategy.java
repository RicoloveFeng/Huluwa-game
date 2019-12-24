package huluwa.strategy;

import huluwa.exceptions.StrategyOutOfPosition;
import huluwa.utils.Position;
import huluwa.utils.Utils;

public interface IStrategy {
    Position nextPosition(Utils.DIRECTION direction, Position base) throws StrategyOutOfPosition;
    int getLimit();
}
