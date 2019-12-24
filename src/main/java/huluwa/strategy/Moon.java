package huluwa.strategy;

import huluwa.utils.Position;

import java.util.ArrayList;

public class Moon extends Strategy {
    public Moon(){
        posCount = 0;
        positions = new ArrayList<Position>();
        /*          new Pos(0, 0),
                    new Pos(0, 1),
                    new Pos(1, 0),
                    new Pos(1, 1),
                    new Pos(2, 1),
                    new Pos(2, 2),
                    new Pos(3, 2),
                    new Pos(3, 3),
                    new Pos(4, 1),
                    new Pos(4, 2),
                    new Pos(5, 1),
                    new Pos(5, 0)*/
        positions.add(new Position(0, 0));
        positions.add(new Position(1, 0));
        positions.add(new Position(1, 1));
        positions.add(new Position(2, 1));
        positions.add(new Position(2, 2));
        positions.add(new Position(3, 2));
        positions.add(new Position(1, 3));
        positions.add(new Position(2, 3));
        positions.add(new Position(0, 4));
        positions.add(new Position(1, 4));
        posLimit = positions.size();
    }
}
