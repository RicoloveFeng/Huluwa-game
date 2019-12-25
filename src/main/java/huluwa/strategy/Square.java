package huluwa.strategy;

import huluwa.utils.Position;

import java.util.ArrayList;

public class Square extends Strategy {
    public Square() {
        posCount = 0;
        positions = new ArrayList<Position>();
        positions.add(new Position(0, 0));
        positions.add(new Position(-1, 1));
        positions.add(new Position(1, 1));
        positions.add(new Position(-2, 2));
        positions.add(new Position(2, 2));
        positions.add(new Position(-3, 1));
        positions.add(new Position(3, 1));
        positions.add(new Position(4, 0));
        posLimit = positions.size();
    }
}
