package huluwa.strategy;

import huluwa.utils.Position;

import java.util.ArrayList;

public class Fish extends Strategy {
    public Fish() {
        posCount = 0;
        positions = new ArrayList<Position>();
        /*new Pos(0, 0),
                    new Pos(1, 1),
                    new Pos(1, -1),
                    new Pos(1, 0),
                    new Pos(1, -2),
                    new Pos(1, 2),
                    new Pos(2, -1),
                    new Pos(2, 0),
                    new Pos(2, 1),
                    new Pos(3, 0)*/
        positions.add(new Position(0, 0));
        positions.add(new Position(1, 1));
        positions.add(new Position(-1, 1));
        positions.add(new Position(0, 1));
        positions.add(new Position(-2, 1));
        positions.add(new Position(2, 1));
        positions.add(new Position(-1, 2));
        positions.add(new Position(0, 2));
        positions.add(new Position(1, 2));
        posLimit = positions.size();
    }
}
