package huluwa.strategy;

import huluwa.utils.Position;

import java.util.ArrayList;

public class Arrow extends Strategy {
    public Arrow(){
        posCount = 0;
        positions = new ArrayList<Position>();
        /*new Pos(0, 0),
                    new Pos(1, 0),
                    new Pos(2, 0),
                    new Pos(3, 0),
                    new Pos(4, 0),
                    new Pos(5, 0),
                    new Pos(5, 1),
                    new Pos(5, -1),
                    new Pos(4, 2),
                    new Pos(4, -2)*/
        positions.add(new Position(0, 0));
        positions.add(new Position(0, 1));
        positions.add(new Position(0, 2));
        positions.add(new Position(0, 3));
        positions.add(new Position(0, 4));
        positions.add(new Position(1, 4));
        positions.add(new Position(-1, 4));
        positions.add(new Position(-1, 3));
        positions.add(new Position(-1, 3));
        posLimit = positions.size();
    }
}
