package huluwa.strategy;

import huluwa.utils.Position;

import java.util.ArrayList;

public class Birdfly extends Strategy {
    public Birdfly(){
        posCount = 0;
        positions = new ArrayList<Position>();
        /*new Pos(2, 0),
                    new Pos(2, 1),
                    new Pos(1, -1),
                    new Pos(3, 2),
                    new Pos(1, -2),
                    new Pos(3, 3),
                    new Pos(0, -3),
                    new Pos(4, 4),
                    new Pos(0, -4),
                    new Pos(4, 5)*/
        positions.add(new Position(0, 2));
        positions.add(new Position(1, 2));
        positions.add(new Position(-1, 1));
        positions.add(new Position(2, 3));
        positions.add(new Position(-2, 1));
        positions.add(new Position(3, 3));
        positions.add(new Position(-3, 0));
        positions.add(new Position(4, 4));
        positions.add(new Position(-4, 0));
        posLimit = positions.size();
    }
}
