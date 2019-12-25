package huluwa.creatures;

import huluwa.GameManager;
import huluwa.utils.Range;
import huluwa.exceptions.MoveToOutsideOfField;
import huluwa.utils.Position;

public interface ICreature extends Runnable {
    // methods needed to draw in GUI
    String getIdentifier();

    int getHp();

    void takeDamage(int d);

    Range getMoveRange();

    Position getPosition();

    double getMissRate();

    void moveTo(int col, int row) throws MoveToOutsideOfField;

    boolean isAlive();

    void setProcessQueue(GameManager.ProcessQueue processQueue);

    boolean isAttackable();

    Object clone();
}
