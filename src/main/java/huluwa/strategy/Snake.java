package huluwa.strategy;

import huluwa.utils.Position;

import java.util.ArrayList;

public class Snake extends Strategy {
    public Snake(){
        posCount = 0;
        positions = new ArrayList<Position>();
        positions.add(new Position(0, 0));
        positions.add(new Position(1, 0));
        positions.add(new Position(2, 0));
        positions.add(new Position(3, 0));
        positions.add(new Position(4, 0));
        positions.add(new Position(-1, 0));
        positions.add(new Position(-2, 0));
        positions.add(new Position(-3, 0));
        positions.add(new Position(-4, 0));
        posLimit = positions.size();
    }
}
