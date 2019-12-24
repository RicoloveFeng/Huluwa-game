package huluwa.strategy;

import huluwa.utils.Position;

import java.util.ArrayList;

public class Birdwing extends Strategy {
    public Birdwing(){
        posCount = 0;
        positions = new ArrayList<Position>();
        /*new Pos(0, 0),
                    new Pos(1, 1),
                    new Pos(1, -1),
                    new Pos(2, 1),
                    new Pos(2, -1),
                    new Pos(3, 2),
                    new Pos(3, -2),
                    new Pos(4, 2),
                    new Pos(4, -2)*/
        positions.add(new Position(0, 0));
        positions.add(new Position(1, 1));
        positions.add(new Position(-1, 1));
        positions.add(new Position(1, 2));
        positions.add(new Position(-1, 2));
        positions.add(new Position(2, 3));
        positions.add(new Position(-2, 3));
        positions.add(new Position(2, 4));
        positions.add(new Position(-2, 4));
        posLimit = positions.size();
    }
}
