package huluwa.strategy;

import huluwa.exceptions.StrategyOutOfPosition;
import huluwa.utils.Position;
import huluwa.utils.Utils;
import org.junit.Test;

import java.util.ArrayList;

import static huluwa.utils.Utils.*;
import static org.junit.Assert.*;

public class IStrategyTest {

    @Test
    public void nextPosition() throws IllegalAccessException, InstantiationException {
        ArrayList<IStrategy> strategies = new ArrayList<>();
        strategies.add(new Arrow());
        strategies.add(new Birdfly());
        strategies.add(new Birdwing());
        strategies.add(new Defend());
        strategies.add(new Fish());
        strategies.add(new Moon());
        strategies.add(new Snake());
        strategies.add(new Square());
        for(IStrategy strategy:strategies){
            IStrategy strategy1 = strategy.getClass().newInstance();
            IStrategy strategy2 = strategy.getClass().newInstance();
            Position tmp;

            try{
                for(int i:range(20)){
                    tmp = strategy1.nextPosition(Utils.DIRECTION.TO_BAD, new Position(7,0));
                    assertTrue(0<= tmp.col() && tmp.col() < GRID_HEIGHT && 0<=tmp.row() &&tmp.row() < GRID_WIDTH);

                }
            }catch (StrategyOutOfPosition e){

            }

            try{
                for(int j:range(20)){
                    tmp = strategy2.nextPosition(Utils.DIRECTION.TO_HULUWA, new Position(7,17));
                    assertTrue(0<= tmp.col() && tmp.col() < GRID_HEIGHT && 0<=tmp.row() &&tmp.row() < GRID_WIDTH);
                }

            }catch(StrategyOutOfPosition e){

            }
        }
    }
}